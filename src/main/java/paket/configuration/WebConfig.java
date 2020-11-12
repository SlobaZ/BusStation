package paket.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.extras.springsecurity5.dialect.SpringSecurityDialect;

@Configuration
public class WebConfig implements WebMvcConfigurer {   
	
	@Autowired
    private MessageSource messageSource;
	
	 @Bean
	    public BCryptPasswordEncoder passwordEncoder() {
	        return new BCryptPasswordEncoder();
	    }

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		
		registry.addViewController("/").setViewName("index");
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/loggedIn").setViewName("index");
        registry.addViewController("/registration").setViewName("registration");
        registry.addViewController("/carriers").setViewName("carrier");
        registry.addViewController("/lines").setViewName("line");
        registry.addViewController("/admin/addcarrier").setViewName("admin/carrier-add");
        registry.addViewController("/admin/editcarrier/{id}").setViewName("admin/carrier-edit");
        registry.addViewController("/admin/addline").setViewName("admin/line-add");
        registry.addViewController("/admin/editline/{id}").setViewName("admin/line-edit");
        registry.addViewController("/admin/users").setViewName("admin/user");
        registry.addViewController("/admin/reservations").setViewName("admin/reservation-all");
        registry.addViewController("/user/reserve/{id}").setViewName("user/result");
        
	}
	
    @Override
    public Validator getValidator() {
        LocalValidatorFactoryBean factory = new LocalValidatorFactoryBean();
        factory.setValidationMessageSource(messageSource);
        return factory;
    }

    @Bean
	public SpringSecurityDialect securityDialect() {
	    return new SpringSecurityDialect();
	}

}
