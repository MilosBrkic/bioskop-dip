/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.milosbrkic.bioskop.service;

import com.milosbrkic.bioskop.domen.Film;
import com.milosbrkic.bioskop.repository.FilmRepository;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 *
 * @author milos
 */
@Service
public class FilmService extends AbstractService<Film, FilmRepository>{

    public FilmService(FilmRepository repository) {
        super(repository);
    }

    @Override
    public Film findById(int id) {
        return repository.findById(id);
    }

    @Override
    public List<Film> getAll() {
        return repository.getAll();
    }

    @Override
    public void save(Film object) {
        repository.save(object);
    }

    @Override
    public void deleteById(int id) throws Exception {
        repository.deleteById(id);
    }

    @Override
    public List<Film> findByQuery(String query) {
        return repository.findByQuery(query);
    }
    
}
