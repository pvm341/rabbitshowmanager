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
public class DBATest {
    
    public DBATest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        DBA.getInstance();
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
        assertEquals("JUV",DBA.lookup("abbrev", "human_ages", "age", "1"));
        assertEquals("ADT",DBA.lookup("abbrev", "human_ages", "age", "2"));
        assertEquals("OPN",DBA.lookup("abbrev", "human_ages", "age", "3"));
        assertEquals("Group/Stud",DBA.lookup("gender_text", "human_genders", "id", "1"));
        assertEquals("Gentleman",DBA.lookup("gender_text", "human_genders", "id", "2"));
        assertEquals("Lady",DBA.lookup("gender_text", "human_genders", "id", "3"));
        assertEquals("Dutch",DBA.lookup("breed", "breeds", "id", "5"));
        assertEquals("f",DBA.lookup("top_pen_req","breeds", "id", "5"));
        assertEquals("Netherland Dwarf",DBA.lookup("breed","breeds", "id", "14"));
    } 
    
    @Test 
    public void getFormatStringFromHeaderTest(){
        String header = "Status;id;adult_age;top_pen_req;section;breed";
        assertEquals("The result should be [   %c   %4d %-9s %-11s %-7s %s]", 
                "   %c  %4d %-9s %-11s %-7s %s",DBA.getFormatFromHeader(header));
        header = "Status;  id;colour;abbrev";
        assertEquals("The Result should be [   %c    %5d %-9s %s]",
                "   %c  %5d %-6s %s",DBA.getFormatFromHeader(header));
    }
    
    @Test 
    public void getHeaderTest(){
        ResultSet rs = DBA.executeSQL("SELECT * FROM breeds");
        String header = DBA.getHeader(rs);
        assertEquals("Should be Status;  id;adult_age;top_pen_req;section;breed",
                "Status;  id;adult_age;top_pen_req;section;breed",header);
        rs = DBA.executeSQL("SELECT * FROM colours");
        header = DBA.getHeader(rs);
        assertEquals("Should be Status; id;abbrev;colour","Status;  id;abbrev;colour",header);
        rs = DBA.executeSQL("SELECT * FROM breedcolours");
        header = DBA.getHeader(rs);
        assertEquals("Should be Status;breed_id;colour_id;available;selected;class_no",
                "Status;breed_id;colour_id;available;selected;class_no",header);
        
    }
}
