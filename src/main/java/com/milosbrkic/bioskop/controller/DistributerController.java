/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.milosbrkic.bioskop.controller;

import com.milosbrkic.bioskop.domen.Distributer;
import com.milosbrkic.bioskop.domen.Film;
import com.milosbrkic.bioskop.service.DistributerService;
import com.milosbrkic.bioskop.service.FilmService;
import com.milosbrkic.bioskop.validator.DistributerValidator;
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
@RequestMapping(value = "/distributer")
public class DistributerController {
    
    private final DistributerService distributerService;
    private final FilmService filmService;
    private final DistributerValidator validator;
    private final MessageSource messageSource;
    

    @Autowired
    public DistributerController(DistributerService distributerService, FilmService filmService, DistributerValidator validator, MessageSource messageSource) {
        this.distributerService = distributerService;
        this.filmService = filmService;
        this.validator = validator;
        this.messageSource = messageSource;
    }
    
    @GetMapping
    public ModelAndView home() {
        ModelAndView model = new ModelAndView("distributer/home");
        model.addObject("distributeri", distributerService.getAll());
        return model;
    }

    
    
    @InitBinder
    private void initBinder(WebDataBinder binder) {
        binder.setValidator(validator);
        
    }
    
    @GetMapping(path = "add")
    public String add() {
        return "distributer/add";
    }
    
    @PostMapping(path = "save")
    public String save(@ModelAttribute(name = "distributer") @Validated Distributer distributer, BindingResult result, RedirectAttributes redirectAttributes, Model model, Locale locale){
        if (result.hasErrors()) {
            model.addAttribute("distributer", distributer);
            return "distributer/add";
        } else {
            distributerService.save(distributer);
            String message = messageSource.getMessage("distributer.save" ,null, locale);
            redirectAttributes.addFlashAttribute("message", message);
            return "redirect:/distributer/add";
        }
    }
    
    @GetMapping(path = "/{numberId}/view")
    public ModelAndView view(@PathVariable(name = "numberId") int numberId) {
        ModelAndView model = new ModelAndView("distributer/view");  
        Distributer distributer = distributerService.findById(numberId);
        List<Film> filmovi = filmService.findByQuery("SELECT f FROM Film f WHERE f.distributer = " +distributer.getId());
        model.addObject("filmovi", filmovi);
        model.addObject("distributer", distributer);
        return model;
    }
    
    @GetMapping(value = "/{numberId}/delete")
    public String delete(@PathVariable(name = "numberId") int numberId, RedirectAttributes redirectAttributes, Locale locale) {
        try {
            distributerService.deleteById(numberId);
            String message = messageSource.getMessage("distributer.delete" ,new Object[]{numberId}, locale);
            redirectAttributes.addFlashAttribute("message", message);
        } catch (Exception ex) {
            String error = messageSource.getMessage("distributer.delete.error" ,new Object[]{numberId}, locale);
            redirectAttributes.addFlashAttribute("error", error);
        }
        
        return "redirect:/distributer";        
    }
    
       
    @ModelAttribute(name = "distributer")
    private Distributer getDistributer() {
        return new Distributer();
    }
    
    
}
