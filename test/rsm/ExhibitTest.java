/*
 * Here comes the text of your license
 * Each line should be prefixed with  * 
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
public class ExhibitTest {
    
    public ExhibitTest() {
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
    public void ringNumberTestJustTheYear(){
        assertFalse("False just the year",Exhibit.checkRingNumber("10"));
    }
    
    @Test
    public void ringNumberTestJustTheRingSize(){
        assertFalse("False just the ring size",Exhibit.checkRingNumber("X"));
    }
    @Test
    public void ringNumberTestJustTheNumber(){
        assertFalse("False just the number",Exhibit.checkRingNumber("12345"));
    }
    @Test
    public void ringNumberTestBlank(){
        assertFalse("False blank",Exhibit.checkRingNumber(""));
    }
    @Test
    public void ringNumberTestJustTheYearAndLetter(){
        assertFalse("False just the year and letter",Exhibit.checkRingNumber("10X"));
    }
    @Test
    public void ringNumberTestJustTheLetterAndNumber(){
        assertFalse("False just the letter and number",Exhibit.checkRingNumber("X12345"));
    }
    @Test
    public void ringNumberTestJustTheYearAndNumber(){
        assertFalse("False just the year and number",Exhibit.checkRingNumber("1012345"));
    }
    @Test
    public void ringNumberTestValid00001(){
        assertTrue("00A00001",Exhibit.checkRingNumber("00A00001")); 
    }
    @Test
    public void ringNumberTestValid00010(){
        assertTrue("16B00010",Exhibit.checkRingNumber("16B00010")); 
    }
    @Test
    public void ringNumberTestValid00100(){
        assertTrue("24E00100",Exhibit.checkRingNumber("24E00100")); 
    }
    @Test
    public void ringNumberTestValid01000(){
        assertTrue("36J01000",Exhibit.checkRingNumber("36J01000")); 
    }
    @Test
    public void ringNumberTestValid10000(){
        assertTrue("43L10000",Exhibit.checkRingNumber("43L10000")); 
    }
    @Test
    public void ringNumberTestValid01234(){
        assertTrue("57L01234",Exhibit.checkRingNumber("57L01234")); 
    }
    @Test
    public void ringNumberTestInvalidSizeLetterY(){
        assertFalse("68Y66660",Exhibit.checkRingNumber("68Y66660")); 
    }
    @Test
    public void ringNumberTestInvalidSizeLetterZ(){
        assertFalse("74Z00001",Exhibit.checkRingNumber("74Z00001")); 
    }
    @Test
    public void ringNumberTestInvalidSizeLetterM(){
        assertFalse("86M88880",Exhibit.checkRingNumber("86M88880")); 
    }
    @Test
    public void ringNumberTestInvalidSizeLetterW(){
        assertFalse("99W99999",Exhibit.checkRingNumber("99W99999")); 
    }
    @Test
    public void ringNumberTestInvalidSizeCharAtSign(){
        assertFalse("14@00129",Exhibit.checkRingNumber("14@00129")); 
    }
}
