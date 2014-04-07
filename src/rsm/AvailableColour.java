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
public class AvailableColour {
    private int id;
    private String colour;
    private String abbrev;
    private boolean available;
    
    public AvailableColour(ResultSet rs){
        boolean err = false;
        try {
            this.id = rs.getInt("id");
            this.colour = rs.getString("colour");
            this.abbrev = rs.getString("abbrev");
            this.available = true;
        } catch (SQLException ex) {
            err = true;
            Logger.getLogger(AvailableColour.class.getName()).log(Level.SEVERE, null, ex);
        } finally{
            if (err){
                this.id =0;
                this.available=false;
                this.colour = "error";
                this.abbrev = "err";
            }
        }
    }

    AvailableColour(Colour colour) {
        this.id = colour.getId();
        this.colour = colour.getColour();
        this.abbrev = colour.getAbbrev();
        this.available = true;
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

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}
