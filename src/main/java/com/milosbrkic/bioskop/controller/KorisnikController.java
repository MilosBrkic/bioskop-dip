/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.milosbrkic.bioskop.controller;

import com.milosbrkic.bioskop.domen.Korisnik;
import com.milosbrkic.bioskop.domen.Token;
import com.milosbrkic.bioskop.service.TokenService;
import com.milosbrkic.bioskop.service.KorisnikService;
import com.milosbrkic.bioskop.validator.UserValidator;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.WebAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author milos
 */
@Controller
@RequestMapping(value = "/user")
public class KorisnikController {
    
    public final KorisnikService korisnikService;
    public final TokenService tokenService;
    public final UserValidator validator;  
    private final MessageSource messageSource;
    private final JavaMailSender sender;

    @Autowired
    public KorisnikController(KorisnikService korisnikService, TokenService tokenService, UserValidator validator, MessageSource messageSource, JavaMailSender sender) {
        this.korisnikService = korisnikService;
        this.tokenService = tokenService;
        this.validator = validator;
        this.messageSource = messageSource;
        this.sender = sender;
    }
  
    @InitBinder
    private void initBinder(WebDataBinder binder) {
        binder.setValidator(validator);     
    }
    
    @GetMapping(value = "/login")
    public String login(HttpServletRequest request, Model model, String error, String logout, Locale locale) {

        if (logout != null)
            model.addAttribute("message", messageSource.getMessage("logout.success", null, locale));
        
        if(error != null){
            HttpSession session = request.getSession(false);
            if (session != null) {

                AuthenticationException ex = (AuthenticationException) session.getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
                if (ex != null && ex.getMessage() != null) 
                    if(ex.getMessage().equals("Bad credentials"))
                        model.addAttribute("error", messageSource.getMessage("username.password.invalid" ,null, locale));
                    else {
                        model.addAttribute("error", messageSource.getMessage("account.inactive", null, locale));
                        model.addAttribute("resendLink", messageSource.getMessage("account.resend", null, locale));
                    }
            }
        }
        return "user/login";
    }
    
    @GetMapping(value = "/registerZaposleni")
    public String registerZaposleni() {
        return "user/registerZaposleni";     
    }
    
    @GetMapping(value = "/register")
    public String register() {        
        return "user/register";
    }
    
    //registracija zeposlenog
    @PostMapping(value = "/registerZaposleni")
    public String registerPostZaposleni(@ModelAttribute(name = "korisnik") @Validated Korisnik korisnik, BindingResult result, RedirectAttributes redirectAttributes, Model model,Locale locale) {
        if(result.hasErrors()){
            model.addAttribute("korisnik", korisnik);
            return "user/registerZaposleni";
        }
        else{
            korisnik.setStatus("zaposleni");
            korisnikService.save(korisnik);                                        
            redirectAttributes.addFlashAttribute("message", messageSource.getMessage("register.success" ,null, locale));
            return "redirect:/user/registerZaposleni";
        }          
    }
         
       
    //registracija kupca
    @PostMapping(value = "/register")
    public String registerPost(@ModelAttribute(name = "korisnik") @Validated Korisnik korisnik, BindingResult result, RedirectAttributes redirectAttributes, Model model,Locale locale) {
        if(result.hasErrors()){
            model.addAttribute("korisnik", korisnik);
            return "user/register";
        }
        else{
            Token token = new Token(korisnik);
            korisnikService.save(korisnik);            
            tokenService.save(token);
           
            sendEmail(korisnik.getEmail(), token.getVrednost());

            redirectAttributes.addFlashAttribute("message", messageSource.getMessage("email.verification.send" ,new Object[]{korisnik.getEmail()}, locale));
            return "redirect:/user/register";
        }          
    }
    
    
    
    @GetMapping(value="/confirm-account")
    public ModelAndView confirmUserAccount(@RequestParam("token")String tokenValue, Locale locale){
        ModelAndView model = new ModelAndView("user/confirm");         
        try{
            Token token = tokenService.findByQuery("select t from ConfirmationToken t where t.vrednost = '"+tokenValue+"'").get(0);
            Korisnik user = korisnikService.findById(token.getKorisnik().getId());
 
            user.setStatus("kupac");
            korisnikService.save(user);
            model.addObject("message",messageSource.getMessage("email.verification.success" ,null, locale));
        }
        catch(Exception e){    
            model.addObject("error",messageSource.getMessage("email.verification.error" ,null, locale));
        }
        return model;
    }
    
    @RequestMapping(value = "/resend-email", method = RequestMethod.GET)
    public String resendTokenPage(){
        return "user/resendEmail";
    }
    
    @RequestMapping(value = "/resend-email", method = RequestMethod.POST)
    public ModelAndView resendToken(@RequestParam("email") String email, Locale locale){
        ModelAndView model = new ModelAndView("user/login");
        try{           
            Korisnik korisnik = korisnikService.findByQuery("select k from Korisnik k where k.email = '"+email+"' and k.status = 'neaktivan' ").get(0);
            //stari token          
            Token token = tokenService.findByQuery("select t from ConfirmationToken t where t.korisnik = "+korisnik.getId()).get(0);
            long id = token.getId();
            token = new Token(korisnik);
            token.setId(id);
            //update starog tokena sa novim datumom i vrednosti
            tokenService.save(token);
            sendEmail(email, token.getVrednost());                       
            }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
        model.addObject("message",messageSource.getMessage("email.verification.send" ,null, locale));
        return model;
    }
    
    private void sendEmail(String emailAdress, String token){
        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(emailAdress);
        email.setSubject("Potvrda registracije");
        email.setText("Da biste potvrdili registraciju kliknite na sledeći link : http://localhost:8080/Bioskop/user/confirm-account?token="+token);          
        email.setFrom("brkicmilos97@gmail.com");
        sender.send(email);
    }
    
   
    @ModelAttribute(name = "korisnik")
    public Korisnik getUser(){
        return new Korisnik();
    }
    
    
}
