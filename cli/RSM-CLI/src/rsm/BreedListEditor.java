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

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author paul
 */
class BreedListEditor {
    private BreedList breedList;
    
    public BreedListEditor(){
        breedList = new BreedList();
        try {
            breedList.readBreedList();
        } catch (SQLException ex) {
            Logger.getLogger(BreedListEditor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void display(int start, int pageLength){
        breedList.setStart(start);
        breedList.setPageLength(pageLength);
        breedList.displayList();
        Screen.getReply("<>q",0,pageLength);
    }
    
}
