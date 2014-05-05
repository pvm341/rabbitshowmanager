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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author paul
 */
public class Exhibit extends BaseDataItem implements DBInterface{
    private final static String ringNumberRexp = 
              "[0-9]{2}"                    // two year digits
            + "[A-LX]{1}"                   // ring size
            + "("                           // 5 digits with at least 
                                            // one digit that is not zero
            + "[0-9]{4}[1-9]{1}|"           // 00001 
            + "[0-9]{3}[1-9]{1}[0-9]{1}|"   // 00010
            + "[0-9]{2}[1-9]{1}[0-9]{2}|"   // 00100
            + "[0-9]{1}[1-9]{1}[0-9]{3}|"   // 01000
            + "[1-9]{1}[0-9]{4}"            // 10000
            + ")";
    private int penNo;
    private String ringNumber;  
    private int breedClass;
    private int gender;
    private int ageGroup;
    private boolean breedByExhibitor;
    private int exhibitorId;
    
    Exhibit() {
        
    }

    public int getPenNo() {
        return penNo;
    }

    public void setPenNo(int penNo) {
        this.penNo = penNo;
    }

    public String getRingNumber() {
        return ringNumber;
    }

    public void setRingNumber(String ringNumber) {
        if (checkRingNumber(ringNumber)) {
            this.ringNumber = ringNumber;
        } else {
           this.ringNumber = "Invalid"; 
        }
    }

    public int getBreedClass() {
        return breedClass;
    }

    public void setBreedClass(int breedClass) {
        this.breedClass = breedClass;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public int getAgeGroup() {
        return ageGroup;
    }

    public void setAgeGroup(int ageGroup) {
        this.ageGroup = ageGroup;
    }

    public boolean isBreedByExhibitor() {
        return breedByExhibitor;
    }

    public void setBreedByExhibitor(boolean breedByExhibitor) {
        this.breedByExhibitor = breedByExhibitor;
    }

    public int getExhibitorId() {
        return exhibitorId;
    }

    public void setExhibitorId(int exhibitorId) {
        this.exhibitorId = exhibitorId;
    }

    @Override
    public String toListString(String formatString) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Exhibit getData(ResultSet rs) throws SQLException {
        super.getData();
        return this;
    }

    @Override
    public void performUpdate() {
        
    }

    @Override
    public void performDelete() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void performInsert() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Exhibit performRead() {
             ResultSet rs = DBA.executeSQL(String.format(
                "SELECT * FROM exhibits WHERE pen_no = %d",this.penNo));
        try {
            return this.getData(rs);
        } catch (SQLException ex) {
            Logger.getLogger(Colour.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
}
    /** 
     * takes a user entered ring number and compares it against the format
     * hard code as a string constant at the top of the file.
     * @param ringNumber
     * @return 
     */
    public static boolean checkRingNumber(String ringNumber) {
        return ringNumber.matches(ringNumberRexp);
    }
    
}
