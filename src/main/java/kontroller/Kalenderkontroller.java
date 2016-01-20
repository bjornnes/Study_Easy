/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kontroller;

import beans.BrukerB;
import beans.KalenderEvent;
import beans.RomBestilling;
import com.google.gson.Gson;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import service.Service;

/**
 *
 * @author Stein-Erik
 */
@Controller
public class Kalenderkontroller {
    @Autowired
    private Service service;

    
    @RequestMapping(value = "/events/getEvents", method = RequestMethod.GET)
    public
    @ResponseBody
    String getEvents(HttpServletResponse response, HttpSession sess) {
            
        //kall til database for å finne relevant info.
        //ID, tittel, start, slutt, descr, rom, type, eiernavn, fag
        BrukerB brukerb = (BrukerB)sess.getAttribute("brukerBean");
        List<KalenderEvent> events = service.getAlleEventsFraBruker(brukerb);
        String tittel = "";
        try{
             tittel = events.get(0).getTittel();
        }catch(Exception e){
            
        }
        
        
        //0: forelesning, 1: øving, 2: privat ting, 3: romreservasjon
        String[] farger = {"#00BFFF", "#00FF7F", "#FFFF00", "#FFA500"};
        
        String jsonSend = "";
        
        for (KalenderEvent event : events){
            String start = "" + event.getStartTid();
            String slutt = "" + event.getSluttTid();
            
            String descr = "";
            if (event.getFag() != null){
                descr += "Fag: " + event.getFag() + "<br>";
            }
            if (event.getRom() != null){
                descr += "Rom: <a href='" + event.getRom() + "'>" + event.getRom() + "</a><br>";
            }
            if (event.getNotat() != null){
                descr += "Notat: " + event.getNotat();
            }
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("id", event.getId());
            map.put("title", event.getTittel());
            map.put("start", start);
            map.put("end", slutt);
            map.put("description", descr);
            map.put("color", farger[event.getType()]);
            String json = new Gson().toJson(map);
            if (!jsonSend.isEmpty()){
                jsonSend += ", ";
            }
            jsonSend += json;
        }
        List<RomBestilling> bestillinger = service.getAlleBestillingerFraBruker(brukerb);
        for (RomBestilling bestilling : bestillinger){
            
            if (bestilling.getTilhorerEvent() == 0){
                
                String start = "" + bestilling.getStartDato();
                String slutt = "" + bestilling.getSluttDato();

                String descr = "Rom: <a href='" + bestilling.getRomId() + "'>" + bestilling.getRomId() + "</a>";
                descr += "<br>Fra: " + start + "<br>Til: " + slutt;

                Map<String, Object> map = new HashMap<String, Object>();
                map.put("title", "Rombestilling");
                map.put("start", start);
                map.put("slutt", slutt);
                map.put("color", farger[3]);
                map.put("description", descr);

                String json = new Gson().toJson(map);
                if (!jsonSend.isEmpty()){
                    jsonSend += ", ";
                }
                jsonSend += json;
            }
            else{
                
            }
            
        }

        // Write JSON string.
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        return "[" + jsonSend + "]";
    }
   @RequestMapping(value="kalenderEventCheck")
    public String asdasda(@RequestParam(value="checkbox_1", required=false)boolean forelesning, @RequestParam(value="checkbox_2", required=false)boolean Oving,
            @RequestParam(value="checkbox_3", required=false)boolean romreservasjon, @RequestParam(value="checkbox_4", required=false)boolean privatHendelse, HttpServletRequest req){
        req.setAttribute("checkbox1", forelesning);
        req.setAttribute("checkbox2", Oving);
        req.setAttribute("checkbox3", romreservasjon);
        req.setAttribute("checkbox4", privatHendelse);
        return "Forside";
    }
}
