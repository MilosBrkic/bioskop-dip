/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.milosbrkic.bioskop.validator;

import com.milosbrkic.bioskop.domen.Karta;
import com.milosbrkic.bioskop.domen.Korpa;
import com.milosbrkic.bioskop.domen.Projekcija;
import com.milosbrkic.bioskop.repository.KartaRepository;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.Calendar;
import java.util.List;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 *
 * @author milos
 */
@Component
public class KartaValidator implements Validator{

    private final KartaRepository kartaRepository;

    public KartaValidator(KartaRepository kartaRepository) {
        this.kartaRepository = kartaRepository;
    }
    
    
    
    @Override
    public boolean supports(Class<?> type) {
        return Korpa.class.equals(type);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Korpa korpa = (Korpa) o;
        //Karta karta = (Karta) o;
        
        if(korpa.getKarte() == null || korpa.getKarte().isEmpty())
            errors.rejectValue("karte", "karta.empty");
        
        if(korpa.getProjekcija() == null){
             errors.rejectValue("projekcija", "karta.projekcija.empty");
             return;
        }
        if(Common.isInPast(korpa.getProjekcija())){
            errors.rejectValue("projekcija", "karta.projekcija.past");
            return;
        }
        
        
        
        for(Karta k : korpa.getKarte()){
        
            if(k.getBrojSedista() <= 0)
                errors.rejectValue("karte", "karta.brojSedista.negative");

             if(k.getBrojReda() <= 0)
                errors.rejectValue("karte", "karta.brojReda.negative");
             else{
                if(k.getBrojReda() > korpa.getProjekcija().getSala().getRedovi().size())
                    errors.rejectValue("karte", "karta.brojReda.invalid");
                else {
                    int i = k.getBrojReda() - 1;
                    if(k.getBrojSedista() > korpa.getProjekcija().getSala().getRedovi().get(i).getBrojSedista())
                       errors.rejectValue("karte", "karta.brojSedista.invalid");
                 }
             }
             
             //da li postoji vec karta za to mesto
            List<Karta> karte = kartaRepository.findByQuery("select k from Karta k where k.brojReda ="+k.getBrojReda()+" and k.brojSedista = "+k.getBrojSedista()+" and projekcija ="+korpa.getProjekcija().getId());
            
            if(karte != null && !karte.isEmpty()){
                errors.rejectValue("karte", "karta.mesto.unique");
                return;
            }
             
        }
         
        
    }

}
