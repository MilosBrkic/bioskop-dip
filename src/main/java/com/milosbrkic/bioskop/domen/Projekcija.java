/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.milosbrkic.bioskop.domen;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Time;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author milos
 */
@Entity
@Table(name = "projekcije")
public class Projekcija implements Serializable{
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;
    
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "film")
    private Film film;
    
    
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "sala")
    private Sala sala;
    
    private Date datum;
    private Time vreme;
    
    @Column(name = "cena_karte")
    private BigDecimal cenaKarte;
    
    @OneToMany(
        mappedBy="projekcija",
        fetch = FetchType.LAZY,
        cascade = CascadeType.ALL,
        orphanRemoval = false
    )   
    private List<Karta> karte;

    public List<Karta> getKarte() {
        return karte;
    }

    public void setKarte(List<Karta> karte) {
        this.karte = karte;
    }

    public Projekcija(Film film, Sala sala, Date datum, Time vreme) {
        this.film = film;
        this.sala = sala;
        this.datum = datum;
        this.vreme = vreme;
    }

    

    public Projekcija() {
        
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Film getFilm() {
        return film;
    }

    public void setFilm(Film film) {
        this.film = film;
    }

    public Sala getSala() {
        return sala;
    }

    public void setSala(Sala sala) {
        this.sala = sala;
    }

    public Date getDatum() {
        return datum;
    }
    
    public String getFormatDatum(){
        if(datum != null)
            return new SimpleDateFormat("dd.MM.yyyy.").format(datum);
        else
            return "-";
    }

    public void setDatum(Date datum) {
        this.datum = datum;
    }
    
    //spaja datum i vreme
    public Date getDatumVreme(){
        Calendar dateCal = Calendar.getInstance();
        dateCal.setTime(datum);
        Calendar timeCal = Calendar.getInstance();
        timeCal.setTime(vreme);

        dateCal.set(Calendar.HOUR_OF_DAY, timeCal.get(Calendar.HOUR_OF_DAY));
        dateCal.set(Calendar.MINUTE, timeCal.get(Calendar.MINUTE));
 
        return new Date(dateCal.getTime().getTime());//datum i vreme projekcije
    }
    
    public boolean isInPast(){
        Date danas = new Date(new java.util.Date().getTime());//danas
        return danas.after(getDatumVreme());
    }
    
    public Time getVreme() {
        return vreme;
    }

    public void setVreme(Time vreme) {
        this.vreme = vreme;
    }
      
    public String getFormatVreme(){
        String pocetak = new SimpleDateFormat("HH:mm").format(vreme);
        if(film != null){           
            String kraj = new SimpleDateFormat("HH:mm").format(new Time(vreme.getTime() + 60000 * film.getTrajanje()));
            return pocetak+"-"+kraj;
        }
        else
            return pocetak;
    }

    public BigDecimal getCenaKarte() {
        return cenaKarte;
    }

    public void setCenaKarte(BigDecimal cenaKarte) {
        this.cenaKarte = cenaKarte;
    }
    
    

    @Override
    public String toString() {
        return new SimpleDateFormat("dd.MM.yyyy.").format(datum)+" "+film.getNaziv()+" u "+vreme+" sala "+sala.getBrojSale();
    }
       
  
}
