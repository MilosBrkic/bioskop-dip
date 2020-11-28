/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.milosbrkic.bioskop.service;

import com.milosbrkic.bioskop.domen.Zanr;
import com.milosbrkic.bioskop.repository.ZanrRepository;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 *
 * @author milos
 */
@Service
public class ZanrService extends AbstractService<Zanr, ZanrRepository>{

    public ZanrService(ZanrRepository repository) {
        super(repository);
    }

    @Override
    public Zanr findById(int id) {
        return repository.findById(id);
    }

    @Override
    public List<Zanr> getAll() {
        return repository.getAll();
    }

    @Override
    public void save(Zanr object) {
        repository.save(object);
    }

    @Override
    public void deleteById(int id) throws Exception {
        repository.deleteById(id);
    }

    @Override
    public List<Zanr> findByQuery(String query) {
        return repository.findByQuery(query);
    }
    
}
