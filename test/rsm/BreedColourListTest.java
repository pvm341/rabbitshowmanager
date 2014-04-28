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
public class BreedColourListTest {
    
    public BreedColourListTest() {
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

    @Test
    public void findInListByIdTest(){
        BreedColourList instance = new BreedColourList(); 
        instance.readList(HeaderRequired.NOHEADERS);
        assertEquals(0,instance.findInListById(1));
        assertEquals(59,instance.findInListById(14));
        
    }
    
    @Test 
     public void isAlreadyInTheList(){
        BreedColourList instance = new BreedColourList();
        instance.readList(HeaderRequired.NOHEADERS);
        assertFalse(instance.isAlreadyInList(0,1));
        assertFalse(instance.isAlreadyInList(1, 0));
        assertTrue(instance.isAlreadyInList(1, 1));
        
    }
}
