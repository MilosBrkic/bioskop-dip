/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.milosbrkic.bioskop.validator;

import com.milosbrkic.bioskop.domen.Projekcija;
import java.sql.Date;
import java.util.Calendar;

/**
 *
 * @author milos
 */
public class Common {
    
    public static boolean isInPast(Projekcija projekcija){
        Date danas = new Date(new java.util.Date().getTime());//danas
              
        Calendar dateCal = Calendar.getInstance();
        dateCal.setTime(projekcija.getDatum());
        Calendar timeCal = Calendar.getInstance();
        timeCal.setTime(projekcija.getVreme());

        dateCal.set(Calendar.HOUR_OF_DAY, timeCal.get(Calendar.HOUR_OF_DAY));
        dateCal.set(Calendar.MINUTE, timeCal.get(Calendar.MINUTE));
 
        Date date = new Date(dateCal.getTime().getTime());//datum i vreme projekcije
 
        return danas.after(date);           
    }
    
}
