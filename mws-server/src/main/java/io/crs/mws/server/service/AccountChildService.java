package io.crs.mws.server.service;

import io.crs.mws.server.entity.Account;
import io.crs.mws.server.entity.BaseEntity;

public interface AccountChildService<T extends BaseEntity> extends CrudService<T> {
	Account getCurrentAccount();
}
