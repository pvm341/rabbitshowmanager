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
public class ExhibitorAgeList extends BaseDataList implements DBListInterface {
    
    public ExhibitorAgeList(){
        list = new Vector<ExhibitorAge>();
    }
    
    @Override
    public int findInListById(int reqId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void readList(HeaderRequired hr) {
        ExhibitorAge exhibitorAge = new ExhibitorAge();
        super.readList(hr,exhibitorAge,"human_genders",null,"id");
    }

    @Override
    public void writeList() {
        ExhibitorAge exhibitorAge = new ExhibitorAge();
        super.writeList(this, exhibitorAge);
    }

    @Override
    public BaseDataItem findInListWithId(int reqId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean isAlreadyInTheList(Object requiredItem) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
