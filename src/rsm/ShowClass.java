/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
class ShowClass implements DBInterface{
    private int classNo;
    private String name;
    private boolean breedClass, 
                    breedersOnly, 
                    membersOnly, 
                    upsidedown;
    private int exhibtAge, 
                exhibitGender, 
                exhibitorAge, 
                exhibitorGender,
                section;
    private int[] results = new int[7];
    private Vector<Integer> colours;

    ShowClass() {
        
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
       this.exhibtAge = exhibitAge;
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

    public int[] getResults() {
        return results;
    }

    public void setResults(int[] results) {
        this.results = results;
    }

    public Vector<Integer> getColours() {
        return colours;
    }
    
    public void addAColour(int colour) {
        this.colours.add(colour);
    }
    
    public void delAColour(int colour){
        this.colours.remove(colour);
    }
    
    @Override
    public void deleteRecord() {
        String where = String.format("class_no = %d", this.classNo);
        if (DBAccess.isExistingRec("colours", where)){
            DBAccess.updateSQL("DELETE FROM colours WHERE "+where); 
        }    
    }

    @Override
    public void readRecord(int recNo) {
        ResultSet rs;
        int faultAt = 0;
        String sql = String.format("SELECT * FROM colours WHERE id = %d", recNo);
        rs = DBAccess.executeSQL(sql);
        try {
            rs.next();
            this.classNo = rs.getInt("class_no");
            faultAt++;
            this.breedClass = rs.getBoolean("breed_class");
            faultAt++;
            this.name = rs.getString("name");
            faultAt++;
            this.breedersOnly = rs.getBoolean("breeders_only");
            faultAt++;
            this.section = rs.getInt("section");
            faultAt++;
            this.upsidedown = rs.getBoolean("upsidedown");
            faultAt++;
            this.membersOnly = rs.getBoolean("members_only");
            faultAt++;
            this.exhibitGender = rs.getInt("exhibit_gender");
            faultAt++;
            this.exhibtAge = rs.getInt("exhibit_age");
            faultAt++;
            this.exhibitorGender = rs.getInt("exhibitor_gender");
            faultAt++;
            this.exhibitorAge = rs.getInt("exhibitor_age");
            //faultAt++;
            //this.results = rs.getArray("results");
            for (int idx = 0; idx<7; idx++){
                faultAt++;
                this.results[idx] = rs.getInt("results["+Integer.toString(idx)+"]");
            }
        } catch (SQLException ex) {
            System.out.printf("Error at fault no = %d", faultAt);
            Logger.getLogger(Colour.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void writeRecord() {
        String sql;
        String where = String.format("class_no = %d", this.classNo);
        if (DBAccess.isExistingRec("colours",where)){
            sql=String.format("UPDATE showclasses SET "
                    + "breed_class = %s,"
                    + "name = \'%s\',"
                    + "breeders_only = %s,"
                    + "section = %d,"
                    + "upsidedown = %s,"
                    + "members_only = %s,"
                    + "exhibit_gender = %d,"
                    + "exhibit_age = %d, "
                    + "exhibitor_gender = %d,"
                    + "exhibitor_age, = %d"
                    + "results = {%d,%d,%d,%d,%d,%d,%d} "
                    + "WHERE %s",
                    this.breedClass,
                    this.name,
                    this.breedersOnly,
                    this.section,
                    this.upsidedown,
                    this.membersOnly,
                    this.exhibitGender,
                    this.exhibtAge,
                    this.exhibitorGender,
                    this.exhibitorAge,
                    this.results[0],
                    this.results[1],
                    this.results[2],
                    this.results[3],
                    this.results[4],
                    this.results[5],
                    this.results[6],
                    where); 
        } else {
            this.classNo = DBAccess.getNewKey("showclasses", "class_no");
            sql=String.format("INSERT INTO showclasses ("
                    + "class_no,"
                    + "breed_class,"
                    + "name,"
                    + "breeders_only,"
                    + "section,"
                    + "upsidedown,"
                    + "members_only,"
                    + "exhibit_gender,"
                    + "exhibit_age, "
                    + "exhibitor_gender,"
                    + "exhibitor_age,"
                    + "results"
                    + ") VALUES ("
                    + "%d,"     // class_no
                    + "%d,"     // breed_class
                    + "\'%s\'," // name 
                    + "%s,"     // breeders_only
                    + "%d,"     // section
                    + "%s,"     // upsidedown
                    + "%s,"     // members_only
                    + "%d,"     // exhibit_gender
                    + "%d,"     // exhibit_age
                    + "%d,"     // exhibitor_gender
                    + "%d,"     // exhibitor_age
                    + "{%d,%d,%d,%d,%d,%d,%d})", // results 
                    this.classNo,
                    this.breedClass,
                    this.name,
                    this.breedersOnly,
                    this.section,
                    this.upsidedown,
                    this.membersOnly,
                    this.exhibitGender,
                    this.exhibtAge,
                    this.exhibitorGender,
                    this.exhibitorAge,
                    this.results[0],
                    this.results[1],
                    this.results[2],
                    this.results[3],
                    this.results[4],
                    this.results[5],
                    this.results[6]);
        }
        DBAccess.updateSQL(sql);
    }
}
