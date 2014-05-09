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
public class BaseDataItem {
    protected boolean dirtyBit;
    protected boolean deleteOnWrite;
    
    public BaseDataItem() {
        deleteOnWrite = false;
        dirtyBit = false;
    }

    public boolean isDirty() {
        return dirtyBit;
    }

    public void setDirty(boolean dirty) {
        this.dirtyBit = dirty;
    }

    public boolean isDeleteOnWrite() {
        return deleteOnWrite;
    }

    public void setDeleteOnWrite(boolean deleteOnWrite) {
        this.deleteOnWrite = deleteOnWrite;
    }
    
    public String toListString(){
        return null;
    }
}
