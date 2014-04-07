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
public class BreedColourListTest {
    private BreedColourList instance;
    
    public BreedColourListTest() {
        instance = new BreedColourList();
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

    @Test
    public void testGetBreedColourList() {
        assertNotNull("Should be a value",instance);
        assertEquals("Should be none at present",0,instance.size());
        instance.readBcl();
        assertTrue("Should be not be 0",0 != instance.size());
    }
    
    @Test
    public void testGetAvailableColourListByBreedId(){
        assertNotNull(instance);
        instance.readBcl();
        assertFalse("read the list and is not 0", 0==instance.size());
        AvailableColourList acl = new AvailableColourList();
    }
}
