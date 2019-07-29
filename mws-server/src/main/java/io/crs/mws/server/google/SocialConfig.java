/**
 * 
 */
package io.crs.mws.server.google;

import javax.inject.Inject;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.social.connect.ConnectionFactory;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.NotConnectedException;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.support.ConnectionFactoryRegistry;
import org.springframework.social.connect.web.ProviderSignInController;
import org.springframework.social.google.api.Google;
import org.springframework.social.google.connect.GoogleConnectionFactory;

import io.crs.mws.server.service.AccountService;

/**
 * @author robi
 *
 */
@Configuration
public class SocialConfig {

	@Inject
	private Environment environment;

	@Inject
	private AccountService accountService;

	/**
	 * When a new provider is added to the app, register its
	 * {@link ConnectionFactory} here.
	 * 
	 * @see GoogleConnectionFactory
	 */
	@Bean
	public ConnectionFactoryLocator connectionFactoryLocator() {
		ConnectionFactoryRegistry registry = new ConnectionFactoryRegistry();
		registry.addConnectionFactory(
				new GoogleConnectionFactory("459508062149-raha7ifeis656d0diorf7p18ik77gkem.apps.googleusercontent.com",
						"H9T_-GCSJAUeXCMFUK5zyqXm"));
		return registry;
	}

	/**
	 * Singleton data access object providing access to connections across all
	 * users.
	 */
	@Bean
	public UsersConnectionRepository usersConnectionRepository() {
		AppUsersConnectionRepository repository = new AppUsersConnectionRepository(connectionFactoryLocator(),
				accountService, Encryptors.noOpText());
		repository.setConnectionSignUp(new SimpleConnectionSignUp());
		return repository;
	}

	/**
	 * Request-scoped data access object providing access to the current user's
	 * connections.
	 */
	@Bean
	@Scope(value = "request", proxyMode = ScopedProxyMode.INTERFACES)
	public ConnectionRepository connectionRepository() {
		User user = SecurityContext.getCurrentUser();
		return usersConnectionRepository().createConnectionRepository(user.getId());
	}

	/**
	 * A proxy to a request-scoped object representing the current user's primary
	 * Google account.
	 * 
	 * @throws NotConnectedException if the user is not connected to Google.
	 */
	@Bean
	@Scope(value = "request", proxyMode = ScopedProxyMode.INTERFACES)
	public Google google() {
		return connectionRepository().getPrimaryConnection(Google.class).getApi();
	}

	/**
	 * The Spring MVC Controller that allows users to sign-in with their provider
	 * accounts.
	 */
	@Bean
	public ProviderSignInController providerSignInController() {
		return new ProviderSignInController(connectionFactoryLocator(), usersConnectionRepository(),
				new SimpleSignInAdapter());
	}

}
