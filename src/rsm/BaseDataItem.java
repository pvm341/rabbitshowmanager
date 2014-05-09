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

/**
 *
 * @author paul
 */
public abstract class BaseDataItem implements DBInterface {
    protected boolean dirtyBit;
    protected boolean readyToDelete;
    protected boolean newItem;
    protected boolean cancelNewitem;
    
    public BaseDataItem() {
        this.readyToDelete = false;
        this.dirtyBit = false;
        this.newItem = true;
        this.cancelNewitem = false;
    }

    public boolean isDirty() {
        return this.dirtyBit;
    }

    public void setDirty(boolean dirty) {
        this.dirtyBit = dirty;
    }

    public char getStatusChar(){
        return this.dirtyBit?'U':this.readyToDelete?'D':this.cancelNewitem?'C':this.newItem?'N':'*';
    }
    
    public boolean isReadyToDelete() {
        return this.readyToDelete;
    }
    
    public void setReadyToDelete(boolean readyToDelete) {
        this.readyToDelete = readyToDelete;
    }
    
    public boolean isNewItem(){
        return this.newItem;
    }
    
    public void setNewItem(boolean newItem){
        this.newItem = newItem;
    }
    
    public boolean isCancelledNewItem(){
        return this.newItem;
    }
    
    public void setCancelNewItem(boolean newItem){
        this.newItem = newItem;
    }
    
    public void getData() {
        this.readyToDelete = false;
        this.dirtyBit = false;
        this.newItem = false; 
        this.cancelNewitem = false;
    }
    
    
    public abstract String toListString(String formatString);

    public abstract int performUpdate();

    public abstract int performDelete(); 

    public abstract int performInsert(); 
    
    public abstract BaseDataItem performRead();

}
