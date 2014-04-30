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
class ExhibitorList extends BaseDataList implements DBListInterface{

    
    public ExhibitorList() {
        list = new Vector<ExhibitorList>();
    }

    @Override
    public int findInListById(int reqId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void readList(HeaderRequired hr) {
        Exhibitor exhibitor = new Exhibitor();
        super.readList(hr, exhibitor, "exhibitors", null, "id");
    }

    @Override
    public void writeList() {
        Exhibitor exhibitor = new Exhibitor();
        super.writeList(this, exhibitor);
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
