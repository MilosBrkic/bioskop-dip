/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.milosbrkic.bioskop.validator;

import com.milosbrkic.bioskop.domen.Distributer;
import com.milosbrkic.bioskop.repository.DistributerRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 *
 * @author milos
 */
@Component
public class DistributerValidator implements Validator {
    
    private final DistributerRepository repository;

    @Autowired
    public DistributerValidator(DistributerRepository repository) {
        this.repository = repository;
    }
    
    @Override
    public boolean supports(Class<?> type) {
        return Distributer.class.equals(type);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Distributer distributer = (Distributer) o;
        
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "naziv", "film.naziv.empty");
        
        List<Distributer> distributeri = repository.findByQuery("select d from Distributer d where d.naziv='"+distributer.getNaziv()+"'");
        if(distributeri != null && !distributeri.isEmpty() && distributer.getId() == 0)
            errors.rejectValue("naziv", "distributer.naziv.unique");
        
    }
    
}
