package rsm;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

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
    public void createBreedColour(){
        BreedColour instance = new BreedColour();
        assertFalse(null ==instance);
        instance.setBreedAndColourIds(1,1);
        assertEquals(1,instance.getBreedId(true));
        assertEquals(1,instance.getColourId(true));
        assertEquals(1,instance.getBreedId(false));
        assertEquals(1,instance.getColourId(false));
        instance.setBreedAndColourIds(2, 2);
        assertEquals(2,instance.getBreedId(true));
        assertEquals(2,instance.getColourId(true));
        assertEquals(1,instance.getBreedId(false));
        assertEquals(1,instance.getColourId(false));
        instance.setBreedAndColourIds(23, 67);
        assertEquals(23,instance.getBreedId(true));
        assertEquals(67,instance.getColourId(true));
        assertEquals(1,instance.getBreedId(false));
        assertEquals(1,instance.getColourId(false));
    }
}
