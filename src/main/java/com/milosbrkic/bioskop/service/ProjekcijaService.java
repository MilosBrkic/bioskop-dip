/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.milosbrkic.bioskop.service;

import com.milosbrkic.bioskop.domen.Projekcija;
import com.milosbrkic.bioskop.repository.ProjekcijaRepository;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 *
 * @author milos
 */
@Service
public class ProjekcijaService extends AbstractService<Projekcija, ProjekcijaRepository>{

    public ProjekcijaService(ProjekcijaRepository repository) {
        super(repository);
    }

    @Override
    public Projekcija findById(int id) {
        return repository.findById(id);
    }

    @Override
    public List<Projekcija> getAll() {
        return repository.getAll();
    }

    @Override
    public void save(Projekcija object) {
        repository.save(object);
    }

    @Override
    public void deleteById(int id) throws Exception {
        repository.deleteById(id);
    }

    @Override
    public List<Projekcija> findByQuery(String query) {
        return repository.findByQuery(query);
    }
    
}
