/*
 * Here comes the text of your license
 * Each line should be prefixed with  * 
 */

package rsm;

import java.util.Vector;

/**
 *
 * @author paul
 */
class ExhibitList extends BaseDataList implements DBListInterface {

    public ExhibitList() {
        list = new Vector<Exhibit>();
    }

    @Override
    public int findInListById(int reqId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void readList(HeaderRequired hr) {
        Exhibit exhibit = new Exhibit(); 
        super.readList(hr,exhibit, "exhibit_genders",null,null);
    }

    @Override
    public void writeList() {
        Exhibit exhibit = new Exhibit();     
        super.writeList(this, exhibit);
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
