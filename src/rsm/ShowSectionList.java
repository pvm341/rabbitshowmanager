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
public class ShowSectionList extends BaseDataList implements DBListInterface {
 
    public ShowSectionList(){
        list = new Vector<ShowSection>();
    }

    @Override
    public int findInListById(int reqId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


    @Override
    public void writeList() {
        ShowSection showSection = new ShowSection();
        super.writeList(this, showSection);
    }

    @Override
    public void readList(HeaderRequired hr)  {
        ShowSection showSection = new ShowSection();
        super.readList(hr, showSection, "showsections", null, null);
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
