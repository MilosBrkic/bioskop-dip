/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.milosbrkic.bioskop.repository;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author milos
 * @param <T>
 */
@Repository
@Transactional(propagation = Propagation.REQUIRED)
public abstract class AbstractRepository<T> {
    
    @PersistenceContext(type = PersistenceContextType.TRANSACTION)
    protected EntityManager entityManager;
    
    @Transactional
    public abstract T findById(int id);
    
    @Transactional
    public abstract List<T> getAll();
    
    @Transactional
    public abstract void save(T object);
    
    @Transactional
    public abstract void deleteById(int id) throws Exception;
    
    @Transactional
    public abstract List<T> findByQuery(String query);
    
}
