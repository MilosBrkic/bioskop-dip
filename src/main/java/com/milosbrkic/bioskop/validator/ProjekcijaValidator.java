/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.milosbrkic.bioskop.validator;

import com.milosbrkic.bioskop.domen.Film;
import com.milosbrkic.bioskop.domen.Projekcija;
import com.milosbrkic.bioskop.repository.FilmRepository;
import com.milosbrkic.bioskop.repository.ProjekcijaRepository;
import com.milosbrkic.bioskop.repository.SalaRepository;
import java.math.BigDecimal;
import java.sql.Date;
import java.time.Duration;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 *
 * @author milos
 */
@Component
public class ProjekcijaValidator implements Validator {

    private final FilmRepository filmRepository;
    private final SalaRepository salaRepository;
    private final ProjekcijaRepository projekcijaRepository;

    @Autowired
    public ProjekcijaValidator(FilmRepository filmRepository, SalaRepository salaRepository, ProjekcijaRepository projekcijaRepository) {
        this.filmRepository = filmRepository;
        this.salaRepository = salaRepository;
        this.projekcijaRepository = projekcijaRepository;
    }
    
    
    
    @Override
    public boolean supports(Class<?> type) {
        return Projekcija.class.equals(type);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Projekcija projekcija = (Projekcija) o;
        
        if(projekcija.getDatum() == null)
            errors.rejectValue("datum", "projekcija.datum.empty");
        
        if(projekcija.getVreme()== null)
            errors.rejectValue("vreme", "projekcija.vreme.empty");
        
        if(projekcija.getDatum() != null && projekcija.getVreme()!= null && Common.isInPast(projekcija)){
            errors.rejectValue("datum", "projekcija.datum.past");
            errors.rejectValue("vreme", "projekcija.datum.past");
        }
        
        
        if(projekcija.getFilm() == null){
            errors.rejectValue("film", "projekcija.film.empty");
            return;
        }
        
       if(projekcija.getCenaKarte().compareTo(BigDecimal.ZERO) == -1)
            errors.rejectValue("cenaKarte", "karta.cena.negative");
        
       if(!projekcija.getFilm().isAktivan())
            errors.rejectValue("film", "projekcija.aktivan.false");
                      
        if(projekcija.getSala() == null){
            errors.rejectValue("sala", "projekcija.sala.empty");
            return;
        }
          
        if(projekcija.getDatum() != null){
            //projekcije istog datuma u istoj sali
            List<Projekcija> projekcije = projekcijaRepository.findByQuery("select p from Projekcija p where p.sala.brojSale ="+projekcija.getSala().getBrojSale());

            for(Projekcija p : projekcije){

                if(p.getId() == projekcija.getId() || p.isInPast())//u slucaju azuriranja nece ga porediti sa samim sobom / preskace prosle projekcije 
                    continue;

                
                Date pp = p.getDatumVreme();
                Date pk = new Date(p.getDatumVreme().getTime()+ p.getFilm().getTrajanje() * 60000);
                
                Date pocetak = projekcija.getDatumVreme();
                Date kraj = new Date(projekcija.getDatumVreme().getTime()+ projekcija.getFilm().getTrajanje() * 60000);
               /* LocalTime a = p.getVreme().toLocalTime();//pocetak i kraj projekcije sa kojom poredimo            
                LocalTime b = a.plusMinutes(p.getFilm().getTrajanje());

                LocalTime pocetak = projekcija.getVreme().toLocalTime();//pocetak i kraj projekcije koju ubacujemo            
                LocalTime kraj = pocetak.plusMinutes(projekcija.getFilm().getTrajanje());
                Duration d = Duration.between(kraj, pocetak);
                d.*/
                
                if(kraj.after(pp) && pk.after(pocetak )){
                    errors.rejectValue("vreme", "projekcija.vreme.invalid");
                    break;
                }
            }
        }
    }
    

    
}
