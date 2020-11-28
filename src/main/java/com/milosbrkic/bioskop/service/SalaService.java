/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.milosbrkic.bioskop.service;

import com.milosbrkic.bioskop.domen.Red;
import com.milosbrkic.bioskop.domen.Sala;
import com.milosbrkic.bioskop.repository.SalaRepository;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 *
 * @author milos
 */
@Service
public class SalaService extends AbstractService<Sala, SalaRepository>{

    public SalaService(SalaRepository repository) {
        super(repository);
    }

    @Override
    public Sala findById(int id) {
       return repository.findById(id);
    }

    @Override
    public List<Sala> getAll() {
        return repository.getAll();
    }

    @Override
    public void save(Sala sala) {
        int ukupanBrojSedista = 0;   
        
        for(Red r : sala.getRedovi()){
            r.setSala(sala);
            ukupanBrojSedista+= r.getBrojSedista();
        }
        sala.setBrojSedista(ukupanBrojSedista);
        
        repository.save(sala);
    }

    @Override
    public void deleteById(int id) throws Exception {
        repository.deleteById(id);
    }

    @Override
    public List<Sala> findByQuery(String query) {
       return repository.findByQuery(query);
    }
    
}
