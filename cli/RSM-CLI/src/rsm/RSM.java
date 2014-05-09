/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package rsm;


/**
 *
 * @author paul
 */
public class RSM {
    private String[] mainMenu = {"Exit Program","Maintenance","Before the show", "At the show","After the show"};
    DBAccess dba = DBAccess.getInstance();
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        RSM rsm = new RSM();
        DBAccess.getInstance();       
        rsm.control();
    }

    private void control() {
        int option;
        
        Screen.getPositon();
        do {
            Screen.clear("Rabbit Show Manager V1.0");
            Screen.displayStatus();
            option = Screen.menu(10,5,mainMenu);
            switch(option){
                case 1: 
                    Maintenance maintenance = new Maintenance();
                    maintenance.control();
                    break;
                case 2:
                    beforeTheShow();
                    break;
                case 3:
                    atTheShow();
                    break;
                case 4:
                    afterTheShow();
                    break;
                case 0:
                    break;
            }
        } while (option != 0);
        Screen.putPosition();
    }
    
    private void maintenance(){
        
        
    }
    
    private void beforeTheShow(){
        
    }
    
    private void atTheShow(){
        
    }
    
    private void afterTheShow(){
        
    }
}
