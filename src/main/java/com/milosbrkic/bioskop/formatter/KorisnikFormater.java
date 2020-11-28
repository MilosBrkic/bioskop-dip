/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.milosbrkic.bioskop.formatter;

import com.milosbrkic.bioskop.domen.Korisnik;
import com.milosbrkic.bioskop.repository.KorisnikRepository;
import java.text.ParseException;
import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;

/**
 *
 * @author milos
 */
public class KorisnikFormater implements Formatter<Korisnik>{

    private final KorisnikRepository repository;

    @Autowired
    public KorisnikFormater(KorisnikRepository repository) {
        this.repository = repository;
    }
      
    @Override
    public String print(Korisnik z, Locale locale) {
        return z.toString();
    }

    @Override
    public Korisnik parse(String string, Locale locale) throws ParseException {
        int id = Integer.parseInt(string);
        return repository.findById(id);
    }
    
}
