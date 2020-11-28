/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.milosbrkic.bioskop.formatter;

import com.milosbrkic.bioskop.domen.Distributer;
import com.milosbrkic.bioskop.repository.DistributerRepository;
import java.text.ParseException;
import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;

/**
 *
 * @author milos
 */
public class DistributerFormater implements Formatter<Distributer>{

    private final DistributerRepository repository;

    @Autowired
    public DistributerFormater(DistributerRepository repository) {
        this.repository = repository;
    }
    
    
    @Override
    public String print(Distributer d, Locale locale) {
        return d.toString();
    }

    @Override
    public Distributer parse(String name, Locale locale) throws ParseException {
        int id = Integer.parseInt(name);
        Distributer d = repository.findById(id);
        return d;
    }
    
}
