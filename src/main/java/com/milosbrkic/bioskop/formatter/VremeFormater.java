/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.milosbrkic.bioskop.formatter;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import org.springframework.format.Formatter;

/**
 *
 * @author milos
 */
public class VremeFormater implements Formatter<Time>{

    @Override
    public String print(Time t, Locale locale) {
        return t.toString();
    }

    @Override
    public Time parse(String string, Locale locale) throws ParseException {
        return new Time(new SimpleDateFormat("HH:mm").parse(string).getTime());
    }
    
}
