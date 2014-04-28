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
class ShowClass extends BaseDataItem implements DBInterface{
    private int classNo;
    private String name;
    private boolean breedClass, breedersOnly, membersOnly, upsidedown; 
    private int breedId, exhibitAge, exhibitGender, exhibitorAge, 
                exhibitorGender, section;
    private int[] results = new int[7];
    private Vector<Integer> colours = null;

    ShowClass() {
        if (colours == null){
            colours = new Vector<Integer>();
        }
    }
    
    public int getClassNo() {
        return classNo;
    }

    public boolean isBreedersOnly() {
        return breedersOnly;
    }

    public boolean isMembersOnly() {
        return membersOnly;
    }

    public int getExhibitAge() {
        return exhibitAge;
    }

    public int getExhibitGender() {
        return exhibitGender;
    }

    public int getExhibitorAge() {
        return exhibitorAge;
    }

    public int getExhibitorGender() {
        return exhibitorGender;
    }

    public int[] getResults() {
        return results;
    }

    public boolean isBreedClass() {
        return breedClass;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setClassNo(int classNo) {
        this.classNo = classNo;
    }

    public void setBreedClass(boolean selected) {
        this.breedClass = selected;
    }

    public void setBreedersOnly(boolean selected) {
        this.breedersOnly = selected;
    }

    public void setUpSideDown(boolean selected) {
        this.upsidedown = selected;
    }

    public void setMembersOnly(boolean selected) {
        this.membersOnly = selected;
    }

    public void setExhibitAge(int exhibitAge) {
       this.exhibitAge = exhibitAge;
    }

    public void setExhibitGender(int exhibitGender ) {
        this.exhibitGender = exhibitGender;
    }

    public void setExhibitorAge(int exhibitorAge) {
        this.exhibitorAge = exhibitorAge;
    }

    public void setExhibitorGender(int exhibitorGender) {
        this.exhibitorGender = exhibitorGender;
    }
    
    public boolean isUpsidedown() {
        return upsidedown;
    }

    public void setUpsidedown(boolean upsidedown) {
        this.upsidedown = upsidedown;
    }
    
    public void setStandard(boolean value){
        if (value){
            this.upsidedown = !value;
            this.membersOnly = !value;
            this.breedersOnly = !value;
        }
    }
    public int getSection() {
        return section;
    }

    public void setSection(int section) {
        this.section = section;
    }

    public int getResults(int idx) {
        return results[idx];
    }

    public void setResults(int idx, int result) {
        this.results[idx] = result;
    }

    public Vector<Integer> getColours() {
        return colours;
    }
    
    public void addAColour(int colour) {
        this.colours.add(colour);
    }
    
    public void delAColour(int colour){
        for (int idx = 0; idx<this.colours.size(); idx++ ){
            if (colours.get(idx) == colour)
                this.colours.remove(idx);
        }
    }
    
    public int getBreedId() {
        return breedId;
    }

    public void setBreedId(int breedId) {
        this.breedId = breedId;
    }

    @Override
    public String toListString(String formatString) {
        String tableLine = String.format(formatString, 
                getStatusChar(),
                this.classNo,
                this.name,
                this.breedId,
                Boolean.toString(breedClass),
                Integer.toString(this.section),
                Boolean.toString(membersOnly),
                Boolean.toString(breedersOnly),
                Boolean.toString(upsidedown),
                Integer.toString(exhibitAge),
                Integer.toString(exhibitGender),
                Integer.toString(exhibitorAge),
                Integer.toString(exhibitorGender),
                Integer.toString(results[0]),
                Integer.toString(results[1]),
                Integer.toString(results[2]),
                Integer.toString(results[3]),
                Integer.toString(results[4]),
                Integer.toString(results[5]),
                Integer.toString(results[6]));                                              
        return tableLine;
    }

    @Override
    public ShowClass getData(ResultSet rs) throws SQLException {
        this.classNo = rs.getInt("class_no");
        this.breedClass = rs.getBoolean("breed_class");
        this.breedId = rs.getInt("breed");
        this.name = rs.getString("name");
        this.breedersOnly = rs.getBoolean("breeders_only");
        this.section = rs.getInt("section");
        this.upsidedown = rs.getBoolean("upsidedown");
        this.membersOnly = rs.getBoolean("members_only");
        this.exhibitGender = rs.getInt("exhibit_gender");
        this.exhibitAge = rs.getInt("exhibit_age");
        this.exhibitorGender = rs.getInt("exhibitor_gender");
        this.exhibitorAge = rs.getInt("exhibitor_age");
        this.results[0] = rs.getInt("results1");//.getArray("results");        
        this.results[1] = rs.getInt("results2");//.getArray("results");        
        this.results[2] = rs.getInt("results3");//.getArray("results");        
        this.results[3] = rs.getInt("results4");//.getArray("results");        
        this.results[4] = rs.getInt("results5");//.getArray("results");        
        this.results[5] = rs.getInt("results6");//.getArray("results");        
        this.results[6] = rs.getInt("results7");//.getArray("results");        
        super.getData();
        return this;
    }

    @Override
    public void performUpdate() {
        DBAccess.updateSQL(String.format(
                "UPDATE showclasses SET "
                    + "name = \'%s\',"
                    + "breed_class = %s,"
                    + "breeders_only = %s,"
                    + "upsidedown = %s,"
                    + "members_only = %s,"
                    + "section = %d,"
                    + "exhibit_gender = %d,"
                    + "exhibit_age = %d, "
                    + "exhibitor_gender = %d,"
                    + "exhibitor_age = %d,"
                    + "results1 = %d,"
                    + "results2 =%d,"
                    + "results3 = %d,"
                    + "results4 = %d,"
                    + "results5 = %d,"
                    + "results1 = %d,"
                    + "results1 = %d "
                    + "WHERE class_no = %d",
                    this.name,
                    Boolean.toString(this.breedClass),
                    Boolean.toString(this.breedersOnly),
                    Boolean.toString(this.upsidedown),
                    Boolean.toString(this.membersOnly),
                    this.section,
                    this.exhibitGender,
                    this.exhibitAge,
                    this.exhibitorGender,
                    this.exhibitorAge,
                    this.results[0],
                    this.results[1],
                    this.results[2],
                    this.results[3],
                    this.results[4],
                    this.results[5],
                    this.results[6],
                    this.classNo));
        this.setDirty(false);
    }

    @Override
    public void performDelete() {
        DBAccess.updateSQL(String.format("DELETE FROM showclasses WHERE class_no = %d",this.classNo));
        this.setReadyToDelete(false);
    }

    @Override
    public void performInsert() {
        DBAccess.updateSQL(String.format(
                "INSERT INTO showclasses ("
                    + "class_no,"
                    + "breed"
                    + "breed_class,"
                    + "breeders_only,"
                    + "upsidedown,"
                    + "members_only,"
                    + "name,"
                    + "section,"
                    + "exhibit_gender,"
                    + "exhibit_age, "
                    + "exhibitor_gender,"
                    + "exhibitor_age,"
                    + "results"
                    + ") VALUES ("
                    + "%d,"     // class_no
                    + "%d,"     // breedId
                    + "%s,"     // breed_class
                    + "%s,"     // breeders_only
                    + "%s,"     // upsidedown
                    + "%s,"     // members_only
                    + "\'%s\'," // name 
                    + "%d,"     // section
                    + "%d,"     // exhibit_gender
                    + "%d,"     // exhibit_age
                    + "%d,"     // exhibitor_gender
                    + "%d,"     // exhibitor_age
                    + "%d,"
                    + "%d,"
                    + "%d,"
                    + "%d,"
                    + "%d,"
                    + "%d,"
                    + "%d)", // results 
                    this.classNo,
                    this.breedId,
                    Boolean.toString(this.breedClass),
                    Boolean.toString(this.breedersOnly),
                    Boolean.toString(this.upsidedown),
                    Boolean.toString(this.membersOnly),
                    this.name,
                    this.section,
                    this.exhibitGender,
                    this.exhibitAge,
                    this.exhibitorGender,
                    this.exhibitorAge,
                    this.results[0],
                    this.results[1],
                    this.results[2],
                    this.results[3],
                    this.results[4],
                    this.results[5],
                    this.results[6]));
       this.setNewItem(false);
    }

    @Override
    public ShowClass performRead() {
        ResultSet rs = DBAccess.executeSQL(String.format(
                "SELECT * FROM showclasses WHERE class_no = %d",this.classNo));
        try {
            return this.getData(rs);
        } catch (SQLException ex) {
            Logger.getLogger(Colour.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

 }
