/**
 * 
 */
package io.crs.mws.server.config;

import java.util.Arrays;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.crs.mws.server.repository.ofy.ObjectifyRegistration;
import io.crs.mws.server.security2.AppProperties;

/**
 * @author robi
 *
 */
@EnableWebMvc
@Configuration
@Import({ SecurityConfigCali.class })
@ComponentScan({ "io.crs.mws.server.repository", "io.crs.mws.server.service", "io.crs.mws.server.controller" })
public class MvcConfig implements WebMvcConfigurer {

	@Autowired
	Environment env;

	private final long MAX_AGE_SECS = 3600;

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**").allowedOrigins("*")
				.allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS").allowedHeaders("*")
				.allowCredentials(true).maxAge(MAX_AGE_SECS);
	}

	// API
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/**").addResourceLocations("classpath:static/");
		registry.addResourceHandler("/adm/**").addResourceLocations("/WEB-INF/adm/");
	}

	@Override
	public void addInterceptors(final InterceptorRegistry registry) {
		/*
		 * registry.addInterceptor(new LoggerInterceptor()); registry.addInterceptor(new
		 * UserInterceptor()); registry.addInterceptor(new SessionTimerInterceptor());
		 */
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	@Bean
	public ObjectMapper objectMapper() {
		return new ObjectMapper();
	}

	@Bean
	public MessageSource messageSource() {
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();

		messageSource.setBasename("classpath:messages");
		messageSource.setDefaultEncoding("UTF-8");
		return messageSource;
	}

	@Bean
	public ObjectifyRegistration objectifyRegistration() {
		return new ObjectifyRegistration();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AppProperties appProperties() {
		AppProperties appProperties = new AppProperties();
		appProperties.getAuth().setTokenSecret(env.getProperty("app.auth.tokenSecret"));
		appProperties.getAuth().setTokenExpirationMsec(new Long(env.getProperty("app.auth.tokenExpirationMsec")));
		appProperties.getOauth2().setAuthorizedRedirectUris(
				Arrays.asList(env.getProperty("app.oauth2.authorizedRedirectUris").split(",")));
		return appProperties;
	}

}