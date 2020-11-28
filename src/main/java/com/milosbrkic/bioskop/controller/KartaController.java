/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.milosbrkic.bioskop.controller;

import com.milosbrkic.bioskop.domen.Karta;
import com.milosbrkic.bioskop.domen.Korisnik;
import com.milosbrkic.bioskop.domen.Korpa;
import com.milosbrkic.bioskop.service.KorisnikService;
import com.milosbrkic.bioskop.service.KartaService;
import com.milosbrkic.bioskop.service.PayPalService;
import com.milosbrkic.bioskop.service.ProjekcijaService;
import com.milosbrkic.bioskop.validator.KartaValidator;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import java.math.BigDecimal;
import java.util.Locale;
import java.util.Random;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.context.SecurityContextHolder;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author milos
 */
@Controller
@RequestMapping(value = "/karta")
public class KartaController {
    
    private final KartaService kartaService;
    private final ProjekcijaService projekcijaService;
    private final KorisnikService korisnikService;
    private final PayPalService payPalService;
    private final MessageSource messageSource;
    private final KartaValidator validator;
    private final JavaMailSender sender;
        
    @Autowired
    public KartaController(KartaService kartaService, ProjekcijaService projekcijaService, KorisnikService korisnikService, PayPalService payPalService, MessageSource messageSource, KartaValidator validator, JavaMailSender sender) {
        this.kartaService = kartaService;
        this.projekcijaService = projekcijaService;
        this.korisnikService = korisnikService;
        this.payPalService = payPalService;
        this.messageSource = messageSource;
        this.validator = validator;
        this.sender = sender;
    }

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.setValidator(validator);    
    }

    
    @GetMapping(path = "/{numberId}/sell")
    public ModelAndView sell(@PathVariable(name = "numberId") int numberId) {
        ModelAndView model = new ModelAndView("karta/sell");
        Korpa k = new Korpa();
        k.setProjekcija(projekcijaService.findById(numberId));
        model.addObject("korpa", k);
        return model;
    }
    
    @GetMapping(path = "/{numberId}/buy")
    public ModelAndView buy(@PathVariable(name = "numberId") int numberId) {
        ModelAndView model = new ModelAndView("karta/buy");
        Korpa k = new Korpa();
        k.setProjekcija(projekcijaService.findById(numberId));
        model.addObject("korpa", k);
        return model;
    }
    
    @PostMapping(path = "buy")
    public String buyPost(@ModelAttribute(name = "korpa") @Validated Korpa korpa, BindingResult result, HttpSession session, Model model){        
        if (result.hasErrors()) {
            //model.addAttribute("karta", karta);
            return "karta/buy";
        } else {
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            Korisnik korisnik = korisnikService.findByName(username);
            BigDecimal ukupanIznos = new BigDecimal(0);
            for(Karta k : korpa.getKarte()){
                k.setProjekcija(korpa.getProjekcija());
                k.setStatus("prodata online");
                k.setCena(korpa.getProjekcija().getCenaKarte());
                k.setKupac(korisnik);
                ukupanIznos = ukupanIznos.add(k.getCena());
            }
            try {
                session.setAttribute("korpa", korpa);               
                Payment payment = payPalService.createPayment(ukupanIznos, "EUR", "paypal", "sale", "Prodaja karata", "http://localhost:8080/Bioskop/karta/cancel", "http://localhost:8080/Bioskop/karta/success");
                for(Links link:payment.getLinks()) {
                    if(link.getRel().equals("approval_url")) {
                        return "redirect:"+link.getHref();
                    }
                }
			
            } catch (PayPalRESTException e) {
                System.out.println(e.getMessage());
            }         
            
            return "redirect:/karta/"+korpa.getProjekcija().getId()+"/buy";
        }              
    }

    
    
    @PostMapping(path = "save")
    public String save(@ModelAttribute(name = "korpa") @Validated Korpa korpa, BindingResult result, RedirectAttributes redirectAttributes, Model model, Locale locale){         
        if (result.hasErrors()) {
            //model.addAttribute("karta", karta);
            return "karta/sell";
        } else {
            for(Karta k : korpa.getKarte()){
                k.setProjekcija(korpa.getProjekcija());
                k.setStatus("prodata");
                k.setCena(korpa.getProjekcija().getCenaKarte());
                k.setKupac(null);
                k.setCode(0L);
                kartaService.save(k);
            }
            redirectAttributes.addFlashAttribute("message", messageSource.getMessage("karta.save" ,null, locale));
            return "redirect:/karta/"+korpa.getProjekcija().getId()+"/sell";
        }              
    }
    
    /*@GetMapping(path = "/{numberId}/delete")
    public ModelAndView delete(@PathVariable(name = "numberId") int numberId) {
        //ModelAndView model = new ModelAndView("redirect:/karta/"+numberId+"/sell");
        ModelAndView model = new ModelAndView("redirect:/home");
        try {
            kartaService.deleteById(numberId);
            System.out.println("===================== delete karta success");
        } catch (Exception e) {
            
            //model.addObject("message", "greska");
            System.out.println("===================== delete karta error");
        }

        return model;
    }*/
    
    @GetMapping(value = "success")
    public String successPay(HttpSession session, @RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId, RedirectAttributes redirectAttributes, Locale locale) {             
        try {           
            Korpa korpa =  (Korpa) session.getAttribute("korpa");
            Payment payment = payPalService.executePayment(paymentId, payerId);
            //System.out.println(payment.toJSON());
            if (payment.getState().equals("approved")) {
                Random rand = new Random();
                for(Karta k : korpa.getKarte()){
                    k.setCode(Math.abs(rand.nextLong()));
                    kartaService.save(k);
                }
                sendEmail(korpa);
                redirectAttributes.addFlashAttribute("message", messageSource.getMessage("karta.kupljena" ,null, locale));
                return "redirect:/karta/"+korpa.getProjekcija().getId()+"/buy";
            }
        } catch (PayPalRESTException e) {
            System.out.println(e.getMessage());
        }
        return "karta/success";
    }

    @GetMapping(value = "cancel")
    public String cancelPay(HttpSession session, RedirectAttributes redirectAttributes, Locale locale) {
        Korpa korpa =  (Korpa) session.getAttribute("korpa");
        redirectAttributes.addFlashAttribute("error", messageSource.getMessage("kupovina.error" ,null, locale));
        return "redirect:/karta/"+korpa.getProjekcija().getId()+"/buy";
    }
    
     private void sendEmail(Korpa korpa){
        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(korpa.getKarte().get(0).getKupac().getEmail());
        email.setSubject("Vaše karte");
        String text = "Hvala na kupovini karti u našem bioskopu.\n\nProjekcija: "+korpa.getProjekcija().toString()+"\nSpisak karata:";
        for(Karta k : korpa.getKarte()){
            text += "\nBroj reda: "+k.getBrojReda()+", broj sedišta: "+k.getBrojSedista();
        }
        text += "\nVerifikacioni kod: "+korpa.getKarte().get(0).getCode()+"\n\nOdštampajte ovu poruku i donesite je u bioskip radi validacije.";
        email.setText(text);          
        email.setFrom("brkicmilos97@gmail.com");
        sender.send(email);
    }
    
    
    /*@ModelAttribute(name = "karta")
    private Karta getKarta(){
        return new Karta();
    }*/
    
    /*@ModelAttribute(name = "korpa")
    private Korpa getKorpa(){
        return new Korpa();
    }*/
    
    /*@ModelAttribute(name = "karte")
    private List<Karta> getKarte(){
        return new LinkedList<>();
    }*/
    
       
}
