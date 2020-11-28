/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.milosbrkic.bioskop.repository;

import com.milosbrkic.bioskop.domen.Osoba;
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
public class OsobaRepository extends AbstractRepository<Osoba>{

    @Override
    public Osoba findById(int id) {
        System.out.println("====================== find by: "+id);
        return entityManager.find(Osoba.class, id);
    }

    @Override
    public List<Osoba> getAll() {
        System.out.println("========================= get all osoba");
        return entityManager.createQuery("select o from Osoba o", Osoba.class).getResultList();
    }

    @Override
    public void save(Osoba object) {
        entityManager.persist(object);
    }

    @Override
    public void deleteById(int id) throws Exception {
        entityManager.createQuery("delete from Osoba o where o.id=:broj").setParameter("broj", id).executeUpdate();
    }

    @Override
    public List<Osoba> findByQuery(String query) {
        return entityManager.createQuery(query, Osoba.class).getResultList();
    }
    
}
