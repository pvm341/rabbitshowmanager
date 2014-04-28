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
public class AvailableColoursList {
    private Vector<AvailableColour> availableColours;
        
    public AvailableColoursList() {
        this.availableColours = new Vector<AvailableColour>(); 
    }
    
    public Vector<AvailableColour> getAll(){
        return availableColours;
    }
    
    /**
     * gets the list of colours for a breed from the database
     * @param breedColourList
     * @param breed the breedId to select the colours for.
     */
    public void getColoursForBreed(BreedColourList breedColourList, ColourList colourList, int breed) {
        
        availableColours.clear();
        for (BreedColour breedColour : (Vector<BreedColour>) breedColourList.list){
            if (breedColour.getBreedId(VersionRequired.CURRENT) == breed){
                Colour colour = colourList.get(breedColour.getColourId(VersionRequired.CURRENT));
                this.availableColours.add(new AvailableColour(colour));
            }
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
    
    public int getAvailableQty(){
        int count=0;
        for (int idx = 0; idx < availableColours.size(); idx++ ){
            AvailableColour ac = availableColours.get(idx); 
            if ( ac.isAvailable() && !ac.isSelected()){
                count++;
            } 
        }
        return count;
    }
    
    
    
    public void adjustComboBox(JComboBox comboBox){
        int selectedItem = comboBox.getSelectedIndex();
        if (availableColours.size()>1){
            comboBox.removeAllItems();
            for (AvailableColour ac : availableColours){
                if (!ac.isSelected() && ac.isAvailable()){
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


    
}
