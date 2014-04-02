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
import org.uispec4j.UISpec4J;
import org.uispec4j.UISpecTestCase;

/**
 *
 * @author paul
 */
public class RabbitShowManagerMainTest extends UISpecTestCase {
    
    public RabbitShowManagerMainTest() {
        UISpec4J.init();
    }
    
    @BeforeClass
    public static void setUpClass() {
        System.out.println("main");
        String[] args = null;
        
        RabbitShowManagerMain.main(args);
        // TODO review the generated test code and remove the default call to fail.
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
     * Test of main method, of class RabbitShowManagerMain.
     */
    @Test
    public void testMain() {
        org.uispec4j.Window win = getMainWindow();
        //org.uispec4j.TextBox tb = win.getTextBox("edtClassQty");
        //assertEquals("Should be 99999","99999".equals(tb.getText()));
    }
    
}
