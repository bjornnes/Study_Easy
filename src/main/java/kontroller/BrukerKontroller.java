/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kontroller;

import beans.Abonemennt;
import beans.Bruker;
import beans.BrukerB;
import beans.Passord;
import beans.Rom;
import email.Email;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import service.Service;
import verktøy.Passordgenerator;
import verktøy.PasswordHasher;

/**
 *
 * @author Stein-Erik
 */
@Controller
public class BrukerKontroller {
    
    @Autowired
    private Service service;
    private Passordgenerator generator = new Passordgenerator();
    private Bruker testBruker = new Bruker();
    private PasswordHasher hasher = new PasswordHasher();
    
    @RequestMapping(value = "glemtPassord")
    public String glemtPassord(Model model){
        model.addAttribute("bruker", new Bruker());
        return "Glemsk";
    }
    
    @RequestMapping(value="sendNyttPassord")
    public String glemsk(@ModelAttribute("bruker") Bruker bruker, Model model, HttpServletRequest request, BindingResult error){
        String sjekk = bruker.getEpost();
        Bruker temp = null;
        try{
            temp = service.hentBruker(sjekk);
        }catch (Exception e){
            
        }   
        if(temp != null){
            if(sendNyPass(temp, error, false)){
                    return "Innlogging";
                }else{
                    model.addAttribute("melding", "feilmelding.email");
                    return "Glemsk";
                }
        }
        
        
        model.addAttribute("melding", "feilside.email");
        model.addAttribute("bruker", new Bruker());
        return "Glemsk";
    }
    
    @RequestMapping("EndrePassord")
    public String endrePassord(HttpSession sess, @ModelAttribute("passord") Passord pass, BindingResult error, Model model) throws Exception{
        System.out.println("Endrer passord*********");
        BrukerB brukerb = (BrukerB) sess.getAttribute("brukerBean");
        Bruker bruker = (Bruker) service.hentBruker(brukerb.getEpost());
        bruker.setEtternavn("asdasd");
        Passord valider = new Passord();
        if(!hasher.check(pass.getPassord(),bruker.getPassord())){
            System.out.println(bruker.getPassord());
            System.out.println(pass.getPassord());
            model.addAttribute("melding", "feilmelding.gammeltPassord");
            model.addAttribute("passord", new Passord());
            return "EndrePassordRed";
        }
        valider.validate(pass, error);
        if(error.hasErrors()){
            model.addAttribute("passord", new Passord());
            model.addAttribute("melding", "feilmelding.passordGenerell");
            return "EndrePassordRed";
        }else{
            bruker.setPassord(pass.getPassord1());
            System.out.println("Skal endre passord");
            if(service.endreBruker(bruker)){
                model.addAttribute("bruker", bruker);
                return "MinSide";
            }
            System.out.println("Kunne ikke endre passord");
        }
        model.addAttribute("melding", "feilmelding.passordGenerell");
        model.addAttribute("passord", new Passord());
        return "EndrePassordRed";
    }
    
    @RequestMapping("EndrePassordRed")
    public String endrePassordRed(HttpSession sess, Model model){
        BrukerB brukerb = (BrukerB)sess.getAttribute("brukerBean");
        if(brukerb != null && brukerb.isInnlogget()){
            model.addAttribute("passord", new Passord());
            return "EndrePassordRed";
        }
        model.addAttribute("bruker", new Bruker());
        return "Innlogging";
    }
    
    private String genererPassord(BindingResult error){
        String nyttPassord = generator.genererPassord();
        Passord pass = new Passord();
        pass.setGenerert(true);
        pass.setPassord1(nyttPassord);
        pass.validate(pass, error);
        if(error.hasErrors()){
            System.out.println(error.getErrorCount()+ nyttPassord);
            nyttPassord = genererPassord(error);
        }
        return nyttPassord;
    }    
    
    private Boolean sendNyPass(Bruker temp, BindingResult error, boolean nyBruker){
        String mottaker = temp.getEpost();
        String tema = "Nytt passord for bruker: "+temp.getEpost();
        String tema2 = "Ny bruker opprettet for: "+temp.getEpost()+" hos Study Easy";
        String nyttPassord = genererPassord(error);
        temp.setPassord(nyttPassord);
        Email email = new Email();
        String melding= 
                "Dine nye innloggingsdetaljer er: \n \n "
                + "Logg inn på: http://localhost:8084/Study_Easy/ \n \n"
                + "Brukernavn: "+temp.getEpost()+"\n "
                + "Passord: "+nyttPassord+"\n \n "
                + "Vi anbefaler at du bytter dette passordet ved neste innlogging. \n \n "
                + "Hilsen Study Easy teamet";
        if(!nyBruker){
            if(email.sendEpost(mottaker, tema, melding)){
                if(service.endreBruker(temp)){
                    return true;
                }
            }
        }else{
            if(email.sendEpost(mottaker, tema2, melding)){
                if(service.nyBruker(temp)){
                    return true;
                }
            }
        }
        return false; 
    }

    @RequestMapping("MinSideRed")
    public String minSideRed(HttpSession sess, Model model){
        BrukerB brukerb = (BrukerB) sess.getAttribute("brukerBean");
        if(brukerb != null && brukerb.isInnlogget()){
            model.addAttribute("bruker", brukerb);
            if(brukerb.getTilgangsniva() == 2){
                return "MinSideRed";
            }else{
                return "MinSide";
            }
            
        }
        model.addAttribute("bruker", new Bruker());
        return "Innlogging";
    }
    
    @RequestMapping(value="MinSideRedLagre")
    public String minSideRedLagre(@ModelAttribute("bruker") BrukerB brukerb, HttpSession sess){
        System.out.println("MinSideRedLagre************");
        BrukerB brukerbb = (BrukerB) sess.getAttribute("brukerBean");
        BrukerB nyBrukerInfo = new BrukerB();
        if(brukerbb.getTilgangsniva()==2){
            nyBrukerInfo.setFornavn(brukerb.getFornavn());
            nyBrukerInfo.setEtternavn(brukerb.getEtternavn());
        }
        return "MinSide";
    }
    
    @RequestMapping(value="LeggTilBrukerLagre")
    public String leggTilBrukerLagre(@Valid @ModelAttribute("nyBruker") Bruker bruker, @RequestParam("Tilgangsnivaa")String tilgang, Model model, BindingResult error, HttpSession sess){
        if(error.hasErrors()){
            model.addAttribute("melding", "feilmelding.nyBrukerValidering");
            return "LeggTilBruker";
        }
        if(tilgang.equals("Elev")){
            bruker.setTilgangsniva(0);
        }else if(tilgang.equals("Lærer")){
            bruker.setTilgangsniva(1);
        }else if(tilgang.equals("Timeplansansvarlig")){
            bruker.setTilgangsniva(2);
        }
        if(sendNyPass(bruker, error, true)){
            model.addAttribute("bruker", (BrukerB)sess.getAttribute("brukerBean"));
            return "MinSide";
        }
        System.out.println("Skal returnere legg til bruker");
        model.addAttribute("melding", "feilmelding.nyBruker");
        model.addAttribute("nyBruker", new Bruker());
        return "LeggTilBruker";
    }
    
    @RequestMapping(value="NyttAbonemennt")
    public String nyttAbonemennt(@Valid @ModelAttribute("nyttAbonemennt") Abonemennt abonemennt, HttpSession sess){
        return "NyttAbonnement";
    }
    @RequestMapping("BekreftBestilling")
    public String bekreftBestilling(Model model, HttpSession sess){
        BrukerB brukerb = (BrukerB)sess.getAttribute("brukerBean");
        if(brukerb != null && brukerb.isInnlogget()){
            model.addAttribute("rom", new Rom());
            return "BekreftBestilling";
        }
        model.addAttribute("bruker", new Bruker());
        return "Innlogging";
    }
}
