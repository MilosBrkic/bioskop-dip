package com.milosbrkic.bioskop.config;

import com.milosbrkic.bioskop.formatter.DatumFormater;
import com.milosbrkic.bioskop.formatter.DistributerFormater;
import com.milosbrkic.bioskop.formatter.FilmFormater;
import com.milosbrkic.bioskop.formatter.OsobaFormater;
import com.milosbrkic.bioskop.formatter.ProjekcijaFormater;
import com.milosbrkic.bioskop.formatter.SalaFormater;
import com.milosbrkic.bioskop.formatter.VremeFormater;
import com.milosbrkic.bioskop.formatter.ZanrFormater;
import com.milosbrkic.bioskop.formatter.KorisnikFormater;
import com.milosbrkic.bioskop.repository.DistributerRepository;
import com.milosbrkic.bioskop.repository.FilmRepository;
import com.milosbrkic.bioskop.repository.OsobaRepository;
import com.milosbrkic.bioskop.repository.ProjekcijaRepository;
import com.milosbrkic.bioskop.repository.SalaRepository;
import com.milosbrkic.bioskop.repository.ZanrRepository;
import com.milosbrkic.bioskop.repository.KorisnikRepository;
import java.util.Properties;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.format.FormatterRegistry;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.view.tiles3.TilesConfigurer;
import org.springframework.web.servlet.view.tiles3.TilesViewResolver;


@Configuration
@Import({DatabaseConfiguration.class, SecurityConfig.class})
@EnableTransactionManagement
@EnableWebMvc
@ComponentScan(basePackages = {
    "com.milosbrkic.bioskop.controller",
    "com.milosbrkic.bioskop.repository",
    "com.milosbrkic.bioskop.service",
    "com.milosbrkic.bioskop.validator",
    "com.milosbrkic.bioskop.formatter"
}
)
//za definisanje HanderMappera, kontrolera, viewResolver-a
public class WebContextConfig implements WebMvcConfigurer {

    private final DistributerRepository distributerRepository;
    private final ZanrRepository zanrRepository;
    private final FilmRepository filmRepository;
    private final SalaRepository salaRepository;
    private final ProjekcijaRepository projekcijaRepository;
    private final KorisnikRepository zaposleniRepository;
    private final OsobaRepository osobaRepository;
    
    //@Value("${email.username}")
    private final String username = System.getenv("email.username");
    //@Value("${email.password}")
    private final String password = System.getenv("email.password");
    
    @Autowired
    Environment env;

    @Autowired
    public WebContextConfig(DistributerRepository distributerRepository, ZanrRepository zanrRepository, FilmRepository filmRepository, SalaRepository salaRepository, ProjekcijaRepository projekcijaRepository, KorisnikRepository zaposleniRepository, OsobaRepository osobaRepository) {
        this.distributerRepository = distributerRepository;
        this.zanrRepository = zanrRepository;
        this.filmRepository = filmRepository;
        this.salaRepository = salaRepository;
        this.projekcijaRepository = projekcijaRepository;
        this.zaposleniRepository = zaposleniRepository;
        this.osobaRepository = osobaRepository;
    }

    
    @Bean
    public ViewResolver tilesViewResolver() {
        TilesViewResolver tilesViewResolver = new TilesViewResolver();
        tilesViewResolver.setOrder(0);
        return tilesViewResolver;
    }

    @Bean
    public TilesConfigurer tilesConfigurer() {
        TilesConfigurer tilesConfigurer = new TilesConfigurer();
        tilesConfigurer.setDefinitions(new String[]{"/WEB-INF/pages/tiles/tiles.xml"});
        return tilesConfigurer;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/webjars/**").addResourceLocations("/webjars/");
        registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
    }
    
    @Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasenames("validation/validation","messages/messages");
        messageSource.setFallbackToSystemLocale(false);
        return messageSource;
    }
    
    @Bean
    public static PropertySourcesPlaceholderConfigurer properties() {
        PropertySourcesPlaceholderConfigurer props = new PropertySourcesPlaceholderConfigurer();
        props.setLocations(new Resource[] {
                new ClassPathResource("application.properties")
        });
        return props;
    }
    
    @Bean
    public LocaleResolver localeResolver() {
        return new CookieLocaleResolver();
        //return new SessionLocaleResolver();
    }
    
    @Bean
    public JavaMailSenderImpl mailSender() {
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();

        javaMailSender.setHost("smtp.gmail.com");
        javaMailSender.setPort(587);
             
        StandardPBEStringEncryptor decryptor = new StandardPBEStringEncryptor();
        decryptor.setPassword("bioskop");
        System.out.println("email username = "+username);
        javaMailSender.setUsername(username);
	javaMailSender.setPassword(decryptor.decrypt(password));
                       
        Properties props = javaMailSender.getJavaMailProperties();
	    props.put("mail.transport.protocol", "smtp");
	    props.put("mail.smtp.auth", "true");
	    props.put("mail.smtp.starttls.enable", "true");
	    props.put("mail.debug", "true");

        return javaMailSender;
    }
    
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
        localeChangeInterceptor.setParamName("lang");
        registry.addInterceptor(localeChangeInterceptor);
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addFormatter(new DistributerFormater(distributerRepository));
        registry.addFormatter(new ZanrFormater(zanrRepository));
        registry.addFormatter(new FilmFormater(filmRepository));
        registry.addFormatter(new SalaFormater(salaRepository));
        registry.addFormatter(new ProjekcijaFormater(projekcijaRepository));
        registry.addFormatter(new KorisnikFormater(zaposleniRepository));
        registry.addFormatter(new OsobaFormater(osobaRepository));
        registry.addFormatter(new DatumFormater());
        registry.addFormatter(new VremeFormater());
    }
    
    
}
