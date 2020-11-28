/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.milosbrkic.bioskop.service;

import com.milosbrkic.bioskop.repository.AbstractRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author milos
 * @param <T> type of domain
 * @param <R> type of repository
 */
@Service
public abstract class AbstractService<T,R> {
    
    @Autowired
    protected R repository;

    public AbstractService(R repository) {
        this.repository = repository;
    }
      
    public abstract T findById(int id);    

    public abstract List<T> getAll(); 

    public abstract void save(T object); 

    public abstract void deleteById(int id) throws Exception; 

    public abstract List<T> findByQuery(String query);
    
}
