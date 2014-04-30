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

import java.util.Vector;

/**
 *
 * @author paul
 */
public class BreedColourList extends BaseDataList {

    public BreedColourList () {
        list = new Vector<BreedColour>();
    }

    @Override
    public void writeList() {
        BreedColour breedColour = new BreedColour();
        super.writeList(this, breedColour);
//         for (Iterator it = list.iterator(); it.hasNext();) {
//             = (BreedColour) it.next();
//            if (breedColour.isDirty()){
//                DBAccess.updateSQL(String.format(updateSQLFmt,breedColour.getBreedId(true),breedColour.getColourId(true),breedColour.getBreedId(false),breedColour.getColourId(false)));
//                breedColour.setDirty(false);
//            } else if (breedColour.isReadyToDelete()) { 
//                // update done set to clean in the list
//                DBAccess.updateSQL(String.format(deleteSQLFmt,breedColour.getBreedId(true),breedColour.getColourId(true)));
//                // leave Ready to delete until the end of this 
//            } else if (breedColour.isNewItem()){ 
//                DBAccess.updateSQL(String.format(insertSQLFmt,breedColour.getBreedId(true),breedColour.getColourId(true)));
//                // insert complete now clear new item status
//                breedColour.setNewItem(false);
//            }
//        }
    }
    
    @Override
    public int findInListById(int reqId) {
        int found = -1;
        BreedColour instance = null;
        for (int idx=0; found==-1 && idx<list.size(); idx++){
           instance = (BreedColour) list.get(idx);
           found = instance.getBreedId(VersionRequired.CURRENT) == reqId?idx:-1; 
        }
        return found;
    }

    /**
     *
     * @param searchRecord the value of searchRecord
     * @return the boolean
     */
    public boolean isAlreadyInList(BreedColour searchRecord) {
        boolean found = false;
        for (BreedColour bc : (Vector<BreedColour>) list){
            found = bc.getBreedId(VersionRequired.CURRENT) == searchRecord.getBreedId(VersionRequired.CURRENT) &&
                    bc.getColourId(VersionRequired.CURRENT) == searchRecord.getColourId(VersionRequired.CURRENT);
            if (found)
                break;
        }
        return found;
    }
    
    public boolean isAlreadyInList(int breedId, int colourId){
        BreedColour bc = new BreedColour();
        bc.setBreedAndColourIds(breedId, colourId);
        return isAlreadyInList(bc);
    }

    public void readList(HeaderRequired hr) {
        BreedColour breedColour = new BreedColour();
        super.readList(hr,breedColour, "breedcolours",null,"breed_id"); 
    }
    
}

