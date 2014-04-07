/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package rsm;

/**
 *
 * @author paul
 */
class ShowClass {
    private int classNo;
    private String name;
    private boolean breedClass, breedersOnly, membersOnly, upsidedown;
    private int exhibtAge, exhibitGender, exhibitorAge, exhibitorGender;
    private int[] results = new int[7];

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

    public void setStandard(boolean selected) {
       
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
    
}
