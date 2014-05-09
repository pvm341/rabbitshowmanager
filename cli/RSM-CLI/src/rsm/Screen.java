/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package rsm;

import java.io.*;
import java.util.Scanner;
import static rsm.ScreenColours.*;

/**
 *
 * @author paul
 */
class Screen {
    private static final int ESC = 27;
    private static String statusString = "Classes %d Exhibitors %d Exhibits %d Entries %d";
    private static String inkStrFmt = "%c[3%dm";
    private static String paperStrFmt = "%c[4%dm";
    private static Scanner in = new Scanner(System.in);

    public Screen(){
       
    }
    
    public static void clear(){
        System.out.printf("%c[2J",ESC);
        Screen.locate(1,1);
    }
    
    public static void clear(String str){
        int x= (str.length()%2)+20;
        System.out.printf("%c[2J%c[1;%dH%s",ESC,ESC,x,str); 
    }
    
    public static void locate(int x, int y){
        System.out.printf("%c[%d;%dH",ESC,y,x);
    }

    public static void locate(int x, int y, String str){
        locate(x,y);
        System.out.print(str);
    }
    
    public static int menu(int x, int y, String[] mainMenu) {
        
        int menuItem = 0;
        int reply, idx;
        String replyStr;
        boolean err;
        
        for (String str : mainMenu){
            Screen.locate(x,y+menuItem);
            System.out.printf("%d - %s",menuItem++,str);
        }
        do {
            Screen.locate(x, y+menuItem+5);
            System.out.printf("Select option 0-%d :%c[K",menuItem-1,27);
            replyStr = in.nextLine();
            for (err=false, idx=0; idx<replyStr.length() && !err;idx++){
                err = replyStr.charAt(idx)<'0' || replyStr.charAt(idx)>'9';
            }
            if (err){
                reply = -1;
            } else {
                reply = Integer.parseInt(replyStr);
            }
        } while(reply <0 || reply>=menuItem);
        return  reply;
    }
 
    public static void ink(ScreenColours sc){
        System.out.printf(inkStrFmt,ESC, sc.ordinal());
    }
    
    public static void paper(ScreenColours sc){
        System.out.printf(paperStrFmt, ESC,sc.ordinal());
    }
    
    public static String inkStr(ScreenColours sc){
        return String.format(inkStrFmt,ESC,sc.ordinal());
    }
    
    public static String paperStr(ScreenColours sc){
        return String.format(paperStrFmt,ESC,sc.ordinal());
    }
    
    public static void getPositon() {
        System.out.printf("%c[s", ESC);
    }

    public static void putPosition() {
        System.out.printf("%c[u", ESC);
    }

    public static void anyKey(){
        in.nextLine();
    }
    public static void displayStatus() {
        int classes;
        int exhibitors;
        int exhibits;
        int entries;
        Screen.locate(5, 23);
        classes = DBAccess.getRecordCount("showclasses");
        exhibitors = DBAccess.getRecordCount("exhibitors");
        exhibits = DBAccess.getRecordCount("exhibits");
        entries = DBAccess.getRecordCount("entries");
        System.out.printf(inkStr(YELLOW)+paperStr(BLUE)+statusString+inkStr(WHITE)+paperStr(BLACK), classes, exhibitors, exhibits, entries);
    }
    
    public static boolean validateReply(String optionsStr, int min, int max, String reply){
        int idx, value;
        boolean number = false, valid =false;
        if (reply != null && !reply.isEmpty()){
            for (idx = 0, number = true; idx < reply.length(); idx++){
                number = reply.charAt(idx)>='0' && reply.charAt(idx)<='9';
            }
            if (number) {
                value = Integer.parseInt(reply);
                valid = value>=min && value < max;
            }else{
                for (idx = 0, valid = false;!valid && idx<optionsStr.length(); idx++){
                   valid = reply.indexOf(optionsStr.charAt(idx))!=-1;
                }
            }
       }
        return valid ;
    }
    
    public static String getReply(String optionStr, int min, int max) {
        String reply;
        
        do {
            reply = in.nextLine();
        } while(!Screen.validateReply(optionStr,min,max,reply));
        return reply;
    }
    
    
}
