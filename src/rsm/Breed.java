/*
 * Copyright (C) 2014 paul
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package rsm;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author paul
 */
public class Breed extends BaseDataItem implements DBInterface{
    private int id;
    private int adultAge;
    private boolean topPenReq;
    private int section;
    private String breed;
        
    public Breed(){
       super(); 
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAdultAge() {
        return adultAge;
    }

    public void setAdultAge(int adultAge) {
        this.adultAge = adultAge;
    }

    public String getAdultAgeText(){
        return DBA.lookup("age_text", "exhibit_ages", "id", Integer.toString(this.adultAge));
    }
    
    public String getAdultAgeAbbrev(){
        return DBA.lookup("abbrev", "exhibit_ages", "id", Integer.toString(this.adultAge));
    }
    
    public boolean isTopPenReq() {
        return topPenReq;
    }

    public String isTopPenReqStr(){
        return this.topPenReq ? "true" : "false";
    }
    
    public void setTopPenReq(boolean topPenReq) {
        this.topPenReq = topPenReq;
    }

    public int getSection() {
        return section;
    }

    public void setSection(int section) {
        this.section = section;
    }

    public String getSectionStr(){
        return DBA.lookup("section_text", "showsections", "section", Integer.toString(section));
    }
    
    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }
    
  
    public boolean equals(Breed breed1, Breed breed2){
        return  (breed1.adultAge     == breed2.adultAge) &&
                (breed1.section      == breed2.section)  &&
                (breed1.topPenReq    == breed2.topPenReq)&&
                (breed1.breed.equals(breed2.breed));
    }
    
    @Override
    public String toListString(String formatStr){
        String tableLine = String.format(formatStr, getStatusChar(),
                                                    getId(),
                                                    getAdultAgeAbbrev(),
                                                    isTopPenReqStr(),
                                                    getSectionStr(),
                                                    getBreed());    
        return tableLine;
    }
    
        public void readRecord(int recNo){
        ResultSet rs;
        rs = DBA.executeSQL("SELECT * FROM breeds WHERE id="+Integer.toString(recNo));
        if (rs != null){
            try {
                rs.next();
                this.id=rs.getInt("id");
                this.adultAge = rs.getInt("adult_age");
                this.topPenReq = rs.getBoolean("top_pen_req");
                this.section = rs.getInt("section");
                this.breed = rs.getString("breed");
            } catch (SQLException ex) {
                Logger.getLogger(Breed.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void writeRecord(){
        String sql;
        String where = String.format("id = %d", this.id);
        if (DBA.isExistingRec("breeds",where)){
            sql=String.format("UPDATE breeds SET adult_age = %d, top_pen_req = %s, section = %d, breed = \'%s\' WHERE %s",this.adultAge, this.topPenReq ? "true":"false",this.section,this.breed,where); 
        } else {
            this.id = 1 + DBA.getRecordCount("breeds");
            sql=String.format("INSERT INTO breeds (id,adult_age,top_pen_req,section,breed) VALUES (%d,%d,%s,%d,\'%s\')",this.id,this.adultAge, this.topPenReq ? "true":"false",this.section,this.breed);
        }
        DBA.updateSQL(sql);
        setDirty(false);
    }
    

    public void deleteRecord(){
        String where = String.format("id = %d", this.id);
        if (DBA.isExistingRec("breeds", where)){
            DBA.updateSQL("DELETE FROM breeds WHERE "+where); 
        }    
    }

    public int getRecordCount() {
        return DBA.getRecordCount("breeds",null);
    }
    
    public int getRecordCount(String where){
        return DBA.getRecordCount("breeds",where);
    }

    @Override
    public Breed getData(ResultSet rs) throws SQLException {
        this.id = rs.getInt("id");
        this.adultAge = rs.getInt("adult_age");
        this.topPenReq = rs.getBoolean("top_pen_req");
        this.section = rs.getInt("section");
        this.breed = rs.getString("breed");
        super.getData();
        return this;
    }

    @Override
    public void performUpdate() {
       
    }

    @Override
    public void performDelete() {
        // Delete contraints with this id
        DBA.updateSQL("DELETE FROM breedcolours WHERE breed_id ="+Integer.toString(this.getId()));
        // delete main record
        DBA.updateSQL(String.format("DELETE FROM breeds WHERE id = %d",this.getId()));
    }
        
    @Override
    public void performInsert() {
        DBA.updateSQL(String.format("INSERT INTO breeds (id,adult_age,top_pen_req,section,breed) VALUES (%d,%d,%s,%d,\'%s\')",this.getId(),this.getAdultAge(),this.isTopPenReqStr(),this.getSection(),this.getBreed()));
        // New Breed therefore need to create breedcolour new breed anycolour record in breedcolours 
        // others will have to be entered manually on another form
        DBA.updateSQL("INSERT INTO breedcolours (breed_id,colour_id) VALUES ("+Integer.toString(this.getId())+",1)");
 }

    @Override
    public Breed performRead() {
        ResultSet rs = DBA.executeSQL(String.format(
              "SELECT * FROM breed WHERE id = %d",this.id));
        try {
            return this.getData(rs);
        } catch (SQLException ex) {
            Logger.getLogger(Colour.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

}