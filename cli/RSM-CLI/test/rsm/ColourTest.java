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
     * Test of writeRecord method, of class Colour.
     * 
     */
    @Test
    public void testWriteRecord() {
        System.out.println("writeRecord");
        Colour backup = new Colour();
        Colour instance = new Colour(999,"test colour","TSTC");
        int rc = DBAccess.getRecordCount("colours",null);
        instance.writeRecord();
        assertEquals("New Colour added",rc+1,DBAccess.getRecordCount("colours",null));
        instance.setColour("updated col");
        instance.writeRecord();
        backup.readRecord(53);
        assertEquals("New Colour altered",rc+1,DBAccess.getRecordCount("colours",null));
        instance.deleteRecord();
        assertEquals("New Colour deleted",rc,DBAccess.getRecordCount("colours",null));
   }
    
    @Test
    public void testReadRecord(){
        System.out.println("readRecord");
        Colour instance = new Colour();
        int recNo = 3;
        instance.readRecord(recNo);
        assertEquals("Read Colour 3",recNo,instance.getId());
        assertEquals("Read Colour Black",true,"Black".equals(instance.getColour()));
        assertTrue("Read Colour Blk","Blk".equals(instance.getAbbrev()));
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
        assertFalse("should be false",instance.isDeleteOnWrite());
        instance.setDeleteOnWrite(true);
        assertTrue("should be true",instance.isDeleteOnWrite());
        instance.setDeleteOnWrite(false);
        assertFalse("Should be false",instance.isDeleteOnWrite());
    }

}
