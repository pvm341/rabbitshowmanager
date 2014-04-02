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

/**
 *
 * @author paul
 */
public class Entry implements DBInterface {
    private int classNo;
    private int penNo;
     

    public Entry() {
        
    }
    public int getClassNo() {
        return classNo;
    }

    public void setClassNo(int classNo) {
        this.classNo = classNo;
    }

    public int getPenNo() {
        return penNo;
    }

    public void setPenNo(int penNo) {
        this.penNo = penNo;
    }

    
    @Override
    public void deleteRecord() {
        String where = String.format(" WHERE class_no = %d and pen_no = %d ", this.classNo,this.penNo);
        if (DBAccess.isExistingRec("entries", where)){
            DBAccess.updateSQL("DELETE FROM breeds "+where); 
        }    
    }

    @Override
    public void readRecord(int recNo) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void writeRecord() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
