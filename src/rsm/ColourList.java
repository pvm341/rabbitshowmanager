/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package rsm;

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
      super.readList(hr, colour,"colours",null,"id");
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
    
    @Override
    public int findInListById(int reqId) {
        int found = -1;
        Colour colour = null;
        for (int idx=0; found == -1 && idx<list.size(); idx++){
           colour = (Colour) list.get(idx);
           found = colour.getId() == reqId?idx:-1; 
        }
        return found;
    }
}
