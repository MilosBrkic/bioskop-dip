/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.milosbrkic.bioskop.controller;

import com.milosbrkic.bioskop.domen.Sala;
import com.milosbrkic.bioskop.service.SalaService;
import com.milosbrkic.bioskop.validator.SalaValidator;
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
@RequestMapping(value = "/sala")
public class SalaController {
    
    private final SalaService service;
    private final SalaValidator validator;
    private final MessageSource messageSource;
    
    @Autowired
    public SalaController(SalaService service, SalaValidator validator, MessageSource messageSource) {
        this.service = service;
        this.validator = validator;
        this.messageSource = messageSource;
    }
    
    
    @InitBinder
    private void initBinder(WebDataBinder binder) {
        binder.setValidator(validator);     
    }
    
    @GetMapping
    public String home() {
        return "sala/home";
    }
    
    @GetMapping(path = "add")
    public String add() {
        return "sala/add";
    }
    
    @PostMapping(path = "save")
    public String save(@ModelAttribute(name = "sala") @Validated Sala sala,BindingResult result, RedirectAttributes redirectAttributes, Model model, Locale locale){
        if (result.hasErrors()) {
            model.addAttribute("sala", sala);
            return "sala/add";
        } else {           
            service.save(sala);
            String message = messageSource.getMessage("sala.save" ,null, locale);
            redirectAttributes.addFlashAttribute("message", message);
            return "redirect:/sala/add";
        }
    }
    
    
    @GetMapping(value = "/{numberId}/delete")
    public String delete(@PathVariable(name = "numberId") int numberId, RedirectAttributes redirectAttributes, Locale locale) {
        try {
            service.deleteById(numberId);
            String message = messageSource.getMessage("sala.delete" ,new Object[]{numberId}, locale);
            redirectAttributes.addFlashAttribute("message", message);
        } catch (Exception ex) {            
            String error = messageSource.getMessage("sala.delete.error" ,new Object[]{numberId}, locale);
            redirectAttributes.addFlashAttribute("error", error);
        }
        
        return "redirect:/sala";    
    }
    
    @GetMapping(path = "/{numberId}/view")
    public ModelAndView view(@PathVariable(name = "numberId") int numberId) {
        ModelAndView model = new ModelAndView("sala/view");       
        Sala sala = service.findById(numberId);
        model.addObject("sala", sala);
        return model;
    }

    
    @ModelAttribute(name = "sala")
    private Sala getSala() {
        return new Sala(0, 0);
    }
    
    @ModelAttribute(name = "sale")
    private List<Sala> getSale() {
        return service.getAll();
    }
}
