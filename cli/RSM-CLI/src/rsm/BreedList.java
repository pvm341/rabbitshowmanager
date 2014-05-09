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
import java.util.Vector;

/**
 *
 * @author paul
 */
public class BreedList extends BaseDataList  {
    Vector<Breed> breedList;
    
    public BreedList(){
        breedList = new Vector<Breed>();
        
    }

    public void readBreedList() throws SQLException{
        ResultSet rs = DBAccess.executeSQL("SELECT * FROM breeds");
        ResultSetMetaData rsmd = DBAccess.getColumns(rs);
        formatString = "%3d - ";
        int olength, nlength;
        heading      = "       ";
        
        nlength = heading.length();
        for (int idx = 1; idx <= rsmd.getColumnCount(); idx++ ){
            olength = nlength;
            heading += rsmd.getColumnName(idx);
            nlength = heading.length();
            if ((idx) < rsmd.getColumnCount()){
                heading += " ";
            }
            if (idx==1){
                formatString += "%3d ";
            } else {
                formatString += "%-"+Integer.toString(nlength-olength)+"s";
            }
            //System.err.printf("Column name %s column type %d\n",rsmd.getColumnName(idx),rsmd.getColumnType(idx));
        }
        while (rs.next()){
            Breed breed = new Breed();
            breed.setId(rs.getInt("id"));
            breed.setAdultAge(rs.getInt("adult_age"));
            breed.setTopPenReq(rs.getBoolean("top_pen_req"));
            breed.setSection(rs.getInt("section"));
            breed.setBreed(rs.getString("breed"));
            breed.setDirty(false);
            breedList.add(breed);
        }
    }

    public void displayList(){
        Screen.clear("Breed List");

    }
    public void setStart(int start) {
        this.start = start;
    }
    
    public void setPageLength(int pageLength){
        this.pageLength = pageLength;
    }
}
