/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.sql.Timestamp;

/**
 *
 * @author Arne
 */
public class RomBestilling {
    private String romId;
    private Timestamp startDato;
    private Timestamp sluttDato;
    private String eierId;
    
    public String getRomId(){
        return romId;
    }
    public Timestamp getStartDato(){
        return startDato;
    }
    public Timestamp getSluttDato(){
        return sluttDato;
    }
    public String getEierId(){
        return eierId;
    }
    public void setRomId(String romId){
        this.romId = romId;
    }
    public void setStartDato(Timestamp startDato){
        this.startDato = startDato;
    }
    public void setSluttDato(Timestamp sluttDato){
        this.sluttDato = sluttDato;
    }
    public void setEierId(String eierId){
        this.eierId = eierId;
    }
    
}
