/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.milosbrkic.bioskop.repository;

import com.milosbrkic.bioskop.domen.Karta;
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
public class KartaRepository extends AbstractRepository<Karta>{

    @Override
    public Karta findById(int id) {
        return entityManager.find(Karta.class, id);
    }
    
    @Override
    public List<Karta> getAll() {
        String query = "select k from Karta k";
        return entityManager.createQuery(query, Karta.class).getResultList();
    }

    @Override
    public void save(Karta object) {
        entityManager.merge(object);
    }

    @Override
    public void deleteById(int id) {
        entityManager.createQuery("delete from Karta k where k.id=:broj").setParameter("broj", id).executeUpdate();
    }
    
    @Override
    public List<Karta> findByQuery(String query){
        return entityManager.createQuery(query, Karta.class).getResultList();
    }


    
}
