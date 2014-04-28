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
public class ColourTest {
    
    public ColourTest() {
        
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
    public void dirtyBitTest(){
        Colour instance = new Colour();
        assertFalse("should be false",instance.isDirty());
        instance.setDirty(true);
        assertTrue("should be true",instance.isDirty());
        instance.setDirty(false);
        assertFalse("Should be false",instance.isDirty());
    }

    @Test
    public void deleteOnWriteBitTest(){
        Colour instance = new Colour();
        assertFalse("should be false",instance.isReadyToDelete());
        instance.setReadyToDelete(true);
        assertTrue("should be true",instance.isReadyToDelete());
        instance.setReadyToDelete(false);
        assertFalse("Should be false",instance.isReadyToDelete());
    }
    
    @Test
    public void getAColourTest(){
        Colour col1, col2;
        col1 = new Colour(1);
        col2 = new Colour(2);
        col2 = col2.performRead();
        col1 = col1.performRead();
        assertEquals(1,col2.getId());
        assertEquals("AC",col1.getAbbrev());
    }

}
