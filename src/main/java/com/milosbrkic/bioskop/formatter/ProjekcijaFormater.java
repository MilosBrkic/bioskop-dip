/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.milosbrkic.bioskop.formatter;

import com.milosbrkic.bioskop.domen.Projekcija;
import com.milosbrkic.bioskop.repository.ProjekcijaRepository;
import java.text.ParseException;
import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;

/**
 *
 * @author milos
 */
public class ProjekcijaFormater implements Formatter<Projekcija>{

    private final ProjekcijaRepository repository;

    @Autowired
    public ProjekcijaFormater(ProjekcijaRepository repository) {
        this.repository = repository;
    }
    
    @Override
    public String print(Projekcija p, Locale locale) {
        return p.toString();
    }

    @Override
    public Projekcija parse(String string, Locale locale) throws ParseException {
        System.out.println("========= projekcija id"+string);
        int id = Integer.parseInt(string);
        return repository.findById(id);
    }
    
}
