/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.milosbrkic.bioskop.repository;

import com.milosbrkic.bioskop.domen.Distributer;
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
 */
@Repository
@Transactional(propagation = Propagation.REQUIRED)
public class DistributerRepository extends AbstractRepository<Distributer>{
       
    @Override
    public List<Distributer> getAll() {     
        String query = "select d from Distributer d";
        return entityManager.createQuery(query, Distributer.class).getResultList();
    }
    
    @Override
    public Distributer findById(int id) {
        return entityManager.find(Distributer.class, id);
    }

    @Override
    public void save(Distributer object) {
        entityManager.persist(object);
    }

    @Override
    public void deleteById(int id) throws Exception {
        entityManager.createQuery("delete from Distributer d where d.id=:broj").setParameter("broj", id).executeUpdate();
    }

    @Override
    public List<Distributer> findByQuery(String query) {
        return entityManager.createQuery(query, Distributer.class).getResultList();
    }
}
