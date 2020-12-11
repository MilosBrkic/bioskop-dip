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
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author milos
 */
@Entity
@Table(name = "distributeri")
public class Distributer implements Serializable{
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;
    private String naziv;
    private String telefon;
    private String email;

    @OneToMany(
        mappedBy="distributer",
        fetch = FetchType.LAZY,
        cascade = CascadeType.ALL,
        orphanRemoval = false
    )
    private List<Film> filmovi;
    
    
    public Distributer(int id, String naziv, String telefon, String email) {
        this.id = id;
        this.naziv = naziv;
        this.telefon = telefon;
        this.email = email;
    }

    public Distributer() {
        
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

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Film> getFilmovi() {
        return filmovi;
    }

    public void setFilmovi(List<Film> filmovi) {
        this.filmovi = filmovi;
    }
    
    

    @Override
    public String toString() {
        return naziv;
    }

     
}
