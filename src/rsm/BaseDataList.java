/*
 * Copyright (Cl) 2014 paul
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

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author paul
 */
public abstract class BaseDataList implements DBListInterface {
    private String updateSQLFmt, deleteSQLFmt, insertSQLFmt;
    protected Vector list;
    protected String header;
    protected String formatString;
    protected int selectedRecord;

    public BaseDataList() {
        //list = new Vector<>();
    }
    
    public String getHeader() {
        return this.header;
    }
    
    public String getFormatStr(){
        return this.formatString;
    }
    
    public BaseDataItem get(int index){
        if (index >=0 && index<list.size()){
            return (BaseDataItem) list.get(index);
        }
        return null;
    }
    
    public void set(int index, BaseDataItem replacement){
        if (index>=0 && index<list.size()){
           list.set(index, replacement);
        }
    }
    
    public void add(BaseDataItem dataToAdd){
        list.add(dataToAdd);
    }
    
    public BaseDataItem remove(int index) {
        if (index>=0 && index<list.size()){
            return (BaseDataItem) list.remove(index);
        }
        return  null;
    }
    
   
    public void readList(HeaderRequired hr, Object dataItem, 
        String table, String with, String orderBy ){
             ResultSet rs = DBA.executeSQL(String.format(
            "SELECT * FROM %s %s %s",table,
                with != null?String.format("WITH %s",with):" ",
                orderBy != null?String.format("ORDER BY %s", orderBy):" "));   
        try {
            if (hr == HeaderRequired.HEADERS){
                header = DBA.getHeader(rs);
                formatString = DBA.getFormatFromHeader(header);
                header = header.replace(';', ' ');
            }
            while (rs.next()){
                BaseDataItem x;// = new Object();
                if (dataItem instanceof Breed){
                    //x =  Breed();
                   // Breed omx = (Breed) dataItem
                    x = new Breed();
                    x.getData(rs);
                    dataItem = x;
                    //list.add(dataItem);
                } else if (dataItem instanceof Colour){
                    x = new Colour();
                    x.getData(rs);  
                    dataItem = x;
                } else if (dataItem instanceof BreedColour){
                    x = new BreedColour();
                    x.getData(rs);
                    dataItem = x;
                } else if (dataItem instanceof ExhibitAge){
                    x = new ExhibitAge();
                    x.getData(rs);
                    dataItem = x;
                } else if (dataItem instanceof ExhibitGender){
                    x = new ExhibitGender();
                    x.getData(rs);
                    dataItem = x;
                } else if (dataItem instanceof ExhibitorAge){
                    x = new ExhibitorAge();
                    x.getData(rs);
                    dataItem = x;                    
                } else if (dataItem instanceof ExhibitorGender){
                    x = new ExhibitorGender();
                    x.getData(rs);
                    dataItem = x;
                } else if (dataItem instanceof ShowClass){
                    x = new ShowClass();
                    x.getData(rs);
                    dataItem = x; 
                } else if (dataItem instanceof Exhibit){
                    x = new Exhibit();
                    x.getData(rs);
                    dataItem = x;
                } else if (dataItem instanceof Exhibitor){
                    x = new Exhibitor();
                    x.getData(rs);
                    dataItem = x;
                } else if (dataItem instanceof Judge){
                    x = new Judge();
                    x.getData(rs);
                    dataItem = x;
                } else if (dataItem instanceof ShowEntry){
                    x = new ShowEntry();
                    x.getData(rs);
                    dataItem = x;
                } else if (dataItem instanceof ShowSection){
                    x = new ShowSection();
                    x.getData(rs);
                    dataItem = x;
                }  
               
                list.add(dataItem);
            }
        } catch (SQLException ex) {
            Logger.getLogger(BreedList.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }
      
    public void writeList(BaseDataList theList, BaseDataItem paramDataItem){
        //BaseDataList dataList;
        BaseDataItem localDataItem = null;
        // locate the currect polymorph 
        for (Iterator it = list.iterator(); it.hasNext();) {
            if (paramDataItem instanceof Breed){
                 localDataItem  = (Breed)it.next();      
            } else if ( paramDataItem instanceof Colour){
                 localDataItem  = (Colour) it.next();
            } else if (paramDataItem instanceof BreedColour){
                 localDataItem  = (BreedColour) it.next();
            } else if (paramDataItem  instanceof ExhibitAge){
                 localDataItem  = (ExhibitAge) it.next();
            } else if (paramDataItem instanceof ExhibitGender){
                 localDataItem = (ExhibitGender) it.next();
            } else if (paramDataItem instanceof ExhibitorAge){
                 localDataItem = (ExhibitorAge) it.next();
            } else if (paramDataItem instanceof ExhibitorGender){
                 localDataItem = (ExhibitorGender) it.next();
            } else if (paramDataItem instanceof ShowClass){
                 localDataItem = (ShowClass) it.next();
            } else if (paramDataItem instanceof Exhibit){
                 localDataItem = (Exhibit) it.next();
            } else if (paramDataItem instanceof Exhibitor){
                 localDataItem = (Exhibitor) it.next();
            } else if (paramDataItem instanceof Judge){
                 localDataItem = (Judge) it.next();
            } else if (paramDataItem instanceof ShowEntry){
                 localDataItem = (ShowEntry) it.next();
            } else if (paramDataItem instanceof ShowSection){
                 localDataItem = (ShowSection) it.next();
            }  
            // found the correct polymorph now perform the process it 
            if (localDataItem.isDirty()){
                localDataItem.performUpdate();
            } else if (localDataItem.isReadyToDelete()) { 
                // delete will also delete any constrained records.
                localDataItem.performDelete();
            } else if (localDataItem.isNewItem() && localDataItem.isCancelledNewItem()){
                localDataItem.performInsert();
                localDataItem.setNewItem(false);
            }            
        }
    }

    public abstract int findInListById(int reqId);
    public abstract BaseDataItem findInListWithId(int reqId);
    public abstract boolean isAlreadyInTheList(Object requiredItem);
 
}
