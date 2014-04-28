/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package rsm;

import java.sql.ResultSet;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;


/**
 *
 * @author paul
 */
public class DBAccessTest {
    
    public DBAccessTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        DBAccess.getInstance();
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }
    
    /**
     * Test Loop up method
     */
    @Test  
    public void lookupTest(){
        //lookup(String field, String table, String key, String value)
        assertEquals("JUV",DBAccess.lookup("abbrev", "human_ages", "age", "1"));
        assertEquals("ADT",DBAccess.lookup("abbrev", "human_ages", "age", "2"));
        assertEquals("OPN",DBAccess.lookup("abbrev", "human_ages", "age", "3"));
        assertEquals("Group/Stud",DBAccess.lookup("gender_text", "human_genders", "id", "1"));
        assertEquals("Gentleman",DBAccess.lookup("gender_text", "human_genders", "id", "2"));
        assertEquals("Lady",DBAccess.lookup("gender_text", "human_genders", "id", "3"));
        assertEquals("Dutch",DBAccess.lookup("breed", "breeds", "id", "5"));
        assertEquals("f",DBAccess.lookup("top_pen_req","breeds", "id", "5"));
        assertEquals("Netherland Dwarf",DBAccess.lookup("breed","breeds", "id", "14"));
    } 
    
    @Test 
    public void getFormatStringFromHeaderTest(){
        String header = "Status;id;adult_age;top_pen_req;section;breed";
        assertEquals("The result should be [   %c   %4d %-9s %-11s %-7s %s]", 
                "   %c  %4d %-9s %-11s %-7s %s",DBAccess.getFormatFromHeader(header));
        header = "Status;  id;colour;abbrev";
        assertEquals("The Result should be [   %c    %5d %-9s %s]",
                "   %c  %5d %-6s %s",DBAccess.getFormatFromHeader(header));
    }
    
    @Test 
    public void getHeaderTest(){
        ResultSet rs = DBAccess.executeSQL("SELECT * FROM breeds");
        String header = DBAccess.getHeader(rs);
        assertEquals("Should be Status;  id;adult_age;top_pen_req;section;breed",
                "Status;  id;adult_age;top_pen_req;section;breed",header);
        rs = DBAccess.executeSQL("SELECT * FROM colours");
        header = DBAccess.getHeader(rs);
        assertEquals("Should be Status; id;abbrev;colour","Status;  id;abbrev;colour",header);
        rs = DBAccess.executeSQL("SELECT * FROM breedcolours");
        header = DBAccess.getHeader(rs);
        assertEquals("Should be Status;breed_id;colour_id","Status;breed_id;colour_id",header);
        
    }
}
