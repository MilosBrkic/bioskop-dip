/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.milosbrkic.bioskop.repository;

import com.milosbrkic.bioskop.domen.Korisnik;
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
public class KorisnikRepository extends AbstractRepository<Korisnik>{

    @Override
    public Korisnik findById(int id) {
        return entityManager.find(Korisnik.class, id);
    }

    @Override
    public List<Korisnik> getAll() {
        return entityManager.createQuery("select k from Korisnik k").getResultList();
    }
    
    public Korisnik findByName(String name){
        List<Korisnik> lista = entityManager.createQuery("select k from Korisnik k where k.username=:username").setParameter("username", name).getResultList();
        if(lista != null && !lista.isEmpty())
            return lista.get(0);
        else
            return null;
    }

    @Override
    public void save(Korisnik z) {
        int id = entityManager.merge(z).getId();
        entityManager.flush();
        z.setId(id);
    }

    @Override
    public void deleteById(int id) {
        entityManager.createQuery("delete from Korisnik k where k.id=:broj").setParameter("broj", id).executeUpdate();
    }

    @Override
    public List<Korisnik> findByQuery(String query) {
        return entityManager.createQuery(query, Korisnik.class).getResultList();
    }
    
    public void deleteExpired(){
        try{
        entityManager.createQuery("delete from Korisnik k where k.status = 'neaktivan' and k.id IN (select t.korisnik from ConfirmationToken t where t.datumIsteka < NOW())").executeUpdate();
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
}
