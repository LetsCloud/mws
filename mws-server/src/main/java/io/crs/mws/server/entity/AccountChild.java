/**
 * 
 */
package io.crs.mws.server.entity;

import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Parent;

/**
 * Az Account entitás gyermekeinek őse.
 * <p>
 * Az ős Account-ra való hivatkozással egészül ki.
 * 
 * @author robi
 *
 */
public class AccountChild extends BaseEntity {

	@Parent
	private Ref<Account> account;

	/**
	 * Objectify miatt van rá szükség.
	 */
	public AccountChild() {
		super();
//		logger.info("AccountChild()");
	}

	public AccountChild(AccountChild source) {
		super(source);
		account = source.account;
	}

	public Account getAccount() {
		if (account == null)
			return null;
		return account.get();
	}

	public void setAccount(Account account) {
//		logger.info("setAccount()->" + account);
		if (account.getId() != null)
			this.account = Ref.create(account);
	}

	@Override
	public String toString() {
		return "AccountChild [account=" + account + "]" + super.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((account == null) ? 0 : account.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		AccountChild other = (AccountChild) obj;
		if (account == null) {
			if (other.account != null)
				return false;
		} else if (!account.equals(other.account))
			return false;
		return true;
	}

	/**
	 * 
	 * @author CR
	 *
	 * @param <T>
	 */
	public static class Builder<T extends Builder<?>> {

		private Account account;

		public Builder() {
		}

		@SuppressWarnings("unchecked")
		public T account(Account account) {
			this.account = account;
			return (T) this;
		}

		public AccountChild build() {
			return new AccountChild(this);
		}
	}

	protected AccountChild(Builder<?> builder) {
		this.setAccount(builder.account);
	}
}
