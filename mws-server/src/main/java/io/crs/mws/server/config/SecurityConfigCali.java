/**
 * 
 */
package io.crs.mws.server.config;

import static io.crs.mws.shared.api.ApiPaths.APIv1.PUBLIC;
import static io.crs.mws.shared.api.ApiPaths.APIv1.ROOT;
import static io.crs.mws.shared.api.ApiPaths.APIv1.ADMIN;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.config.oauth2.client.CommonOAuth2Provider;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.InMemoryOAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.AuthenticatedPrincipalOAuth2AuthorizedClientRepository;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import io.crs.mws.server.security2.CustomUserDetailsService;
import io.crs.mws.server.security2.RestAuthenticationEntryPoint;
import io.crs.mws.server.security2.TokenAuthenticationFilter;
import io.crs.mws.server.security2.oauth2.CustomOAuth2UserService;
import io.crs.mws.server.security2.oauth2.HttpCookieOAuth2AuthorizationRequestRepository;
import io.crs.mws.server.security2.oauth2.OAuth2AuthenticationFailureHandler;
import io.crs.mws.server.security2.oauth2.OAuth2AuthenticationSuccessHandler;
import io.crs.mws.shared.cnst.Role;

/**
 * @author robi
 *
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, jsr250Enabled = true, prePostEnabled = true)
@PropertySource("classpath:application.properties")
public class SecurityConfigCali extends WebSecurityConfigurerAdapter {
	private static final Logger logger = LoggerFactory.getLogger(SecurityConfigCali.class);

	private Environment environment;

	public SecurityConfigCali(Environment environment) {
		this.environment = environment;
	}
	
	@Override
	public void configure(WebSecurity web) throws Exception {
	    web.ignoring().antMatchers("/_ah/**");
	}
	
	@Bean(BeanIds.AUTHENTICATION_MANAGER)
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
		authenticationManagerBuilder.userDetailsService(customUserDetailsService)
				.passwordEncoder(passwordEncoder());
	}

	@Bean
	public TokenAuthenticationFilter tokenAuthenticationFilter() {
		return new TokenAuthenticationFilter();
	}

	/*
	 * By default, Spring OAuth2 uses
	 * HttpSessionOAuth2AuthorizationRequestRepository to save the authorization
	 * request. But, since our service is stateless, we can't save it in the
	 * session. We'll save the request in a Base64 encoded cookie instead.
	 */
	@Bean
	public HttpCookieOAuth2AuthorizationRequestRepository cookieAuthorizationRequestRepository() {
		return new HttpCookieOAuth2AuthorizationRequestRepository();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Autowired
	private CustomUserDetailsService customUserDetailsService;

	@Autowired
	private CustomOAuth2UserService customOAuth2UserService;

	@Autowired
	private OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;

	@Autowired
	private OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler;

	@Autowired
	private HttpCookieOAuth2AuthorizationRequestRepository httpCookieOAuth2AuthorizationRequestRepository;

		@Override
		public void configure(HttpSecurity http) throws Exception {
			// @formatter:off
			http.cors()
					.and()
				.sessionManagement()
					.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
					.and()
				.csrf()
					.disable()
				.formLogin()
					.disable()
				.httpBasic()
					.disable()
				.exceptionHandling()
					.authenticationEntryPoint(new RestAuthenticationEntryPoint())
					.and()
				.authorizeRequests()
					.antMatchers("/app/**", "/adm/**", "/image/**", "/font/**", "/oauth2/**", "/login/**")
						.permitAll()
					.antMatchers(ROOT + ADMIN)
						.hasRole(Role.ROLE_ADMIN)
					.antMatchers(ROOT)
						.hasAnyRole(Role.ROLE_ADMIN, Role.ROLE_USER)
					.antMatchers(ROOT + PUBLIC + "/**")
						.permitAll()
					.anyRequest()
						.authenticated()
					.and()
				.oauth2Login()
					.authorizationEndpoint()
						.baseUri("/oauth2/authorize")
					.authorizationRequestRepository(cookieAuthorizationRequestRepository())
						.and()
//				.redirectionEndpoint()
//					.baseUri("/oauth2/callback/*")
//					.and()
					.userInfoEndpoint()
//					.oidcUserService(customOidcUserService)
					.userService(customOAuth2UserService)
						.and()
					.successHandler(oAuth2AuthenticationSuccessHandler)
					.failureHandler(oAuth2AuthenticationFailureHandler)
					.clientRegistrationRepository(clientRegistrationRepository())
					.authorizedClientService(authorizedClientService());
			// @formatter:on

			// Add our custom Token based authentication filter
			http.addFilterBefore(tokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
		}

	/**
	 * 
	 */

	private static List<String> clients = Arrays.asList("facebook", "google", "github");

	@Bean
	public ClientRegistrationRepository clientRegistrationRepository() {
		List<ClientRegistration> registrations = clients.stream().map(c -> getRegistration(c))
				.filter(registration -> registration != null).collect(Collectors.toList());

		return new InMemoryClientRegistrationRepository(registrations);
	}

	private static String CLIENT_PROPERTY_KEY = "spring.security.oauth2.client.registration.";

	private ClientRegistration getRegistration(String client) {
		logger.info("getRegistration()->environment=" + environment);

		String propKey = CLIENT_PROPERTY_KEY + client + ".clientId";
		logger.info("getRegistration()->propKey=" + propKey);
		String clientId = environment.getProperty(propKey);

		if (clientId == null) {
			return null;
		}

		String clientSecret = environment.getProperty(CLIENT_PROPERTY_KEY + client + ".clientSecret");

		String redirectUriTemplate = environment.getProperty(CLIENT_PROPERTY_KEY + client + ".redirectUriTemplate");
		logger.info("getRegistration()->redirectUriTemplate=" + redirectUriTemplate);

		String scope = environment.getProperty(CLIENT_PROPERTY_KEY + client + ".scope");

		if (client.equals("google")) {
			return CommonOAuth2Provider.GOOGLE.getBuilder(client).clientId(clientId).clientSecret(clientSecret)
					.scope(scope).build(); // .redirectUriTemplate(redirectUriTemplate)
		}
		if (client.equals("facebook")) {
			return CommonOAuth2Provider.FACEBOOK.getBuilder(client).clientId(clientId).clientSecret(clientSecret)
					.build();
		}
		return null;
	}

	@Bean
	public OAuth2AuthorizedClientService authorizedClientService() {
		return new InMemoryOAuth2AuthorizedClientService(clientRegistrationRepository());
	}

	@Bean
	public OAuth2AuthorizedClientRepository authorizedClientRepository(
			OAuth2AuthorizedClientService authorizedClientService) {
		return new AuthenticatedPrincipalOAuth2AuthorizedClientRepository(authorizedClientService);
	}

}