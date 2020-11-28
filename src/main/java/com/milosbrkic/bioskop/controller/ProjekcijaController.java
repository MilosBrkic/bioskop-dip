/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.milosbrkic.bioskop.controller;

import com.milosbrkic.bioskop.domen.Film;
import com.milosbrkic.bioskop.domen.Projekcija;
import com.milosbrkic.bioskop.domen.Sala;
import com.milosbrkic.bioskop.service.FilmService;
import com.milosbrkic.bioskop.service.ProjekcijaService;
import com.milosbrkic.bioskop.service.SalaService;
import com.milosbrkic.bioskop.validator.ProjekcijaValidator;
import java.util.List;
import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author milos
 */
@Controller
@RequestMapping(value = "/projekcija")
public class ProjekcijaController {
    
    private final ProjekcijaService projekcijaService;
    private final FilmService filmService;
    private final SalaService salaService;
    private final ProjekcijaValidator validator;
    private final MessageSource messageSource;

    @Autowired
    public ProjekcijaController(ProjekcijaService projekcijaService, FilmService filmService, SalaService salaService, ProjekcijaValidator validator, MessageSource messageSource) {
        this.projekcijaService = projekcijaService;
        this.filmService = filmService;
        this.salaService = salaService;
        this.validator = validator;
        this.messageSource = messageSource;
    }
    
     
    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.setValidator(validator);
    }
   
    @GetMapping
    public ModelAndView home() {
        ModelAndView model = new ModelAndView("projekcija/home");
        model.addObject("projekcije", projekcijaService.getAll());
        return model;
    }
    
    @GetMapping(path = "search")
    public ModelAndView search(@ModelAttribute(name = "projekcija") Projekcija projekcija, Locale locale) {       
        ModelAndView model = new ModelAndView("projekcija/home");
        List<Projekcija> projekcije = projekcijaService.findByQuery("select p from Projekcija p where p.datum = '"+projekcija.getDatum()+"'");
        model.addObject("projekcije", projekcije);
        if(projekcije == null || projekcije.isEmpty())
            model.addObject("error", messageSource.getMessage("projekcija.search.error" ,null, locale));
        return model;
    }
    
    @GetMapping(path = "/{numberId}/view")
    public ModelAndView view(@PathVariable(name = "numberId") int numberId) {
        ModelAndView model = new ModelAndView("projekcija/view");      
        model.addObject("projekcija", projekcijaService.findById(numberId));
        return model;
    }
    
    @GetMapping(path = "add")
    public ModelAndView add() {
        ModelAndView model = new ModelAndView("projekcija/add");
        return model;
    }
    
    @GetMapping(path = "/{numberId}/edit")
    public ModelAndView edit(@PathVariable(name = "numberId") int numberId) {
        ModelAndView model = new ModelAndView("projekcija/edit");               
        model.addObject("projekcija", projekcijaService.findById(numberId));
        return model;
    }
    
    
    @PostMapping(path = "save")
    public String save(@ModelAttribute(name = "projekcija") @Validated Projekcija projekcija, BindingResult result, RedirectAttributes redirectAttributes, Model model, Locale locale){  
        if (result.hasErrors()) {
            model.addAttribute("projekcija", projekcija);
            return "projekcija/add";
        } else {
            projekcijaService.save(projekcija);
            redirectAttributes.addFlashAttribute("message", messageSource.getMessage("projekcija.save" ,null, locale));
            return "redirect:/projekcija/add";
        }       
    }
    
    @PostMapping(path = "/{numberId}/save")
    public String update(@ModelAttribute(name = "projekcija") @Validated Projekcija projekcija, BindingResult result, RedirectAttributes redirectAttributes, Model model, @PathVariable(name = "numberId") int numberId, Locale locale){
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("projekcija", projekcija);
            return "projekcija/edit";
        } else {                  
            projekcijaService.save(projekcija);
            redirectAttributes.addFlashAttribute("message", messageSource.getMessage("projekcija.update" ,null, locale));
            return "redirect:/projekcija/"+numberId+"/edit";
        }      
    }
    
    @GetMapping(value = "/{numberId}/delete")
    public String delete(@PathVariable(name = "numberId") int numberId, RedirectAttributes redirectAttributes, Locale locale) {
        try {
            projekcijaService.deleteById(numberId);
            redirectAttributes.addFlashAttribute("message", messageSource.getMessage("projekcija.delete" ,new Object[]{numberId}, locale));
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("error", messageSource.getMessage("projekcija.delete.error" ,new Object[]{numberId}, locale));
        }    
        return "redirect:/projekcija";       
    }

    
   
    
    @ModelAttribute(name = "projekcija")
    private Projekcija getProjekcija() {
        return new Projekcija();
    }
    
    @ModelAttribute(name = "filmovi")
    private List<Film> getFilmovi() {
        return filmService.findByQuery("select f from Film f where f.aktivan = true");
    }
    
    @ModelAttribute(name = "sale")
    private List<Sala> getSale() {
        return salaService.getAll();
    }
}
