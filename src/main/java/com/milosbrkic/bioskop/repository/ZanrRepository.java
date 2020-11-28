/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.milosbrkic.bioskop.repository;

import com.milosbrkic.bioskop.domen.Zanr;
import java.util.List;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author milos
 */
@Repository
@Transactional(propagation = Propagation.REQUIRED)
public class ZanrRepository extends AbstractRepository<Zanr>{
    

    @Override
    public List<Zanr> getAll() {     
        String query = "select z from Zanr z";
        return entityManager.createQuery(query, Zanr.class).getResultList();
    }


    @Override
    public Zanr findById(int id) {
        return entityManager.find(Zanr.class, id);
    }

    @Override
    public void save(Zanr object) {
        entityManager.persist(object);
    }

    @Override
    public void deleteById(int id) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Zanr> findByQuery(String query) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
