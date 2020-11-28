/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.milosbrkic.bioskop.formatter;

import com.milosbrkic.bioskop.domen.Osoba;
import com.milosbrkic.bioskop.repository.OsobaRepository;
import java.text.ParseException;
import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;

/**
 *
 * @author milos
 */
public class OsobaFormater implements Formatter<Osoba>{
    
    private final OsobaRepository repository;

    @Autowired
    public OsobaFormater(OsobaRepository repository) {
        this.repository = repository;
    }
    
    

    @Override
    public String print(Osoba o, Locale locale) {
        return o.getImePrezime();
    }

    @Override
    public Osoba parse(String string, Locale locale) throws ParseException {
        int id = Integer.parseInt(string);
        return repository.findById(id);
    }
    
}
