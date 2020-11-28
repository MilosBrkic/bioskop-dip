/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.milosbrkic.bioskop.domen;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author milos
 */
@Entity
@Table(name = "token")
public class Token implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String vrednost;
    
    @Column(name = "datum_isteka")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datumIsteka;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "korisnik")
    private Korisnik korisnik;

    public Token() {
    }

    public Token(Korisnik korisnik) {
        this.korisnik = korisnik;
        
        Calendar cal = Calendar.getInstance();
	cal.setTime(new Timestamp(cal.getTime().getTime()));
	cal.add(Calendar.HOUR, 24);

        datumIsteka = new Date(cal.getTime().getTime());
        vrednost = UUID.randomUUID().toString();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getVrednost() {
        return vrednost;
    }

    public void setVrednost(String vrednost) {
        this.vrednost = vrednost;
    }

    public Date getDatumIsteka() {
        return datumIsteka;
    }

    public void setDatumIsteka(Date datumIsteka) {
        this.datumIsteka = datumIsteka;
    }

    public Korisnik getKorisnik() {
        return korisnik;
    }

    public void setKorisnik(Korisnik korisnik) {
        this.korisnik = korisnik;
    }

    

   

    
}
