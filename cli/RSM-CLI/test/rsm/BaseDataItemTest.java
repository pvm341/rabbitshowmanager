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
public class BaseDataItemTest {
    
    public BaseDataItemTest() {
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
    public void dirtyBitTest(){
        BaseDataItem instance = new BaseDataItem();
        assertFalse("should be false",instance.isDirty());
        instance.setDirty(true);
        assertTrue("should be true",instance.isDirty());
        instance.setDirty(false);
        assertFalse("Should be false",instance.isDirty());
    }

    @Test
    public void deleteOnWriteBitTest(){
        BaseDataItem instance = new BaseDataItem();
        assertFalse("should be false",instance.isDeleteOnWrite());
        instance.setDeleteOnWrite(true);
        assertTrue("should be true",instance.isDeleteOnWrite());
        instance.setDeleteOnWrite(false);
        assertFalse("Should be false",instance.isDeleteOnWrite());
    }
}
