/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.milosbrkic.bioskop.domen;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 *
 * @author milos
 */
@Entity
@Table(name = "filmovi")
public class Film implements Serializable{
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;
    
    private String naziv;
    private int trajanje;//u minutima
    
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "pripadnost_zanru", 
        joinColumns = @JoinColumn(name = "film"), 
        inverseJoinColumns = @JoinColumn(name = "zanr"))
    private List<Zanr> zanrovi;
    
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "distributer")
    private Distributer distributer;
            
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "reziser")
    private Osoba reziser;
    
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "uloga", 
        joinColumns = @JoinColumn(name = "film"), 
        inverseJoinColumns = @JoinColumn(name = "osoba"))
    private List<Osoba> glumci;
    
    private boolean aktivan;
    private String opis;
    
    @Transient
    private String url;
    
    private int godina;
    private double ocena;

   
    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getGodina() {
        return godina;
    }

    public void setGodina(int godina) {
        this.godina = godina;
    }

    public Osoba getReziser() {
        return reziser;
    }

    public void setReziser(Osoba reziser) {
        this.reziser = reziser;
    }

    public List<Osoba> getGlumci() {
        return glumci;
    }

    public void setGlumci(List<Osoba> glumci) {
        this.glumci = glumci;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public int getTrajanje() {
        return trajanje;
    }

    public void setTrajanje(int trajanje) {
        this.trajanje = trajanje;
    }

    public List<Zanr> getZanrovi() {
        return zanrovi;
    }

    public void setZanrovi(List<Zanr> zanrovi) {
        this.zanrovi = zanrovi;
    }

    public Distributer getDistributer() {
        return distributer;
    }

    public void setDistributer(Distributer distributer) {
        this.distributer = distributer;
    }

    public boolean isAktivan() {
        return aktivan;
    }

    public void setAktivan(boolean aktivan) {
        this.aktivan = aktivan;
    }
    
     public double getOcena() {
        return ocena;
    }

    public void setOcena(double ocena) {
        this.ocena = ocena;
    }

    @Override
    public String toString() {
        return naziv;
    }
    
          
}
