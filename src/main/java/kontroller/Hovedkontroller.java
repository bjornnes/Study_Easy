/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kontroller;

import beans.Bruker;
import beans.BrukerB;
import email.Email;
import java.io.PrintWriter;
import static java.lang.System.console;
import java.util.List;
import java.util.Random;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import service.Service;
import verktøy.PasswordHasher;
import verktøy.Passordgenerator;

/**
 * 
 * @author Stein-Erik
 */
@Controller
public class Hovedkontroller {
    
    @Autowired
    private Service service;
    private Passordgenerator generator;
    
    @RequestMapping(value = "/*")
    public String start(Model model, HttpSession sess){
        BrukerB brukerBean = (BrukerB) sess.getAttribute("brukerBean");
        
        if(brukerBean != null && brukerBean.isInnlogget()){
            return "Forside";
        }
        model.addAttribute("bruker", new Bruker());
        return "Innlogging";
    }
    
    @RequestMapping(value="logInSjekk")
    public String logIn(@ModelAttribute("bruker") Bruker bruker, Model model, HttpSession sess){
        System.out.println("hei)");
        if(service.sjekkPassord(bruker.getEpost(), bruker.getPassord())){
            BrukerB brukerBean = new BrukerB(service.hentBruker(bruker));
            brukerBean.setInnlogget(true);
            sess.setAttribute("brukerBean", brukerBean);
            return "Forside";
        }
        model.addAttribute("melding", "feilmelding.login");
        bruker.setPassord("");
        return "Innlogging";
    }
    
    @RequestMapping(value = "glemtPassord")
    public String glemtPassord(Model model){
        model.addAttribute("bruker", new Bruker());
        return "Glemsk";
    }
    
    @RequestMapping(value="kalenderTest")
    public String kalenderTest (Model model, HttpServletRequest request){
        return "fullcalendar/demos/basic_views";
    }
    @RequestMapping(value="sendNyttPassord")
    public String glemsk(@ModelAttribute("bruker") Bruker bruker, Model model, HttpServletRequest request){
        String sjekk = bruker.getEpost();
        Bruker temp;
        /*List<Bruker> tabell = service.getAlleBrukere();
        for (Bruker bruker1 : tabell) {
            if(bruker1.getEpost().equals(sjekk)){
                temp = service.hentBruker(sjekk);
                if(sendNyPass(temp)){
                    return "EmailRedirect";
                }else{
                    model.addAttribute("melding", "feilmelding.email");
                    return "Glemsk";
                }
            }
        }*/
        temp = service.hentBruker(sjekk);
        if(temp != null){
            if(sendNyPass(temp)){
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
    
    
    @RequestMapping("MinSide")
    public String minSide(){
        return "MinSide";
    }
    
    @RequestMapping("MinSideRed")
    public String minSideRed(){
        return "MinSideRed";
    }
    
    @RequestMapping("VelgRom")
    public String velgRom(){
        return "VelgRom";
    }
    
    @RequestMapping("Forside")
    public String forside(){
        return "Forside";
    }
    
    @RequestMapping("FinnRom")
    public String finnRom(){
        return "FinnRom";
    }
    @RequestMapping("SokeSide")
    public String sokeSide(){
        return "SokeSide";
    }
    
    /*private String genererPassord(){
        String characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ~`!@#$%^&*()-_=+[{]}\\|;:\'\",<.>/?0123456789";
        /*char[] nyttPass = new char[8];
        Random rng = new Random();
        for (int i = 0; i < nyttPass.length; i++) {
            nyttPass[i] = alfabet.charAt(rng.nextInt(alfabet.length()));
        }
        String nyttPassord = new String(nyttPass);
        if(nyttPassord.toLowerCase().equals(nyttPassord) || nyttPassord.toUpperCase().equals(nyttPassord)){
            nyttPassord = genererPassord();
        }
        String nyttPassord = RandomStringUtils.random( 15, characters );
        System.out.println(nyttPassord);
        return nyttPassord;
    }*/
    
    private Boolean sendNyPass(Bruker temp){
        String mottaker = temp.getEpost();
        String tema = "Nytt passord for bruker: "+temp.getEpost();
        String nyttPassord = generator.genererPassord();
        temp.setPassord(nyttPassord);
        Email email = new Email();
        String melding= 
                "Dine nye innloggingsdetaljer er: \n \n "
                + "Brukernavn: "+temp.getEpost()+"\n "
                + "Passord: "+nyttPassord+"\n \n "
                + "Vi anbefaler at du bytter dette passordet ved neste innlogging. \n \n "
                + "Hilsen Study Easy teamet";
        System.out.println("Oppdater bruker: "+temp);
        if(service.endreBruker(temp)){
            if(email.sendEpost(mottaker, tema, melding)){
                System.out.println("Email sendt!****");
                return true;
            }
            System.out.println("Email ikke sendt :( *****");
        }
        //asdasdasd
        return false; 
    }
    
    
    
    
}
