package br.com.empresa.sistemas.springdb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@SpringBootApplication
public class SpringDbApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringDbApplication.class, args);
	}
	
   @Bean
   public FilterRegistrationBean corsFilter() {
       UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
       CorsConfiguration config = new CorsConfiguration().applyPermitDefaultValues();
       config.addAllowedMethod(HttpMethod.PUT);
       config.addAllowedMethod(HttpMethod.DELETE);
       source.registerCorsConfiguration("/**", config);
       FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));
       bean.setOrder(0);
       return bean;
   }

}
