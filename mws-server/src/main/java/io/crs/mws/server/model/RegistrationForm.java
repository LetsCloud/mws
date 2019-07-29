/**
 * 
 */
package io.crs.mws.server.model;

import io.crs.mws.shared.cnst.SocialProvider;

/**
 * @author robi
 *
 */
public class RegistrationForm {

	private String nickname;

	private String username;

	private String email;

	private String password;

	private String confPassw;

	private SocialProvider socialProvider;

	public RegistrationForm() {
	}

	public RegistrationForm(String nickname, String username, String email, String password, String confPassw,
			SocialProvider socialProvider) {
		this();
		this.nickname = nickname;
		this.username = username;
		this.email = email;
		this.password = password;
		this.confPassw = confPassw;
		this.socialProvider = socialProvider;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfPassw() {
		return confPassw;
	}

	public void setConfPassw(String confPassw) {
		this.confPassw = confPassw;
	}

	public SocialProvider getSocialProvider() {
		return socialProvider;
	}

	public void setSocialProvider(SocialProvider socialProvider) {
		this.socialProvider = socialProvider;
	}

	public static class Builder {
		private String nickname;
		private String username;
		private String email;
		private String password;
		private String confPassw;
		private SocialProvider socialProvider;

		public Builder addNickname(final String nickname) {
			this.nickname = nickname;
			return this;
		}

		public Builder addUsername(final String username) {
			this.username = username;
			return this;
		}

		public Builder addEmail(final String email) {
			this.email = email;
			return this;
		}

		public Builder addPassword(final String password) {
			this.password = password;
			return this;
		}

		public Builder addConfPassw(final String confPassw) {
			this.confPassw = confPassw;
			return this;
		}

		public Builder addSocialProvider(final SocialProvider socialProvider) {
			this.socialProvider = socialProvider;
			return this;
		}

		public RegistrationForm build() {
			return new RegistrationForm(nickname, username, email, password, confPassw, socialProvider);
		}
	}

	public static Builder getBuilder() {
		return new Builder();
	}

}
