/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.milosbrkic.bioskop.service;

import com.milosbrkic.bioskop.domen.Osoba;
import com.milosbrkic.bioskop.repository.AbstractRepository;
import com.milosbrkic.bioskop.repository.OsobaRepository;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 *
 * @author milos
 */
@Service
public class OsobaService extends AbstractService<Osoba, OsobaRepository>{

    public OsobaService(OsobaRepository repository) {
        super(repository);
    }

    @Override
    public Osoba findById(int id) {
        return repository.findById(id);
    }

    @Override
    public List<Osoba> getAll() {
        return repository.getAll();
    }

    @Override
    public void save(Osoba object) {
        repository.save(object);
    }

    @Override
    public void deleteById(int id) throws Exception {
        repository.deleteById(id);
    }

    @Override
    public List<Osoba> findByQuery(String query) {
        return repository.findByQuery(query);
    }
    
}
