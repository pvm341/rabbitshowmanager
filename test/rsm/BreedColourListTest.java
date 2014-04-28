/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
    public void findInListByIdTest(){
        BreedColourList instance = new BreedColourList(); 
        instance.readList(false);
        assertEquals(0,instance.findInListById(1));
        assertEquals(59,instance.findInListById(14));
        
    }
    
    @Test 
     public void isAlreadyInTheList(){
        BreedColourList instance = new BreedColourList();
        instance.readList(false);
        assertFalse(instance.isAlreadyInList(0,1));
        assertFalse(instance.isAlreadyInList(1, 0));
        assertTrue(instance.isAlreadyInList(1, 1));
        
    }
}
