/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.milosbrkic.bioskop.domen;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author milos
 */
@Entity
@Table(name = "zanrovi")
public class Zanr implements Serializable{

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;
    private String naziv;
    

    public Zanr(int zanrID, String nazivZanra) {
        this.id = zanrID;
        this.naziv = nazivZanra;
    }

    public Zanr() {
        
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

    @Override
    public String toString() {
        return naziv;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null)
            return false;
        
        return ((Zanr)obj).id == id;
    }

    
    
}
