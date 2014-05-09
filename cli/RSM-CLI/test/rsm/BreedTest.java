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
import rsm.DBAccess;

/**
 *
 * @author paul
 */
public class BreedTest {
    
    public BreedTest() {
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
     * Test of readRecord method, of class Breed.
     */
    @Test
    public void testGetRecord() {
        System.out.println("getRecord");
        int recNo = 5;
        Breed instance = new Breed();
        instance.readRecord(recNo);
        // TODO review the generated test code and remove the default call to fail.
        assertEquals("Record No is 5",recNo,instance.getId());
        instance.readRecord(3);
        assertEquals("Record No is 5",3,instance.getId());
    }

    /**
     * Test of writeRecord method, of class Breed.
     */
    @Test
    public void testSetRecord() {
        int rc = 0;
        System.out.println("setRecord");
        Breed instance = new Breed();
        instance.readRecord(5);
        instance.setId(0);//altered to a new record which doesn't exist
        instance.setBreed("Test Breed"); //altered the breed text in case of potential unique constraint
        instance.getRecordCount();
        rc = instance.getRecordCount();
        instance.writeRecord();
        assertEquals(rc+1,instance.getRecordCount());
        instance.setBreed("updated name");
        instance.writeRecord();
        assertEquals(rc+1,instance.getRecordCount());
        instance.deleteRecord();
        assertEquals(rc,instance.getRecordCount());
    }

    /**
     * Test of deleteRecord method, of class Breed.
     * 
     */
    @Test
    public void testDelRecord() {
        System.out.println("delRecord");
        Breed instance = new Breed();
        int rc = instance.getRecordCount();
        instance.setId(100);
        instance.setBreed("test for delete");
        instance.setAdultAge(4);
        instance.writeRecord();
        assertEquals(rc+1, instance.getRecordCount());
        instance.deleteRecord();
        assertTrue(rc == instance.getRecordCount());
    }
    
    @Test
    public void dirtyBitTest(){
        Breed instance = new Breed();
        assertFalse("should be false",instance.isDirty());
        instance.setDirty(true);
        assertTrue("should be true",instance.isDirty());
        instance.setDirty(false);
        assertFalse("Should be false",instance.isDirty());
    }  
    
    @Test
    public void deleteOnWriteBitTest(){
        Breed instance = new Breed();
        assertFalse("should be false",instance.isDeleteOnWrite());
        instance.setDeleteOnWrite(true);
        assertTrue("should be true",instance.isDeleteOnWrite());
        instance.setDeleteOnWrite(false);
        assertFalse("Should be false",instance.isDeleteOnWrite());
    }

}
