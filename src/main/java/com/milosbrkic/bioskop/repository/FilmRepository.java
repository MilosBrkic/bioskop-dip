/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.milosbrkic.bioskop.repository;

import com.milosbrkic.bioskop.domen.Film;
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
public class FilmRepository extends AbstractRepository<Film>{
      
    @Override
    public void save(Film film) {
        
        int id = entityManager.merge(film).getId();
        entityManager.flush();
        film.setId(id);
    }

    @Override
    public List<Film> getAll() {     
        String query = "select f from Film f";
        return entityManager.createQuery(query, Film.class).getResultList();
    }
    
    @Override
    public void deleteById(int id) throws Exception{
        entityManager.createQuery("delete from Film f where f.id=:broj").setParameter("broj", id).executeUpdate();
    }
    
    @Override
    public Film findById(int id){
        Film f = entityManager.find(Film.class, id);
        if(f != null){
            f.getZanrovi().size();
            f.getGlumci().size();
        }
        
        return f;
    }
   
    @Override
    public List<Film> findByQuery(String query) {
        return entityManager.createQuery(query, Film.class).getResultList();
    }
    
    
}
