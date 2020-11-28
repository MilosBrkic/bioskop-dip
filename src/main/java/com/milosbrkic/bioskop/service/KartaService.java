/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.milosbrkic.bioskop.service;

import com.milosbrkic.bioskop.domen.Karta;
import com.milosbrkic.bioskop.repository.KartaRepository;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 *
 * @author milos
 */
@Service
public class KartaService extends AbstractService<Karta, KartaRepository>{

    public KartaService(KartaRepository repository) {
        super(repository);
    }

    @Override
    public Karta findById(int id) {
        return repository.findById(id);
    }

    @Override
    public List<Karta> getAll() {
        return repository.getAll();
    }

    @Override
    public void save(Karta object) {
        repository.save(object);
    }

    @Override
    public void deleteById(int id) throws Exception {
        repository.deleteById(id);
    }

    @Override
    public List<Karta> findByQuery(String query) {
        return repository.findByQuery(query);
    }
    
}
