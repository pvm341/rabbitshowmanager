/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package rsm;

import java.util.Iterator;
import java.util.Vector;

/**
 *
 * @author paul
 */
public class ColourList extends BaseDataList implements DBListInterface {

    
    public ColourList(){
        list = new Vector<Colour>();
    }
    
    public void readList(HeaderRequired hr) {
      Colour colour = new Colour();
      super.readList(hr, colour,"colours",null,null);
    }

    @Override
    public void writeList() {
        Colour colour = new Colour();
        super.writeList(this, colour);
   }

    public Colour get(int index) {
        if (index>=0 && index<list.size()){
            return (Colour) list.get(index);
        }
        return null;
    }

    public int size() {
       return this.list.size();
    }
    
   public Colour findInListWithId(int reqId) {
        boolean found = false;
        Colour colour = null;
        for (Iterator it = this.list.iterator(); !found && it.hasNext();){
           colour = (Colour) it.next();
           found = (colour.getId() == reqId); 
        }
        if (!found){
            colour.setId(-1);
        }
        return colour;
    }

    @Override
    public int findInListById(int reqId) {
       int found = -1;
       Colour colour;
       for (int idx = 0; idx<this.list.size() && found == -1;idx++ ){
           colour = (Colour)this.get(idx);
            found = (colour.getId()==reqId)?idx:-1;
       }
       return found;
    }

    @Override
    public boolean isAlreadyInTheList(Object requiredItem) {
        return false;
    }
}
