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
public class Colour extends BaseDataItem implements DBInterface{
    private int id;
    private String colour;
    private String abbrev;
    
    public Colour(){
        super(); 
    }
    
    public Colour (int id){
        super();
        this.id = id;
        this.abbrev="nk";
        this.colour="nk";
    }
    
    public Colour(int id, String colour, String abbrev){
        super();
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
    public String toListString(String formatString) {
        String tableLine = String.format(formatString,  this.getStatusChar(),
                                                        this.getId(),
                                                        this.getAbbrev(),
                                                        this.getColour());    
        return tableLine;
    }

    @Override
    public Colour getData(ResultSet rs) throws SQLException {
       id = rs.getInt("id");
       abbrev =rs.getString("abbrev");
       colour =rs.getString("colour");
       super.getData();
       return this;
    }

    @Override
    public int performUpdate() {
        int results = 0;
        results = DBA.updateSQL(String.format(
                "UPDATE colours SET colour= \'%s\', abbrev=\'%s\' WHERE id = %d",
                this.colour,this.abbrev,this.id));
        this.setDirty(false);
        return results;
    }

    @Override
    public int performDelete() {
        int results = 0;
            
        // delete dependant records in breedcolours
        DBA.updateSQL("DELETE FROM breedcolours WHERE colour_id = "+ Integer.toString(this.id));
        results = DBA.updateSQL(String.format("DELETE FROM colours WHERE id = %d",this.id));
        this.setReadyToDelete(false);
        return results;
    }

    @Override
    public int performInsert() {
        int results =0;
        results = DBA.updateSQL(String.format(
            "INSERT INTO colours (id,abbrev,colour) VALUES (%d,\'%s\',\'%s\')", 
                this.id, this.abbrev,this.colour));
        this.setNewItem(false);
        return results;
    }

    @Override
    public Colour performRead() {
        ResultSet rs = DBA.executeSQL(String.format(
                "SELECT * FROM colours WHERE id = %d",this.id));
        try {
            rs.next();
            return this.getData(rs);
        } catch (SQLException ex) {
            Logger.getLogger(Colour.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public Colour performRead(int id) {
        ResultSet rs = DBA.executeSQL(String.format(
                "SELECT * FROM colours WHERE id = %d",id));
        try {
            return this.getData(rs);
        } catch (SQLException ex) {
            Logger.getLogger(Colour.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
}
