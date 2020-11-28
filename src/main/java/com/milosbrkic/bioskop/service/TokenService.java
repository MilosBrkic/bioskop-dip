/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.milosbrkic.bioskop.service;

import com.milosbrkic.bioskop.domen.Token;
import com.milosbrkic.bioskop.repository.TokenRepository;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 *
 * @author milos
 */
@Service
public class TokenService extends AbstractService<Token, TokenRepository>{

    public TokenService(TokenRepository repository) {
        super(repository);
    }

    @Override
    public Token findById(int id) {
        return repository.findById(id);
    }

    @Override
    public List<Token> getAll() {
        return repository.getAll();
    }

    @Override
    public void save(Token object) {
        repository.save(object);
    }

    @Override
    public void deleteById(int id) throws Exception {
        repository.deleteById(id);
    }

    @Override
    public List<Token> findByQuery(String query) {
        return repository.findByQuery(query);
    }
    
}
