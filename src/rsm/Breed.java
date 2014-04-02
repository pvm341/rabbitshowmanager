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
public class Breed implements DBInterface{
    private int id;
    private int adultAge;
    private boolean topPenReq;
    private int section;
    private String breed;
        
    public Breed(){
        
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

    public boolean isTopPenReq() {
        return topPenReq;
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

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }
    
    @Override
    public void readRecord(int recNo){
        ResultSet rs;
        rs = DBAccess.executeSQL("SELECT * FROM breeds WHERE id="+Integer.toString(recNo));
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
    
    @Override
    public void writeRecord(){
        String sql;
        String where = String.format("id = %d", this.id);
        if (DBAccess.isExistingRec("breeds",where)){
            sql=String.format("UPDATE breeds SET adult_age = %d, top_pen_req = %s, section = %d, breed = \'%s\' WHERE %s",this.adultAge, this.topPenReq ? "true":"false",this.section,this.breed,where); 
        } else {
            this.id = 1 + DBAccess.getRecordCount("breeds");
            sql=String.format("INSERT INTO breeds (id,adult_age,top_pen_req,section,breed) VALUES (%d,%d,%s,%d,\'%s\')",this.id,this.adultAge, this.topPenReq ? "true":"false",this.section,this.breed);
        }
        DBAccess.updateSQL(sql);
    }
    
    @Override
    public void deleteRecord(){
        String where = String.format("id = %d", this.id);
        if (DBAccess.isExistingRec("breeds", where)){
            DBAccess.updateSQL("DELETE FROM breeds WHERE "+where); 
        }    
    }

    public int getRecordCount() {
        return DBAccess.getRecordCount("breeds",null);
    }
    
    public int getRecordCount(String where){
        return DBAccess.getRecordCount("breeds",where);
    }

}