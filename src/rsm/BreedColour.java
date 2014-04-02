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
public class BreedColour {
    private int breedId, colourId;

    
    public BreedColour (){
        
    }

    public BreedColour (int breed, int colour){
        this.breedId = breed;
        this.colourId = colour;
    }

    public int getBreedId() {
        return breedId;
    }

    public void setBreedId(int breedId) {
        this.breedId = breedId;
    }

    public int getColourId() {
        return colourId;
    }

    public void setColourId(int colourId) {
        this.colourId = colourId;
    }
    
    public String getBreedStr(){
        ResultSet rs;
        String str = null;
        rs = DBAccess.executeSQL("SELECT breed FROM breeds WHERE id = "+Integer.toString(breedId));
        try {
            while(rs.next()){
                str = rs.getString("breed");
            }
        } catch (SQLException ex) {
            Logger.getLogger(BreedColour.class.getName()).log(Level.SEVERE, null, ex);
        }
        return str;
    }

    public String getColourStr(){
        ResultSet rs;
        String str = null;
        rs = DBAccess.executeSQL("SELECT colour FROM colours WHERE id = "+Integer.toString(colourId));
        try {
            while(rs.next()){
                str = rs.getString("colour");
            }
        } catch (SQLException ex) {
            Logger.getLogger(BreedColour.class.getName()).log(Level.SEVERE, null, ex);
        }
        return str;
    }
    
    public String getColourAbbrev(){
        ResultSet rs;
        String str = null;
        rs = DBAccess.executeSQL("SELECT colour FROM colours WHERE id = "+Integer.toString(colourId));
        try {
            while(rs.next()){
                str = rs.getString("abbrev");
            }
        } catch (SQLException ex) {
            Logger.getLogger(BreedColour.class.getName()).log(Level.SEVERE, null, ex);
        }
        return str;
    }
    
    public void readRecord(int breed, int colour ) {
        ResultSet rs = DBAccess.executeSQL("SELECT * FROM breedcolours WHERE breed_id = "+breed);
        try {
            while (rs.next()){
                if (rs.getInt("colour_id")==colour){
                    
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(BreedColour.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void writeRecord() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


    public void deleteRecord() {
         String where = String.format("WHERE breed_id = %d AND colour_id = %d ", this.breedId, this.colourId);
        if (DBAccess.isExistingRec("breedcolours", where)){
            DBAccess.updateSQL("DELETE FROM breeds WHERE "+where); 
        }
    }
    
}
