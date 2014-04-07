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
class BreedColourList {
    private Vector<BreedColour> bcl;
    
    public BreedColourList(){
        bcl = new Vector<BreedColour>();
    };

    public void readBcl() {
        ResultSet rs = DBAccess.executeSQL("SELECT * FROM breedcolours");
        try {
            while (rs.next()){
                bcl.add(new BreedColour(rs.getInt("breed_id"),rs.getInt("colour_id")));
            }
        } catch (SQLException ex) {
            Logger.getLogger(BreedColourList.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setBcl(Vector<BreedColour> bcl) {
        this.bcl = bcl;
    }

    public int size() {
        return bcl.size();
    }
    
    public AvailableColourList getAvailableColourListByBreedId(int breedId){
        return null;
    }

}
