/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import java.sql.Time;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Henrik
 */
public class FormFinnRom {
    private String notat;
    private int skjerm;
    private int tavle;
    private int projektor;
    private int sitteplass;
    private int storrelse;
    private String tittel;
    private String type;
    private Date fraDato;
    private Time fraTid;
    private int varighet;

    public int getStorrelse() {
        return storrelse;
    }

    public void setStorrelse(int storrelse) {
        this.storrelse = storrelse;
    }

    public String getTittel() {
        return tittel;
    }

    public void setTittel(String tittel) {
        this.tittel = tittel;
    }
    

    public List getTypeList(){
        String[] s = {"Privat","Forelesning","Møte"};
        return Arrays.asList(s);
    }
    
    public List getUtstyrList(){
        String[] s = {"Projektor","Skjerm","Tavle",""};
        return Arrays.asList(s);
    }

    public int getSkjerm() {
        return skjerm;
    }

    public void setSkjerm(int skjerm) {
        this.skjerm = skjerm;
    }

    public int getTavle() {
        return tavle;
    }

    public void setTavle(int tavle) {
        this.tavle = tavle;
    }

    public int getProjektor() {
        return projektor;
    }

    public void setProjektor(int projektor) {
        this.projektor = projektor;
    }

    public int getSitteplass() {
        return sitteplass;
    }

    public void setSitteplass(int sitteplass) {
        this.sitteplass = sitteplass;
    }
    
        public List getTiderList(){
        String[] s = {"0600","0700","0800","0900","1000","1100","1200","1300","1400","1500","1600","1700","1800","1900","2000","2100","2200","2300"};
        return Arrays.asList(s);
    }
    
    public List getVarighetList(){
        String[] s = {"1","2","3"};
        return Arrays.asList(s);
    }
    
    public void setType(String type) {
        this.type = type;
    }

    public void setFraDato(Date fraDato) {
        this.fraDato = fraDato;
    }

    public void setFraTid(Time fraTid) {
        this.fraTid = fraTid;
    }

    public void setVarighet(int varighet) {
        this.varighet = varighet;
    }
    
    

    public String getType() {
        return type;
    }

    public Date getFraDato() {
        return fraDato;
    }

    public Time getFraTid() {
        return fraTid;
    }

    public int getVarighet() {
        return varighet;
    }
    
    


}
