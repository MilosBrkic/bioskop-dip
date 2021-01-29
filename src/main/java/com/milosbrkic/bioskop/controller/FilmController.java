/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.milosbrkic.bioskop.controller;

import com.milosbrkic.bioskop.domen.Distributer;
import com.milosbrkic.bioskop.domen.Film;
import com.milosbrkic.bioskop.domen.Osoba;
import com.milosbrkic.bioskop.domen.Projekcija;
import com.milosbrkic.bioskop.domen.Zanr;
import com.milosbrkic.bioskop.service.DistributerService;
import com.milosbrkic.bioskop.service.FilmService;
import com.milosbrkic.bioskop.service.OsobaService;
import com.milosbrkic.bioskop.service.ProjekcijaService;
import com.milosbrkic.bioskop.service.ZanrService;
import com.milosbrkic.bioskop.validator.FilmValidator;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Locale;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author milos
 */
@Controller
@RequestMapping(value = "/film")
public class FilmController {
    
    private final FilmService filmService;
    private final DistributerService distributerService;
    private final ZanrService zanrDepository;
    private final OsobaService osobaService;
    private final ProjekcijaService projekcijaService;
    
    private final FilmValidator filmValidator;
    private final MessageSource messageSource;    
    private final ServletContext context;

    @Autowired
    public FilmController(FilmService filmService, DistributerService distributerService, ZanrService zanrDepository, OsobaService osobaService, ProjekcijaService projekcijaService, FilmValidator filmValidator, MessageSource messageSource, ServletContext context) {
        this.filmService = filmService;
        this.distributerService = distributerService;
        this.zanrDepository = zanrDepository;
        this.osobaService = osobaService;
        this.projekcijaService = projekcijaService;
        this.filmValidator = filmValidator;
        this.messageSource = messageSource;
        this.context = context;
    }
      
    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.setValidator(filmValidator);
    }

    
          
    @GetMapping
    public ModelAndView home() {
        ModelAndView model = new ModelAndView("film/home");
        model.addObject("filmovi", filmService.getAll());
        return model;
    }
    
    @GetMapping(path = "search")
    public ModelAndView search(@ModelAttribute(name = "film") Film film, Locale locale) {       
        ModelAndView model = new ModelAndView("film/home");
        List<Film> filmovi = filmService.findByQuery("select f from Film f where naziv like '%"+film.getNaziv()+"%'");
        model.addObject("filmovi", filmovi);
        if(filmovi == null || filmovi.isEmpty())
            model.addObject("error", messageSource.getMessage("film.search.error" ,null, locale));
        return model;
    }
    
    
    @GetMapping(path = "add")
    public ModelAndView add() {
        ModelAndView model = new ModelAndView("film/add");
        /*model.addObject("osobe", osobaService.getAll());
        model.addObject("zanroviSvi", zanrDepository.getAll());
        model.addObject("distributeri", distributerService.getAll());*/
        return model;
    }    
    
    @PostMapping(path = "save")
    public String save(@ModelAttribute(name = "film") @Validated Film film, BindingResult result, RedirectAttributes redirectAttributes, Model model, Locale locale){  
        if (result.hasErrors()) {
            model.addAttribute("sala", film);
            return "film/add";
        } else {
            film.setReziser(null);
            filmService.save(film);
            String message = messageSource.getMessage("film.save" ,null, locale);
            redirectAttributes.addFlashAttribute("message", message);
            saveImage(film.getId(), film.getUrl());

            return "redirect:/film/add";            
        }
    }
    
    @GetMapping(path = "/{numberId}/edit")
    public ModelAndView edit(@PathVariable(name = "numberId") int numberId) {
        ModelAndView model = new ModelAndView("film/edit");        
        Film film = filmService.findById(numberId);
        /*model.addObject("osobe", osobaService.getAll());
        model.addObject("zanroviSvi", zanrDepository.getAll());
        model.addObject("distributeri", distributerService.getAll());*/
        model.addObject("film", film);
        return model;
    }
    
    @GetMapping(path = "/{numberId}/view")
    public ModelAndView view(@PathVariable(name = "numberId") int id) {
        ModelAndView model = new ModelAndView("film/view");      
        Film film = filmService.findById(id);
        List<Projekcija> projekcije = projekcijaService.findByQuery("SELECT p from Projekcija p where p.film = "+id +" AND p.datum >= NOW() order by p.datum desc, p.vreme desc");
        model.addObject("projekcije", projekcije);
        model.addObject("film", film);
        return model;
    }
    
    @GetMapping(value = "/{numberId}/delete")
    public String delete(@PathVariable(name = "numberId") int numberId, RedirectAttributes redirectAttributes, Locale locale) {
        try {
            filmService.deleteById(numberId);            
            String path = context.getRealPath("/WEB-INF/pages/film/images/"+numberId+".jpg");
            new File(path).delete();//brisanje slike  
            
            String message = messageSource.getMessage("film.delete" ,new Object[]{numberId}, locale);
            redirectAttributes.addFlashAttribute("message", message);
        } catch (Exception ex) {
            String error = messageSource.getMessage("film.delete.error" ,new Object[]{numberId}, locale);
            redirectAttributes.addFlashAttribute("error", error);
        } 
        return "redirect:/film";
        
    }
    
       
    @PostMapping(path = "/{numberId}/save")
    public String update(@ModelAttribute(name = "film") @Validated Film film, BindingResult result, RedirectAttributes redirectAttributes, Model model, @PathVariable(name = "numberId") int numberId, Locale locale){
        if (result.hasErrors()) {
            //redirectAttributes.addFlashAttribute("film", film);
            return "film/edit";
        } else {
            //System.out.println("ucitavanje = "+film.getDistributer().getFilmovi());
            filmService.save(film);
            redirectAttributes.addFlashAttribute("message", messageSource.getMessage("film.update" ,null, locale));
            saveImage(film.getId(), film.getUrl());
            
            return "redirect:/film/"+numberId+"/view";
        }
        
    }
    
    private void saveImage(int id, String urlString){       
        if(urlString != null && !urlString.trim().isEmpty())
        try {
            URL url = new URL(urlString);
            String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36";
            URLConnection con = url.openConnection();
            con.setRequestProperty("User-Agent", USER_AGENT);
            InputStream in = con.getInputStream();
            String path = context.getRealPath("/WEB-INF/pages/film/images/"+id+".jpg");
            new File(path).delete();//brisanje stare slike                
            Files.copy(in, Paths.get(path));
            in.close();
            
            File currentDirFile = new File(".");
            System.out.println("++++++++++++++++++++++++++ "+currentDirFile.getAbsolutePath());
            
            
            
            }
        catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    @RequestMapping(value = "/getImage/{id}")
    @ResponseBody
    public byte[] getImage(@PathVariable(name = "id") int id, HttpServletRequest request, Locale locale) {
        try {           
            String s = context.getRealPath("/WEB-INF/pages/film/images/"+id+".jpg");           
            Path path = Paths.get(s);           
            byte[] data = Files.readAllBytes(path);
            return data;                                     
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return null;       
    }
    
  
    
           
    @ModelAttribute(name = "film")
    private Film getFilm() {
        return new Film();
    }
    
    @ModelAttribute(name = "distributeri")
    private List<Distributer> getDistributeri() {
        List<Distributer> lista  =distributerService.getAll();
        //System.out.println("ucitavanje = "+lista.get(0).getFilmovi());
        return lista;
    }
    
    @ModelAttribute(name = "osobe")
    private List<Osoba> getOsobe() {
        return osobaService.getAll();
    }
     
    @ModelAttribute(name = "zanroviSvi")
    private List<Zanr> getZanrovi() {
        return zanrDepository.getAll();
    }
}
