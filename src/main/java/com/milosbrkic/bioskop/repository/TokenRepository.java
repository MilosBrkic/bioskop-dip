/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.milosbrkic.bioskop.repository;


import com.milosbrkic.bioskop.domen.Token;
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
public class TokenRepository extends AbstractRepository<Token>{

    @Override
    public Token findById(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Token> getAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void save(Token object) {
        entityManager.merge(object);
    }

    @Override
    public void deleteById(int id) throws Exception {
        entityManager.createQuery("delete from Token t where t.id=:broj").setParameter("broj", id).executeUpdate();
    }

    @Override
    public List<Token> findByQuery(String query) {
        return entityManager.createQuery(query, Token.class).getResultList();
    }
    
}
