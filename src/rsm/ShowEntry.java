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
public class ShowEntry extends BaseDataItem implements DBInterface {
    private int[] classNo = new int[2];
    private int[] penNo   = new int[2];
    
   
    public ShowEntry() {
        this.classNo[VersionRequired.CURRENT.ordinal()]=0;
        this.classNo[VersionRequired.PREVIOUS.ordinal()]=0;
        this.penNo[VersionRequired.CURRENT.ordinal()] = 0;
        this.penNo[VersionRequired.PREVIOUS.ordinal()] = 0;
    }

    public ShowEntry(int i, int i0) {
       this.classNo[VersionRequired.CURRENT.ordinal()] = i;
       this.classNo[VersionRequired.PREVIOUS.ordinal()] = this.classNo[VersionRequired.CURRENT.ordinal()];
       this.penNo[VersionRequired.CURRENT.ordinal()] = i0;
       this.penNo[VersionRequired.PREVIOUS.ordinal()] = this.penNo[VersionRequired.CURRENT.ordinal()];
    }

    
    public void setPenAndClassNo(int penNo, int classNo) {
        if (! this.dirtyBit) {
            this.classNo[VersionRequired.PREVIOUS.ordinal()] = this.classNo[VersionRequired.CURRENT.ordinal()];
            this.penNo[VersionRequired.PREVIOUS.ordinal()] = this.penNo[VersionRequired.CURRENT.ordinal()];
        }
        this.classNo[VersionRequired.CURRENT.ordinal()] = classNo;
        this.penNo[VersionRequired.CURRENT.ordinal()] = penNo;
        if (this.classNo[VersionRequired.PREVIOUS.ordinal()] == 0 && this.penNo[VersionRequired.PREVIOUS.ordinal()] == 0){
           this.classNo[VersionRequired.PREVIOUS.ordinal()]  = this.classNo[VersionRequired.CURRENT.ordinal()];
           this.penNo[VersionRequired.PREVIOUS.ordinal()] = this.penNo[VersionRequired.CURRENT.ordinal()];
           this.dirtyBit = false;
        } else {
           this.dirtyBit = true; 
        }
    }

    public int getPenNo(VersionRequired vr) {
        return this.penNo[vr.ordinal()];
    }
  
    public int getClassNo(VersionRequired vr) {
        return this.classNo[vr.ordinal()];
    }
    
    private String getClassNoStr(VersionRequired vr){
        return String.valueOf(this.classNo[vr.ordinal()]);
    }
    
    private String getPenNoStr(VersionRequired vr){
        return String.valueOf(this.penNo[vr.ordinal()]);
    }

    @Override
    public String toListString(String formatString) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ShowEntry getData(ResultSet rs) throws SQLException {
        setPenAndClassNo(rs.getInt("pen_no"), rs.getInt("classNo"));
        super.getData();
        return this;
    }

    @Override
    public int performUpdate() {
        int results = DBA.updateSQL(String.format(
                "UPDATE entries SET pen_no = %d, class_no = %d "
                        + "WHERE pen_no = %d, class_no = %d", 
                this.getPenNo(VersionRequired.CURRENT),
                this.getClassNo(VersionRequired.CURRENT),
                this.getPenNo(VersionRequired.PREVIOUS),this.getClassNo(VersionRequired.PREVIOUS)));
        this.setDirty(false);
        return results;
    }

    @Override
    public int performDelete() {
        int results =
            DBA.updateSQL(String.format(
               "DELETE FROM entries WHERE pen_no = %d AND class_no %d",
               this.getPenNo(VersionRequired.PREVIOUS),
               this.getClassNo(VersionRequired.PREVIOUS)
               ));
       this.setReadyToDelete(false);
       return results;
   }

    @Override
    public int performInsert() {
        int results = DBA.updateSQL(String.format(
               "INSERT INTO entries (pen_no, class_no) VALUES (%d,%d)", 
                this.getPenNo(VersionRequired.CURRENT),
                this.getClassNo(VersionRequired.CURRENT)));
        this.setNewItem(false);
        return results;
    }

    @Override
    public ShowEntry performRead() {
        ResultSet rs = DBA.executeSQL(String.format(
                "SELECT * FROM entries WHERE id = %d",this.penNo));
        try {
            rs.next();
            return this.getData(rs);
        } catch (SQLException ex) {
            Logger.getLogger(Colour.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
}
