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
public class ShowClass {
    private int classNo; 
    private String name;
    private int breed;
    private boolean breedClass; 
    private int section; 
    private boolean standard;
    private boolean membersOnly; 
    private boolean breedersOnly; 
    private boolean upSideDown;
    private int exhibitAge; 
    private int exhibitGender; 
    private int exhibitorAge; 
    private int exhibitorGender; 
    private int[] results = new int[7]; 
    
    public ShowClass() {
        this.classNo = 100;
        this.breedClass=true;
        this.breed=1;
        this.breedersOnly=false;
        this.membersOnly=false;
        this.upSideDown=false;
        this.standard = false;
    }
    
    public ShowClass(ShowClassEditor sce){
      //  this.classNo = Integer.valueOf(sce.edtClassNo.getText());
        
    }
    private String val(boolean b){
        return b?"true":"false";
    }
    public boolean insertRecord(){
        String sql = String.format("INSERT INTO showclasses (class_no,name,breed,breed_class,section,members_only,breeders_only,upsidedown,exhibit_age,exhibit_gender,exhibitor_age,exhibitor_gender) "
                +"values (%d,\'%s\',%d,%s,%d,%s,%s,%s,%d,%d,%d,%d)",this.classNo,
                this.name,this.breed,val(this.breedClass),this.section,
                val(this.membersOnly),val(this.breedersOnly),val(this.upSideDown),
                this.exhibitAge,this.exhibitGender,this.exhibitorAge,this.exhibitorGender);
        System.out.println(sql);
        return false;
    }
    
    public boolean updateRecord(){
        String sql = String.format("UPDATE showclasses  SET name = \'%s\',breed = %d,breed_class = %s,section = %d,members_only = %s,breeders_only = %s,upsidedown = %s,exhibit_age = %d,exhibit_gender = %d,exhibitor_age = %d,exhibitor_gender = %d "
                +" WHERE class_no = %d",
                this.name,this.breed,val(this.breedClass),this.section,
                val(this.membersOnly),val(this.breedersOnly),val(this.upSideDown),
                this.exhibitAge,this.exhibitGender,this.exhibitorAge,this.exhibitorGender,this.classNo);
        System.out.println(sql);
        return false;
    }

    public int getClassNo() {
        return classNo;
    }

    public void setClassNo(int classNo) {
        this.classNo = classNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBreed() {
        return breed;
    }

    public void setBreed(int breed) {
        this.breed = breed;
    }

    public boolean isBreedClass() {
        return breedClass;
    }

    public void setBreedClass(boolean breedClass) {
        this.breedClass = breedClass;
    }

    public int getSection() {
        return section;
    }

    public void setSection(int section) {
        this.section = section;
    }

    public boolean isMembersOnly() {
        return membersOnly;
    }

    public void setMembersOnly(boolean membersOnly) {
        this.membersOnly = membersOnly;
    }

    public boolean isBreedersOnly() {
        return breedersOnly;
    }

    public void setBreedersOnly(boolean breedersOnly) {
        this.breedersOnly = breedersOnly;
    }

    public boolean isUpSideDown() {
        return upSideDown;
    }

    public void setUpSideDown(boolean upSideDown) {
        this.upSideDown = upSideDown;
    }

    public int getExhibitAge() {
        return exhibitAge;
    }

    public void setExhibitAge(int exhibitAge) {
        this.exhibitAge = exhibitAge;
    }

    public int getExhibitGender() {
        return exhibitGender;
    }

    public void setExhibitGender(int exhibitGender) {
        this.exhibitGender = exhibitGender;
    }

    public int getExhibitorAge() {
        return exhibitorAge;
    }

    public void setExhibitorAge(int exhibitorAge) {
        this.exhibitorAge = exhibitorAge;
    }

    public int getExhibitorGender() {
        return exhibitorGender;
    }

    public void setExhibitorGender(int exhibitorGender) {
        this.exhibitorGender = exhibitorGender;
    }

    public int[] getResults() {
        return results;
    }

    public void setResults(int[] results) {
        this.results = results;
    }

    public boolean isStandard() {
        return standard;
    }

    public void setStandard(boolean standard) {
        this.standard = standard;
    }
 }