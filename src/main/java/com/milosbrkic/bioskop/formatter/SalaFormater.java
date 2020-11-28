/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.milosbrkic.bioskop.formatter;

import com.milosbrkic.bioskop.domen.Sala;
import com.milosbrkic.bioskop.repository.SalaRepository;
import java.text.ParseException;
import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;

/**
 *
 * @author milos
 */
public class SalaFormater implements Formatter<Sala>{
    
    private final SalaRepository repository;

    @Autowired
    public SalaFormater(SalaRepository repository) {
        this.repository = repository;
    }
    
    

    @Override
    public String print(Sala s, Locale locale) {
        return s.toString();
    }

    @Override
    public Sala parse(String string, Locale locale) throws ParseException {
        int id = Integer.parseInt(string);
        return repository.findById(id);
    }
    
}
