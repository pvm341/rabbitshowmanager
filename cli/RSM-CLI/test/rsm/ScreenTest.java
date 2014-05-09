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
public class ScreenTest {
    
    public ScreenTest() {
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
    public void validateReplyTestNull(){
        assertFalse("Null reply so should be false",Screen.validateReply("<>q",0,20,null));
    }
    
    @Test
    public void validateReplyTestEmpty(){
        assertFalse("Empty reply so should be false",Screen.validateReply("<>q", 0, 20, ""));
    }
    
    @Test
    public void validateReplyTestPrev(){
        assertTrue("< therefore valid",Screen.validateReply("<>q", 0, 20, "<"));
    }
    
    @Test
    public void validateReplyTestNext(){
        assertTrue("> therefore valid",Screen.validateReply("<>q", 0, 20, ">"));
    }
    
    @Test
    public void validateReplyTestQuit(){
        assertTrue("q therefore valid",Screen.validateReply("<>q", 0, 20, "q"));
    }
    
    @Test
    public void validateReplyTestNotInValid(){
        assertFalse("e therefore invalid",Screen.validateReply("<>q", 0, 20, "e"));
    }
    
    @Test
    public void validateReplyTestInRange(){
        assertTrue("15 therefore valid",Screen.validateReply("<>q", 0, 20, "15"));
    }
    
    @Test
    public void validateReplyTestMin(){
        assertTrue("0 therefore valid",Screen.validateReply("<>q", 0, 20, "0"));
    }
    
    @Test
    public void validateReplyTestMax(){
        assertTrue("0 therefore valid",Screen.validateReply("<>q", 0, 20, "19"));
    }
    
    @Test
    public void validateReplyTestOutOfRangeMax(){
        assertFalse("20 therefore invalid",Screen.validateReply("<>q", 0, 20, "20"));
    }
    @Test
    public void validateReplyTestOutOfRangeMin(){
        assertFalse("-1 therefore invalid",Screen.validateReply("<>q", 0, 20, "-1"));
    }
}
