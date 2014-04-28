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
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author paul
 */
public class BreedList extends BaseDataList implements DBListInterface {
    private final String deleteSQLFmt;
    private final String updateSQLFmt;
    private final String insertSQLFmt;
    private final String selectSQL;
    
    public BreedList(){
        deleteSQLFmt = "DELETE FROM breeds WHERE id = %d";
        updateSQLFmt = "UPDATE breeds SET adult_age = %d, top_pen_req = %s, section = %d, breed = \'%s\' WHERE id = %d";
        insertSQLFmt = "INSERT INTO breeds (id,adult_age,top_pen_req,section,breed) VALUES (%d,%d,%s,%d,\'%s\')";
        selectSQL    = "SELECT * FROM breeds ORDER BY id";
        list = new Vector<Breed>();
    }


    @Override
    public void readList(boolean headerRequired) {
       Breed breed = new Breed();
       super.readList(headerRequired, breed,"breeds",null,"id");   
    }


    @Override
    public void writeList(){
        for (Iterator it = list.iterator(); it.hasNext();) {
            Breed breed = (Breed) it.next();
            if (breed.isDirty()){
                DBAccess.updateSQL(String.format(updateSQLFmt,breed.getId()));
            } else if (breed.isReadyToDelete()) { 
                // check for constraints and delete them
                if (DBAccess.getRecordCount("breedcolours", "breed_id ="+Integer.toString(breed.getId()))>0){
                    DBAccess.updateSQL("DELETE FROM breedcolours WHERE breed_id ="+Integer.toString(breed.getId()));
                }
                DBAccess.updateSQL(String.format(deleteSQLFmt,breed.getAdultAge(),breed.isTopPenReqStr(),breed.getSection(),breed.getBreed(),breed.getId()));
                // update done set to clean in the list
                breed.setDirty(false);
            } else if (breed.isNewItem()){ 
                DBAccess.updateSQL(String.format(insertSQLFmt,breed.getId(),breed.getAdultAge(),breed.isTopPenReqStr(),breed.getSection(),breed.getBreed()));
                // insert complete now clear new item status
                breed.setNewItem(false);
                // New Breed therefore need to create breedcolour new breed anycolour record in breedcolours 
                // others will have to be entered manually on another form
                DBAccess.updateSQL("INSERT INTO breedcolours (breed_id,colour_id) VALUES ("+Integer.toString(breed.getId())+",1)");
            }
        }
        
//        for (int idx=list.size()-1;idx>=0;idx--){
//            Breed breed = (Breed) list.get(idx);
//            if (breed.isReadyToDelete()){
//                list.remove(idx);
//            }
//        }
    }
    
//    public Breed get(int index){
//        if (index>=0 && index < list.size()){
//          return (Breed) list.get(index);
//        }
//        return null;
//    }
    
//    public void set(int index, Breed breed){
//        if (index >=0 && index < list.size())
//            list.set(index, breed);
//    }
    
//    public void add(Breed breed){
//        list.add(breed);
//    }

    
//    public Breed remove(int index){
//        if (index >=0 && index < list.size())
//            list.remove(index);
//    }
    
//    @Override
//    public String getHeader() {
//        return header;
//    }
//    
//    @Override
//    public String getFormatStr() {
//        return formatString;
//    }

    @Override
    public int findInListById(int reqId) {
        int found = -1;
        Breed breed = null;
        for (int idx=0; found==-1 && idx<list.size(); idx++){
           breed = (Breed) list.get(idx);
           found = breed.getId() == reqId?idx:-1; 
        }
        return found;
    }
  
}
