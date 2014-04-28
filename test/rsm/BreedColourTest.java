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
        assertEquals(1,instance.getBreedId(VersionRequired.CURRENT));
        assertEquals(1,instance.getColourId(VersionRequired.CURRENT));
        assertEquals(1,instance.getBreedId(VersionRequired.PREVIOUS));
        assertEquals(1,instance.getColourId(VersionRequired.PREVIOUS));
        instance.setBreedAndColourIds(2, 2);
        assertEquals(2,instance.getBreedId(VersionRequired.CURRENT));
        assertEquals(2,instance.getColourId(VersionRequired.CURRENT));
        assertEquals(1,instance.getBreedId(VersionRequired.PREVIOUS));
        assertEquals(1,instance.getColourId(VersionRequired.PREVIOUS));
        instance.setBreedAndColourIds(23, 67);
        assertEquals(23,instance.getBreedId(VersionRequired.CURRENT));
        assertEquals(67,instance.getColourId(VersionRequired.CURRENT));
        assertEquals(1,instance.getBreedId(VersionRequired.PREVIOUS));
        assertEquals(1,instance.getColourId(VersionRequired.PREVIOUS));
    }
}
