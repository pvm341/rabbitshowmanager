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
public class ShowEntries extends BaseDataList implements DBListInterface {
    private final String deleteSQLFmt = "DELETE FROM showentries WHERE pen_no = %d AND class_no = %d";
    private final String updateSQLFmt = "UPDATE showentries SET breed_id = %d, colour_id = %d WHERE breed_id = %d AND colour_id = %d";
    private final String insertSQLFmt = "INSERT INTO showentries (breed_id,colour_id) VALUES (%d,%d)";
    private final String selectSQLOrder  = "SELECT * FROM showentries" ;
       

    public ShowEntries (){
        list = new Vector<ShowEntry>();
    }

    @Override
    public void writeList() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int findInListById(int reqId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void readListByPen(boolean headerRequired){
        ShowEntry dataItem = new ShowEntry();
        super.readList(headerRequired, dataItem, "showentries", null, "pen_no");
    }
    
    public void readListByClass(boolean headerRequired){
        ShowEntry dataItem = new ShowEntry();
        super.readList(headerRequired, dataItem, "showentries", null, "Class_no");
    }
    
    @Override
    public void readList(boolean headerRequired) {
        this.readListByClass(headerRequired);
    }
    
}
