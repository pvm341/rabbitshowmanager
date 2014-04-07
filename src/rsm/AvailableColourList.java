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
import javax.swing.JComboBox;

/**
 *
 * @author paul
 */
public class AvailableColourList {
    private Vector<AvailableColour> availableColours;
        
    public AvailableColourList() {
        this.availableColours = new Vector<AvailableColour>(); 
    }
    
    public Vector<AvailableColour> getAll(){
        return availableColours;
    }
    
    /**
     * gets the list of colours for a breed from the database
     * @param breed the breedId to select the colours for.
     */
    public void getColoursForBreed(int breed) {
        ResultSet rs;
        availableColours.clear();
        rs = DBAccess.executeSQL("SELECT colours.id, colours.colour, colours.abbrev "
                + "FROM breeds, colours, breedcolours "
                + "WHERE breeds.id = breedcolours.breed_id "
                + "AND colours.id = breedcolours.colour_id "
                + "AND breeds.id = " + Integer.toString(breed));
        try {
            while (rs.next()) {
                this.availableColours.add(new AvailableColour(rs));
            }
        } catch (SQLException ex) {
            Logger.getLogger(AvailableColourList.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
   
    public AvailableColour getTheColourFromIdx(int idx){
        return availableColours.get(idx);
    }
    
    public void add(Colour colour){
        this.availableColours.add(new AvailableColour(colour));
    }
    
    public int getSize() {
        return availableColours.size();
    }
    
    public void setUnavailable(int... list){
        for (int count = 0; count<list.length; count++){
            availableColours.get(list[count]).setAvailable(false);
        }
    }
    
    public int getAvailable(){
        int count=0;
        for (int idx = 0; idx < availableColours.size(); idx++ ){
            if (availableColours.get(idx).isAvailable()){
                count++;
            } else {
                System.out.printf("Unavailable - %s - %s\n",availableColours.get(idx).getColour(),availableColours.get(idx).getAbbrev());
            }
        }
        return count;
    }
    
    public void adjustComboBox(JComboBox comboBox){
        int selectedItem = comboBox.getSelectedIndex();
        if (availableColours.size()>1){
            comboBox.removeAllItems();
            for (AvailableColour ac : availableColours){
                if (ac.isAvailable()){
                    comboBox.addItem(ac.getColour());
                }
            }
            while(comboBox.getItemCount()<=selectedItem){
                selectedItem--;
            }
            if (selectedItem<0){
                selectedItem = 0;
            }
            comboBox.setSelectedIndex(selectedItem);
        } else {
            comboBox.removeAllItems();
            availableColours.get(0).getColour();
            comboBox.setSelectedIndex(0);
        }
    }
    
    public int adjustSelectedItemOfComboBox(JComboBox comboBox, int requiredItem){
        int selectedItem = comboBox.getSelectedIndex();
        int idx = 0;
        while (selectedItem < requiredItem){
            if (!availableColours.get(idx).isAvailable()){
                selectedItem++;
            }
        }
        return selectedItem;
    }

    public void clear() {
        this.availableColours.clear();
    }

    Object get(int idx) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
}
