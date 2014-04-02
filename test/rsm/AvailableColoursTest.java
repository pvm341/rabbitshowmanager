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
public class AvailableColoursTest {
    
    public AvailableColoursTest() {
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
     * Test of getColours method, of class AvailableColours.
     */
    @Test
    public void testGetColours() {
        System.out.println("getColours");
        int breed = 14;
        AvailableColours instance = new AvailableColours();
        instance.getColours(breed);
        assertEquals("ND colours should be 45",45,instance.getSize());
        instance.getColours(5); //Dutch
        assertEquals("Dutch colours should be 11",11,instance.getSize());
        instance.getColours(1); // Any Variety
        assertEquals("Any Variety should have only one (Any Colour)",1,instance.getSize());
    }
    
    @Test
    public void testGetAvailable(){
        System.out.println("getAvailable");
        AvailableColours instance = new AvailableColours(); 
        instance.getColours(14); 
        assertEquals("Possible 45 ND colours",45,instance.getSize());
        instance.setUnavailable(0,2);
        assertEquals("Two less than full count",43,instance.getAvailable());
    }
    
}
