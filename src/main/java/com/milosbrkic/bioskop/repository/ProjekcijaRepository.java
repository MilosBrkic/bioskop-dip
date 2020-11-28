/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.milosbrkic.bioskop.repository;

import com.milosbrkic.bioskop.domen.Projekcija;
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
public class ProjekcijaRepository extends AbstractRepository<Projekcija>{
      
    
    @Override
    public List<Projekcija> getAll() {     
        String query = "select p from Projekcija p order by p.datum desc, p.vreme desc";
        return entityManager.createQuery(query, Projekcija.class).getResultList();
    }
    
    @Override
    public void save(Projekcija p){
        entityManager.merge(p);
    }
    
    @Override
    public void deleteById(int numberId) throws Exception{
        entityManager.createQuery("delete from Projekcija p where p.id=:broj").setParameter("broj", numberId).executeUpdate();
    }
    
    @Override
    public Projekcija findById(int id){
        Projekcija p = entityManager.find(Projekcija.class, id);
        p.getKarte().size();
        return p;
    }

    @Override
    public List<Projekcija> findByQuery(String query) {
        return entityManager.createQuery(query, Projekcija.class).getResultList();
    }
    
    
    
}
