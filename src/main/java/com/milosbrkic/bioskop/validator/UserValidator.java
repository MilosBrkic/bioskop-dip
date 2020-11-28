/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.milosbrkic.bioskop.validator;

import com.milosbrkic.bioskop.domen.Korisnik;
import com.milosbrkic.bioskop.repository.KorisnikRepository;
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
public class UserValidator implements Validator{

    private final KorisnikRepository repository;

    @Autowired
    public UserValidator(KorisnikRepository repository) {
        this.repository = repository;
    }
    
  
    @Override
    public boolean supports(Class<?> type) {
        return Korisnik.class.equals(type);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Korisnik korisnik = (Korisnik) o;
        
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "user.username.empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "user.password.empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "passwordMatch", "user.passwordMatch.empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "user.email.empty");
        
        if(!korisnik.getPassword().equals(korisnik.getPasswordMatch()))
            errors.rejectValue("passwordMatch", "user.passwordMatch.invalid");
        
        
        Korisnik zap = repository.findByName(korisnik.getUsername());
        if(zap != null)
            errors.rejectValue("username", "user.username.unique");
        
        List<Korisnik> lista = repository.findByQuery("select k from Korisnik k where k.email = '"+korisnik.getEmail()+"'");
        if(lista != null && !lista.isEmpty())
            errors.rejectValue("email", "user.email.unique");
        
    }
    
}
