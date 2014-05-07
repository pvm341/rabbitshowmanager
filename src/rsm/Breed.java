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
    private int youngsters;
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

    public int getYoungsters() {
        return youngsters;
    }

    public void setYoungsters(int youngsters) {
        this.youngsters = youngsters;
    }

    public String getYoungstersText(){
        return DBA.lookup("age_text", "exhibit_ages", "id", Integer.toString(this.youngsters));
    }
    
    public String getYounstersAbbrev(){
        return DBA.lookup("abbrev", "exhibit_ages", "id", Integer.toString(this.youngsters));
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
        return  (breed1.youngsters   == breed2.youngsters) &&
                (breed1.section      == breed2.section)  &&
                (breed1.topPenReq    == breed2.topPenReq)&&
                (breed1.breed.equals(breed2.breed));
    }
    
    @Override
    public String toListString(String formatStr){
        String tableLine = String.format(formatStr, getStatusChar(),
                                                    getId(),
                                                    getYounstersAbbrev(),
                                                    isTopPenReqStr(),
                                                    getSectionStr(),
                                                    getBreed());    
        return tableLine;
    }
    
    @Override
    public Breed getData(ResultSet rs) throws SQLException {
        this.id = rs.getInt("id");
        this.youngsters = rs.getInt("youngsters");
        this.topPenReq = rs.getBoolean("top_pen_req");
        this.section = rs.getInt("section");
        this.breed = rs.getString("breed");
        super.getData();
        return this;
    }

    @Override
    public void performUpdate() {
        DBA.updateSQL(String.format("UPDATE breeds SET youngsters = %d, "
                + "top_pen_req = %s, section = %d breed = \'%s\' where id = %d",
                this.youngsters,
                Boolean.toString(this.topPenReq),
                this.section,
                this.breed,
                this.id));
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
        DBA.updateSQL(String.format("INSERT INTO breeds (id,youngsters,top_pen_req,section,breed) VALUES (%d,%d,%s,%d,\'%s\')",this.getId(),this.getYoungsters(),this.isTopPenReqStr(),this.getSection(),this.getBreed()));
        // New Breed therefore need to create breedcolour new breed anycolour record in breedcolours 
        // others will have to be entered manually on another form
        DBA.updateSQL("INSERT INTO breedcolours (breed_id,colour_id,available, "
                + "selected, class_no) VALUES ("+Integer.toString(this.getId())+",1,true,false,0)");
 }

    @Override
    public Breed performRead() {
        ResultSet rs = DBA.executeSQL(String.format(
              "SELECT * FROM breed WHERE id = %d",this.id));
        try {
            rs.next();
            return this.getData(rs);
        } catch (SQLException ex) {
            Logger.getLogger(Colour.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

}