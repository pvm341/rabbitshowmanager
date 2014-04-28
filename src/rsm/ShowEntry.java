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
        this.classNo[0]=0;
        this.classNo[1]=0;
        this.penNo[0] = 0;
        this.penNo[1] = 0;
    }

    public ShowEntry(int i, int i0) {
       this.classNo[0] = i;
       this.classNo[1] = this.classNo[0];
       this.penNo[0] = i0;
       this.penNo[1] = this.penNo[0];
    }

    
    public void setPenAndClassNo(int penNo, int classNo) {
        if (! this.dirtyBit) {
            this.classNo[1] = this.classNo[0];
            this.penNo[1] = this.penNo[0];
        }
        this.classNo[0] = classNo;
        this.penNo[0] = penNo;
        if (this.classNo[1] == 0 && this.penNo[1] == 0){
           this.classNo[1]  = this.classNo[0];
           this.penNo[1] = this.penNo[0];
           this.dirtyBit = false;
        } else {
           this.dirtyBit = true; 
        }
    }

    public int getPenNo(boolean current) {
        return current ? this.penNo[0]:this.penNo[1];
    }
  
    public int getClassNo(boolean current) {
        return current ? this.classNo[0]:this.classNo[1];
    }
    
    private String getClassNoStr(boolean current){
        return String.valueOf(this.classNo[current?0:1]);
    }
    
    private String getPenNoStr(boolean current){
        return String.valueOf(this.penNo[current?0:1]);
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
    public void performUpdate() {
        DBA.updateSQL(String.format(
                "UPDATE entries SET pen_no = %d, class_no = %d "
                        + "WHERE pen_no = %d, class_no = %d", 
                this.getPenNo(true),this.getClassNo(true),
                this.getPenNo(false),this.getClassNo(false)));
        this.setDirty(false);
    }

    @Override
    public void performDelete() {
       DBA.updateSQL(String.format(
               "DELETE FROM entries WHERE pen_no = %d AND class_no %d",
               this.getPenNo(false),
               this.getClassNo(false)
               ));
       this.setReadyToDelete(false);
   }

    @Override
    public void performInsert() {
        DBA.updateSQL(String.format(
               "INSERT INTO entries (pen_no, class_no) VALUES (%d,%d)", 
                this.getPenNo(true),
                this.getClassNo(true)));
        this.setNewItem(false);
    }

    @Override
    public ShowEntry performRead() {
        ResultSet rs = DBA.executeSQL(String.format(
                "SELECT * FROM entries WHERE id = %d",this.penNo));
        try {
            return this.getData(rs);
        } catch (SQLException ex) {
            Logger.getLogger(Colour.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
}
