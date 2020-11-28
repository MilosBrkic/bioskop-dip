/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.milosbrkic.bioskop.formatter;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.TimeZone;
import org.springframework.format.Formatter;

/**
 *
 * @author milos
 */
public class DatumFormater implements Formatter<Date>{

    @Override
    public String print(Date t, Locale locale) {
        return t.toString();
    }

    @Override
    public Date parse(String string, Locale locale) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        format.setTimeZone(TimeZone.getTimeZone("GMT"));
        java.util.Date datumPom = format.parse(string);
        return new java.sql.Date(datumPom.getTime());
    }
    
}
