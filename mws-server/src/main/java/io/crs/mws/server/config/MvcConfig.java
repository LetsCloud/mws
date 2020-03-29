/**
 * 
 */
package io.crs.mws.server.config;

import java.util.Arrays;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
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

/**
 * @author robi
 *
 */
@EnableWebMvc
@Configuration
@ComponentScan({ "io.crs.mws.server.repository", "io.crs.mws.server.service", "io.crs.mws.server.controller",
		"io.crs.mws.server.security", "io.crs.mws.server.login" })
@PropertySource("classpath:application.properties")
public class MvcConfig implements WebMvcConfigurer {
	private static final Logger logger = LoggerFactory.getLogger(MvcConfig.class);

	@Autowired
	Environment env;

	private final long MAX_AGE_SECS = 3600;

	/**
	 * Let’s enable CORS so that our frontend client can access the APIs from a
	 * different origin. I’ve enabled all origins in the following configuration.
	 * But you should make it more strict in a production application
	 */
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
		logger.info("appProperties()");
		AppProperties appProperties = new AppProperties();
		String tokenSecret = env.getProperty("app.auth.tokenSecret");
		logger.info("appProperties() -> tokenSecret = " + tokenSecret);
		appProperties.getAuth().setTokenSecret(tokenSecret);
//		appProperties.getAuth().setTokenExpirationMsec(new Long(env.getProperty("app.auth.tokenExpirationMsec")));
		appProperties.getAuth().setTokenExpirationMsec(864000000);
		List<String> authorizedRedirectUris = Arrays
				.asList(env.getProperty("app.oauth2.authorizedRedirectUris").split(","));

		if (authorizedRedirectUris == null || authorizedRedirectUris.isEmpty()) {
			logger.info("appProperties() -> (authorizedRedirectUris != null || !authorizedRedirectUris.isEmpty())");
		} else {
			for (String uri : authorizedRedirectUris) {
				logger.info("appProperties() -> authorizedRedirectUri = " + uri);
			}
			appProperties.getOauth2().setAuthorizedRedirectUris(authorizedRedirectUris);
		}
		return appProperties;
	}

}