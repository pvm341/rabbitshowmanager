/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package rsm;

import rsm.BreedList;

/**
 *
 * @author paul
 */
class Maintenance {
    private String[] mainMenu = {   
                                "Exit Section","Breeds","Colours", 
                                "BreedColours","Exhibit Ages",
                                "Exhibit Genders","Exhibitor Ages",
                                "Exhibitor Genders","Show Sections"};
    DBAccess dba = DBAccess.getInstance();
    
    public Maintenance(){
        
    }
    
    public void control(){
        int option;
        
        do {
            Screen.clear("Rabbit Show Manager V1.0");
            Screen.displayStatus();
            option = Screen.menu(10,5,mainMenu);
            switch (option) {
                case 1:
                    BreedListEditor editor = new BreedListEditor();
                    editor.display(0, 20);
                    break;
                default:
                    break;
            }
        } while (option != 0);
    }

 }
