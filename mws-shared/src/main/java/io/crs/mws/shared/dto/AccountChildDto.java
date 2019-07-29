/**
 * 
 */
package io.crs.mws.shared.dto;

/**
 * @author robi
 *
 */
@SuppressWarnings("serial")
public class AccountChildDto extends BaseDto {

	private AccountDtor account;

	public AccountChildDto() {
		super();
	}

	protected AccountChildDto(Builder<?> builder) {
		super(builder);
		account = builder.account;
	}

	public AccountDtor getAccount() {
		return account;
	}

	public void setAccount(AccountDtor account) {
		this.account = account;
	}

	/*
	 * toString
	 */
	@Override
	public String toString() {
		String ret = "AccountChildDtor:{" + "account=" + account + ", " + super.toString() + "}";
		return ret;
	}

	/**
	 * 
	 * @author robi
	 *
	 * @param <T>
	 */
	public static abstract class Builder<T extends Builder<T>> extends BaseDto.Builder<T> {

		private AccountDtor account;

		public T account(AccountDtor account) {
			this.account = account;
			return self();
		}

		public AccountChildDto build() {
			return new AccountChildDto(this);
		}
	}

	/**
	 * 
	 * @author robi
	 *
	 */
	private static class Builder2 extends Builder<Builder2> {
		@Override
		protected Builder2 self() {
			return this;
		}
	}

	public static Builder<?> builder() {
		return new Builder2();
	}
}
