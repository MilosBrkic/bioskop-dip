/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.milosbrkic.bioskop.formatter;

import com.milosbrkic.bioskop.domen.Zanr;
import com.milosbrkic.bioskop.repository.ZanrRepository;
import java.text.ParseException;
import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;

/**
 *
 * @author milos
 */
public class ZanrFormater implements Formatter<Zanr> {
    
    private final ZanrRepository repository;

    @Autowired
    public ZanrFormater(ZanrRepository repository) {
        this.repository = repository;
    }

    @Override
    public String print(Zanr z, Locale locale) {
        return z.toString();
    }

    @Override
    public Zanr parse(String string, Locale locale) throws ParseException {
        int id = Integer.parseInt(string);
        return repository.findById(id);
    }
    
}
