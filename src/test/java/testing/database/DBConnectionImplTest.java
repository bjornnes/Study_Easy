/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testing.database;

import beans.Abonemennt;
import database.DBConnectionImpl;
import beans.Bruker;
import beans.BrukerB;
import beans.Fag;
import beans.KalenderEvent;
import beans.Klasse;
import beans.Rom;
import database.DBConnection;
import java.sql.Connection;
import java.sql.Timestamp;
import org.junit.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import java.util.ArrayList;
import java.util.Date;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

/**
 *
 * @author Sindre
 */
public class DBConnectionImplTest {
    
    static DBConnectionImpl dbc;
    
    Abonemennt ab;
    Abonemennt ac;
    Klasse klasse;
    Rom rom1; 
    Fag fag1;
    Bruker b;
    Bruker bruker;
    Timestamp fraDato;
    Timestamp tilDato;
    
    KalenderEvent kEvent;
    private ArrayList<KalenderEvent> KEliste;
    private ArrayList<Rom> RomListe;
    private ArrayList<Fag> FagListe;
    private ArrayList<Bruker> BrukerListe;
    
    private DataSource dS;
    private JdbcTemplate jT;
     
    
    static DataSource dummyDataSource(){
        String url = "jdbc:mysql://mysql.stud.iie.ntnu.no/g_scrum_t1_test"; //mysql.stud.iie.ntnu.no/g_scrum_t1_test
        String username = "g_scrum_t1_test"; //TestDatabase:"g_scrum_t1_test"  Database:"g_scrum_t1" 
        String password = "nYHZ2FnB"; // TestDatabasePassord:"nYHZ2FnB" DatabasePassord:"0GPgcC6H"
        DriverManagerDataSource dmds = new DriverManagerDataSource(url, username, password);
        dmds.setDriverClassName("com.mysql.jdbc.Driver");
        try{
            Connection con = dmds.getConnection();
            System.out.println(" *********  Konfig " + con );
            //getAllePersoner(con); //brukes for testing av oppkobling
        }catch(Exception e){
            System.out.println(" Konfig.Feil ved henting av conncetion() " + e);
        }
        return dmds;
    }
       
    @BeforeClass
    public static void setUpClass() throws Exception{
        dbc = new DBConnectionImpl();
        dbc.setDatabaseSource(dummyDataSource());
    }
    
    @Before
    public void setUp() {
                        
        KEliste = new ArrayList();
        RomListe = new ArrayList();
        BrukerListe = new ArrayList();
        FagListe = new ArrayList();
        fraDato = Timestamp.valueOf("2016-1-22 09:15:00.0");
        tilDato = Timestamp.valueOf("2016-1-23 12:30:00.0");
        
        ab = new Abonemennt("test4@aol.com","henrik_bjorkheim@hotmail.com", 0);
        ac = new Abonemennt("ola@hotmail.com", "TDAT2001", 1);
        rom1 = new Rom();
        rom1.setRomID("KAUD");
        rom1.setRomNavn("KAUD");
        rom1.setEtasje(3);
        rom1.setType(3);
        rom1.setStorrelse(100);
        rom1.setAntStolplasser(100);
             
        fag1 = new Fag();
        fag1.setFagID("TDAT2004");
        fag1.setNavn("Algdat");
       
        kEvent = new KalenderEvent();
        kEvent.setEierNavn("Ola Nilsson");
        kEvent.setEpost("ola@hotmail.com");
        kEvent.setType(2);
        kEvent.setStartTid(fraDato);
        kEvent.setSluttTid(tilDato);
        kEvent.setTittel("Lekser");
        kEvent.setRom("GR114");
        kEvent.setFag("");
        kEvent.setNotat("Må gjøre");
        kEvent.setPrivat(true);
        kEvent.setTilhorerEvent(2);
        
        klasse = new Klasse();
        klasse.setNavn("2.ing");
        b = new Bruker(); 
        b.setFornavn("Ola");
        b.setEtternavn("Aas");
        b.setEpost("ola@hotmail.com");
        b.setTilgangsniva(0);
        b.setPassord("Passord79##");
        
        bruker = new Bruker();
        bruker.setFornavn("Per");
        bruker.setEtternavn("Aas");
        bruker.setEpost("per@hotmail.com");
        bruker.setNotat("Jeg liker fotball");
        bruker.setTilgangsniva(3);
        bruker.setPassord("Passord89##");
               
        }
    
     @Test
    public void test_getBruker(){
        String epost = "henrik_bjorkheim@hotmail.com";
        String brukar = "Ansatt: Henrik Pus,  Epost: henrik_bjorkheim@hotmail.com";
        /*Bruker nb = new Bruker();
        nb = dbc.getBruker(epost);
        System.out.print(nb.toString());*/
        try{
           b = dbc.getBruker(epost);
           assertEquals(b.toString(),brukar);
        } catch(NullPointerException e){
            System.out.println("Nullpointer..");
        }        
        //assertEquals(dbci.getBruker("test1@aol.com"), b);
    }
        
    @Test
    public void test_leggTilBruker(){
        assertTrue(dbc.leggTilBruker(bruker));
    }
    
    @Test
    public void test_leggTilBrukerFalse(){
        assertFalse(dbc.leggTilBruker(b));
    }
    
    @Test
    public void test_slettBruker(){
        assertTrue(dbc.slettBruker(bruker));
    }
    
    @Test
    public void test_slettBrukerFalse(){
        assertFalse(dbc.slettBruker(bruker));
    }
    
    @Test
    public void test_oppdaterBruker(){
        b.setEtternavn("Nilsson");
        assertTrue(dbc.oppdaterBruker(b));
    }
    
    @Test
    public void test_oppdaterBrukerFalse(){
        assertFalse(dbc.oppdaterBruker(bruker));
    }
    
    @Test
    public void test_sjekkPassord(){
        String passord = "Passord79##";
        assertTrue(dbc.sjekkPassord("ola@hotmail.com", passord));
    }
    
    @Test
    public void test_sjekkPassordFalse(){
        String passord = "Passord78##";
        assertFalse(dbc.sjekkPassord("ola@hotmail.com", passord));
    }
      
    @Test
    public void test_getAlle(){
        assertEquals(dbc.getAlleRom().size(), 34 );
        assertEquals(dbc.getAlleFag().size(), 4);
    
        int ant = 16;
        assertEquals(dbc.getAlleBrukere().size(), ant);
    }
    
    @Test
    public void test_sok(){
        assertEquals(dbc.getAnsattSok("test", "test", "test").size(), 0);
        
        assertEquals(dbc.getFagSok("TDAT2001", "TDAT2001").size(), 1);
        
        assertEquals(dbc.getRomSok("KAUD", "KAUD").size(), 1);
        
        assertEquals(dbc.getStudentSok("Ola", "Ola", "Ola").size(),1);
    
        assertEquals(dbc.getKlasseSok("TDAT").size(), 0);
    }
    
    @Test
    public void testFjernKalenderEvent(){
        assertTrue(dbc.fjernKalenderEvent(kEvent));
    }
    
    /*@Test
    public void testleggTilKalenderEvent(){
        assertTrue(dbc.leggTilEvent(kEvent));
    }*/
  
    @Test
    public void test_slettAbonnement(){
        assertFalse(dbc.slettAbonemennt(ac));
    }
    
    @Test
    public void test_slettAbonnementFalse(){
        assertTrue(dbc.slettAbonemennt(ac));
    }
    
    /*@Test
    public void test_leggTilAbonnementFalse(){
        assertFalse(dbc.leggTilAbonemennt(ac));
    }
    Må dobbeltsjekke metoden at den returnerer riktig false.
    */
    
    @Test
    public void test_leggTilAbonnement(){
        assertTrue(dbc.leggTilAbonemennt(ac));
    }
    
    @Test
    public void test_getRomFraNavn(){
        assertEquals(dbc.getRomFraNavn(rom1).size(), 1);
        assertEquals(dbc.getRomFraNavn("KAUD").size(), 1);
        
    }
    
    /*@Test
    public void test_getRomFraStr(){
        assertEquals(dbc.getRomFraStoerrelse(rom1).size(), 1);
    }
    skjønner ikke hva som returneres :(  */
    
    @Test
    public void test_slettBooking(){
        //assertTrue(dbc.slettBooking(kEvent));
    }
    
    @Test
    public void test_leggTilBooking(){
        //assertTrue(dbc.leggTilBooking(kEvent));
    }
    
    
    
    @After
    public void tearDown() {
        
    }
           
      @AfterClass
    public static void tearDownClass() {
    }
    
    /*public static void main(String[] args) {
        dbc = new DBConnectionImpl();
        nb = dbc.getBruker("henrik_bjorkheim@hotmail.com");
        
        System.out.println(nb.toString());
    }*/      
}
