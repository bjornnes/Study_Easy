/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package verktøy;

import java.util.List;
import beans.*;

import java.util.ArrayList;
import java.util.Arrays;
import javax.faces.application.ProjectStage;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import service.Service;

/**
 *
 * @author Sigrid
 */
public class Funksjoner {
  public List liste=new ArrayList();

  

  public ArrayList<Object> getAlleSokeTreff(String s, Service si){
        
        HttpServletRequest request = null;
        HttpServletRequest response;
        
        ArrayList<Object> liste=new ArrayList();
      
        
        /* midlertidig lister med objekter */
        ArrayList<Bruker> brukerListe = new ArrayList<Bruker>();
        ArrayList<Fag> fagListe = new ArrayList<Fag>();
        ArrayList<Rom> romListe = new ArrayList<Rom>();
        
        if(!s.isEmpty()) {
        /* henter data fra databasen utifra søkeord s */

        brukerListe.addAll(si.getBrukerSok("%" + s + "%", "%" + s + "%", "%" + s + "%"));
        fagListe.addAll(si.getFagSok("%" + s + "%", "%" + s + "%"));
        /*romListe.addAll(si.getRomSok("%" + s + "%", "%" + s + "%", Integer.parseInt(s)));*/
        
        }

        
        Rom r = new Rom();
        /*r.setType(3);
        r.setStorrelse(30);
        romListe.addAll(si.finnRomTypeStorrelse(r));
        */
 
        /* legger til alle lister i objekt hoved listen og returnerer til siden */
        liste.addAll(brukerListe);
        liste.addAll(fagListe);
        liste.addAll(romListe);
        return liste; 
       
         /**     
        String epost = "test1@aol.com";
        bruker.setEpost(epost);
        
        b = si.hentBruker(bruker);
        if(b.getFornavn() != null) {
            liste.add("Fant bruker " + b.getFornavn() + " på epost " + epost + " !");
        } else {
            liste.add("Fant ingen brukere på epost " + epost + " !");
        }
        */
        /*if(select == null) {
            liste.add("checkboxlisten er lik null");
        }*/
        /*if(select.length == 0) {
            liste.add("checkboxlisten har 0 elementer");
        }*/
      
   
        
        
       /* for(int i=0; i<testListe.size();i++){
            if(s.equalsIgnoreCase(testListe.get(i).toString())){
                     testOut.add(testListe.get(i));        
            }         
        }  
      return testOut;*/
    }
  
  
  /*public ArrayList<String> getListe(){
      ArrayList liste=new ArrayList();
      liste.add("Sigrid");
      liste.add("Silje");
      liste.add("Kasper");
      return liste;
  }*/
}
  /*for(int i=0;i<si.getAlleRom().size();i++){
            if(s.equalsIgnoreCase(si.getAlleRom().get(i).getRomNavn())){
                liste.add(si.getAlleRom().get(i));
            }
        }
        for(int i=0;i<si.getAlleFag().size();i++){
            if(s.equalsIgnoreCase(si.getAlleFag().get(i).getNavn())){
                liste.add(si.getAlleRom().get(i));
            }
        }
        for(int i=0;i<si.getAlleRom().size();i++){
            if(s.equalsIgnoreCase(si.getAlleRom().get(i).getRomNavn())){
                liste.add(si.getAlleRom().get(i));
            }     
        }
        */