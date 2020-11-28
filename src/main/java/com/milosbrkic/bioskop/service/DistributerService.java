/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.milosbrkic.bioskop.service;

import com.milosbrkic.bioskop.domen.Distributer;
import com.milosbrkic.bioskop.repository.DistributerRepository;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 *
 * @author milos
 */
@Service
public class DistributerService extends AbstractService<Distributer, DistributerRepository>{

    public DistributerService(DistributerRepository repository) {
        super(repository);
    }

    @Override
    public Distributer findById(int id) {
        return repository.findById(id);
    }

    @Override
    public List<Distributer> getAll() {
        return repository.getAll();
    }

    @Override
    public void save(Distributer object) {
        repository.save(object);
    }

    @Override
    public void deleteById(int id) throws Exception {
        repository.deleteById(id);
    }

    @Override
    public List<Distributer> findByQuery(String query) {
        return repository.findByQuery(query);
    }
    
}
