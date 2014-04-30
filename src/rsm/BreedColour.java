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
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author paul
 */
public class BreedColour extends BaseDataItem {
    private int[] breedId  = {0,0};
    private int[] colourId = {0,0};
    private boolean available;
    private boolean selected;
    private int classNo; 
    
    public BreedColour(){

    }

    public BreedColour(int i, int i0) {
       this.breedId[VersionRequired.CURRENT.ordinal()] = i;
       this.breedId[VersionRequired.PREVIOUS.ordinal()] = this.breedId[VersionRequired.CURRENT.ordinal()];
       this.colourId[VersionRequired.CURRENT.ordinal()] = i0;
       this.colourId[VersionRequired.PREVIOUS.ordinal()] = this.colourId[VersionRequired.CURRENT.ordinal()];
    }

    
    public void setBreedAndColourIds(int breedId, int colourId) {
        if (! this.dirtyBit) {
            this.breedId[VersionRequired.PREVIOUS.ordinal()] = this.breedId[VersionRequired.CURRENT.ordinal()];
            this.colourId[VersionRequired.PREVIOUS.ordinal()] = this.colourId[VersionRequired.CURRENT.ordinal()];
        }
        this.breedId[VersionRequired.CURRENT.ordinal()] = breedId;
        this.colourId[VersionRequired.CURRENT.ordinal()] = colourId;
        if (this.breedId[VersionRequired.PREVIOUS.ordinal()] == 0 && this.colourId[1] == 0){
           this.breedId[VersionRequired.PREVIOUS.ordinal()]  = this.breedId[0];
           this.colourId[VersionRequired.PREVIOUS.ordinal()] = this.colourId[0];
           this.dirtyBit = false;
        } else {
           this.dirtyBit = true; 
        }
    }

    public int getBreedId(VersionRequired vr) {
        return this.breedId[vr.ordinal()];
    }

  
    public int getColourId(VersionRequired vr) {
        return this.colourId[vr.ordinal()];
    }
    
    private String getColourIdStr(VersionRequired vr){
        return String.valueOf(this.colourId[vr.ordinal()]);
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public int getClassNo() {
        return classNo;
    }

    public void setClassNo(int classNo) {
        this.classNo = classNo;
    }

   
    @Override
    public String toListString(String formatString) {
        String tableLine = String.format(formatString, this.getStatusChar(),
                                                       this.getBreedId(VersionRequired.CURRENT),
                                                       Integer.toString(this.getColourId(VersionRequired.CURRENT)),
                                                       Boolean.toString(this.available),
                                                       Boolean.toString(this.selected),
                                                       Integer.toString(this.classNo));
        return tableLine; 
    }

    public int getId() {
        return -1;
    }

    @Override
    public BreedColour getData(ResultSet rs) throws SQLException {
        if (rs == null){
            System.out.printf("rs = null B=%d C=%d\n ",this.breedId[VersionRequired.CURRENT.ordinal()],this.colourId[VersionRequired.CURRENT.ordinal()]);
        } else {
        this.setBreedAndColourIds(rs.getInt("breed_id"),rs.getInt("colour_id"));
        this.setAvailable(rs.getBoolean("available"));
        this.setSelected(rs.getBoolean("selected"));
        this.setClassNo(rs.getInt("class_no"));
        super.getData();
        }
        return this;
    }

    @Override
    public void performUpdate() {
        DBA.updateSQL(String.format(
                "UPDATE breedcolours SET breed_id = %d, colour_id = %d, available = %s, selected = %s, class_no = %d WHERE breed_id = %d AND colour_id = %d",
                        this.getBreedId(VersionRequired.CURRENT),
                        this.getColourId(VersionRequired.CURRENT),
                        this.available,
                        this.selected,
                        this.classNo,
                        this.getBreedId(VersionRequired.PREVIOUS),
                        this.getColourId(VersionRequired.PREVIOUS)));
        this.setDirty(false);
    }

    @Override
    public void performDelete() {
        DBA.updateSQL(String.format(
                "DELETE FROM breedcolours WHERE"
                        + " breed_id = %d AND colour_id = %d",
                this.getBreedId(VersionRequired.PREVIOUS),
                this.getColourId(VersionRequired.PREVIOUS)));
        this.setReadyToDelete(false);
    }

    @Override
    public void performInsert() {
        DBA.updateSQL(String.format(
                "INSERT INTO breedcolours (breed_id,colour_id, available, selected, class_no) VALUES (%d,%d,%s,%s,%d)",
                this.getBreedId(VersionRequired.CURRENT),
                this.getColourId(VersionRequired.CURRENT),
                this.available,
                this.selected,
                this.classNo));
        this.setNewItem(false);
    }

    @Override
    public BreedColour performRead() {
        ResultSet rs;
        rs = DBA.executeSQL(String.format(
                "SELECT * FROM breedcolours WHERE breed_id = %d AND colour_id = %d",
                this.breedId[VersionRequired.CURRENT.ordinal()],
                this.colourId[VersionRequired.CURRENT.ordinal()]));
        try {
            rs.next();
            return this.getData(rs);
        } catch (SQLException ex) {
            Logger.getLogger(Colour.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    
    }   
}
