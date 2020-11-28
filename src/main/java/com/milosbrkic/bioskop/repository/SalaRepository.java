/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.milosbrkic.bioskop.repository;

import com.milosbrkic.bioskop.domen.Sala;
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
public class SalaRepository extends AbstractRepository<Sala>{
    

    @Override
    public void save(Sala sala) {
        entityManager.persist(sala);
    }

    @Override
    public List<Sala> getAll() {     
        String query = "select s from Sala s";
        return entityManager.createQuery(query, Sala.class).getResultList();
    }


    @Override
    public void deleteById(int numberId) throws Exception{
        entityManager.createQuery("delete from Sala s where s.brojSale=:broj").setParameter("broj", numberId).executeUpdate();
    }
    
    @Override
    public Sala findById(int id){     
        Sala s = entityManager.find(Sala.class, id);
        //s.getRedovi().size();
        return s;    
    }

    @Override
    public List<Sala> findByQuery(String query) {
        return null;
    }
}
