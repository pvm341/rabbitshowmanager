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
public class BreedColourTest {
    
    public BreedColourTest() {
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
     * Test of getBreedId method, of class BreedColour.
     */
    @Test
    public void testGetBreedId() {
        System.out.println("getBreedId");
        BreedColour instance = new BreedColour();
        int expResult = 0;
        int result = instance.getBreedId();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setBreedId method, of class BreedColour.
     */
    @Test
    public void testSetBreedId() {
        System.out.println("setBreedId");
        int breedId = 0;
        BreedColour instance = new BreedColour();
        instance.setBreedId(breedId);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getColourId method, of class BreedColour.
     */
    @Test
    public void testGetColourId() {
        System.out.println("getColourId");
        BreedColour instance = new BreedColour();
        int expResult = 0;
        int result = instance.getColourId();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setColourId method, of class BreedColour.
     */
    @Test
    public void testSetColourId() {
        System.out.println("setColourId");
        int colourId = 0;
        BreedColour instance = new BreedColour();
        instance.setColourId(colourId);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
