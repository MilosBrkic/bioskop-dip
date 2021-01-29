/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.milosbrkic.bioskop.service;

import com.milosbrkic.bioskop.domen.Korisnik;
import com.milosbrkic.bioskop.repository.KorisnikRepository;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author milos
 */
@Service
public class KorisnikService extends AbstractService<Korisnik, KorisnikRepository> implements UserDetailsService{

    public KorisnikService(KorisnikRepository repository) {
        super(repository);
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       
        Korisnik k = repository.findByName(username);     
        Set < GrantedAuthority > grantedAuthorities = new HashSet < > ();
        
        switch(k.getStatus()){
            case "admin":
                grantedAuthorities.add(new SimpleGrantedAuthority("ZAPOSLENI"));
                grantedAuthorities.add(new SimpleGrantedAuthority("ADMIN"));              
                break;
            case "zaposleni":
                grantedAuthorities.add(new SimpleGrantedAuthority("ZAPOSLENI"));
                break;
            case "kupac":
                grantedAuthorities.add(new SimpleGrantedAuthority("KUPAC"));                
        }
        grantedAuthorities.add(new SimpleGrantedAuthority("USER"));//svaki ulogovani korisnik ima ovau dozvolu
               
        boolean enabled = !k.getStatus().equals("neaktivan");                    
       
        return new org.springframework.security.core.userdetails.User(k.getUsername(), k.getPassword(), enabled, true, true, true, grantedAuthorities);
    }
    
    public Korisnik findByName(String name){
        return repository.findByName(name);
    }
    
    @Override
    public Korisnik findById(int id) {
        return repository.findById(id);
    }

    @Override
    public List<Korisnik> getAll() {
        return repository.getAll();
    }

    @Override
    public void save(Korisnik k) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if(k.getId() == 0)//enkriptuje samo prvi put
            k.setPassword(encoder.encode(k.getPassword())); 
        
        repository.save(k);
    }

    @Override
    public void deleteById(int id) throws Exception {
        repository.deleteById(id);
    }

    @Override
    public List<Korisnik> findByQuery(String query) {
        return repository.findByQuery(query);
    }
    
    @Scheduled(fixedDelay = 300000)//5 minuta
    public void deleteExpired(){
        System.out.println("==========delete expired");
        repository.deleteExpired();
    }
    
}
