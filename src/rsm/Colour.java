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
public class Colour implements DBInterface{
    private int id;
    private String colour;
    private String abbrev;
    
    public Colour(){
       System.out.println("Colour no args constructor");
    }
    
    public Colour(int id, String colour, String abbrev){
        this.id = id;
        this.colour = colour;
        this.abbrev = abbrev;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    public String getAbbrev() {
        return abbrev;
    }

    public void setAbbrev(String abbrev) {
        this.abbrev = abbrev;
    }
    
    @Override
    public void deleteRecord() {
        String where = String.format("id = %d", id);
        if (DBAccess.isExistingRec("colours", where)){
            DBAccess.updateSQL("DELETE FROM colours WHERE "+where); 
        }    
    }

    @Override
    public void readRecord(int recNo) {
        ResultSet rs;
        String sql = String.format("SELECT * FROM colours WHERE id = %d", recNo);
        rs = DBAccess.executeSQL(sql);
        try {
            rs.next();
            this.id = rs.getInt("id");
            this.colour = rs.getString("colour");
            this.abbrev = rs.getString("abbrev");
        } catch (SQLException ex) {
            Logger.getLogger(Colour.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /** 
     * writeRecord will update or insert an existing record as appropriate
     */
    @Override
    public void writeRecord() {
        String sql;
        String where = String.format("id = %d", this.id);
        if (DBAccess.isExistingRec("colours",where)){
            sql=String.format("UPDATE colours SET colour = \'%s\', abbrev = \'%s\' WHERE %s",this.colour, this.abbrev,where); 
        } else {
            this.id = DBAccess.getNewKey("colours", "id");//1 + DBAccess.getRecordCount("colours");
            sql=String.format("INSERT INTO colours (id,colour,abbrev) VALUES (%d,\'%s\',\'%s\')",this.id,this.colour, this.abbrev);
        }
        DBAccess.updateSQL(sql);
    }
    
    
}
