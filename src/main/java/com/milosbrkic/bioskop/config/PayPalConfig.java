/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.milosbrkic.bioskop.config;

import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.OAuthTokenCredential;
import com.paypal.base.rest.PayPalRESTException;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author milos
 */
@Configuration
public class PayPalConfig {
       
    //@Value("${paypal.client.id}")
    private final String clientID = System.getenv("paypal.client.id");
    //@Value("${paypal.client.secret}")
    private final String clientSecret = System.getenv("paypal.client.secret");
    
    private final String mode = "sandbox";
    
    @Bean
    public Map<String, String> paypalSdkConfig() {
            Map<String, String> configMap = new HashMap<>();
            configMap.put("mode", mode);
            return configMap;
    }

    @Bean
    public OAuthTokenCredential oAuthTokenCredential() {
        System.out.println("cliend id = "+clientID);
        return new OAuthTokenCredential(clientID, clientSecret, paypalSdkConfig());
    }

    @Bean
    public APIContext apiContext() throws PayPalRESTException {
 
        APIContext context = new APIContext(oAuthTokenCredential().getAccessToken());
        context.setConfigurationMap(paypalSdkConfig());
        return context;
    }
    
}
