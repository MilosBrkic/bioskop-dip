/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.milosbrkic.bioskop.validator;

import com.milosbrkic.bioskop.domen.Red;
import com.milosbrkic.bioskop.domen.Sala;
import com.milosbrkic.bioskop.repository.SalaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 *
 * @author milos
 */
@Component
public class SalaValidator implements Validator{

    private final SalaRepository repository;

    @Autowired
    public SalaValidator(SalaRepository repository) {
        this.repository = repository;
    }
        
    @Override
    public boolean supports(Class<?> type) {
        return Sala.class.equals(type);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Sala sala = (Sala) o;
        
        if(sala.getBrojSale() < 0)
            errors.rejectValue("brojSale", "sala.brojSale.size");
       
        //proverava da li je broj jedinstven
        if(repository.findById(sala.getBrojSale()) != null)
            errors.rejectValue("brojSale", "sala.brojSale.unique");
        
        
        if(sala.getRedovi() == null || sala.getRedovi().isEmpty())
            errors.rejectValue("redovi", "sala.brojReda.size");      
        else
        for(Red r : sala.getRedovi())
        if(r.getBrojSedista() <= 0){           
            errors.rejectValue("brojSedista", "sala.brojSedista.size");
            break;
        }
           
        
    }   
    
}
