/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.milosbrkic.bioskop.formatter;

import com.milosbrkic.bioskop.domen.Film;
import com.milosbrkic.bioskop.repository.FilmRepository;
import java.text.ParseException;
import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;

/**
 *
 * @author milos
 */
public class FilmFormater implements Formatter<Film>{

    private final FilmRepository repository;

    @Autowired
    public FilmFormater(FilmRepository repository) {
        this.repository = repository;
    }
       
    @Override
    public String print(Film f, Locale locale) {
        return f.toString();
    }

    @Override
    public Film parse(String string, Locale locale) throws ParseException {
        int id = Integer.parseInt(string);
        return repository.findById(id);
    }
    
}
