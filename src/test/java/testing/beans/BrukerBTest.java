/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testing.beans;

import org.junit.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import java.util.ArrayList;

import beans.BrukerB;
import beans.KalenderEvent;
/**
 *
 * @author Sindre
 */
public class BrukerBTest {


//public class SearchEngineJUnitTest {
    //@InjectMocks private PageReader reader;
    KalenderEvent[] kalenderEvent; 
    
    @BeforeClass
    public static void setUpClass() throws Exception {
        // Koden her eksekveres f�r f�rste test i klassen
    }

    @Before
    public void setUp() throws Exception {
        // Koden her eksekveres f�r hver av testmetodene i klassen
        kalenderEvent=mock(KalenderEvent[].class);
    
    }

    @Test
    public void test_1 (){
        /**
         Tester enkle get-metoder som:
         getId(), getType(), getEier(), getRom()
         **/
        
        

    }
    
    @Test
    public void test_2 (){
        /**
         * Tester enkle set-metoder
         * setId(int id), setType(int Type), setEier(String eier),
         * setRom(String rom)
         */
        
    }
 
    @Test
    public void test_3 (){

    }
    /*
    @Test(expected = NullPointerException.class)
    public void test_at_koden_som kj�res_kaster_en_NullPointerException() {
        // Kode som gj�r noe annet
    }
    */

    @After
    public void tearDown() throws Exception {
        // Koden her kj�res etter hver test
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
        // Koden her kj�rer etter alle testmetodene i klassen er ferdig
    }
}


