/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kontroller;

import beans.Rom;
import beans.Sok;
import static com.sun.corba.se.spi.presentation.rmi.StubAdapter.request;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import verktøy.Funksjoner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author Stein-Erik
 */
@Controller
public class SokeKontroller {
    @RequestMapping("FinnRomSok")
    public void finnRomSok(@ModelAttribute("rom") Rom rom){
        
    }
@RequestMapping(value="search")
    public String searchView(@ModelAttribute(value="soke")Sok sok, Model model){
        Funksjoner fu= new Funksjoner();
        model.addAttribute("liste",fu.getListe());
        return "SokeSide";
            
            }
	}
      
    /**
   */

