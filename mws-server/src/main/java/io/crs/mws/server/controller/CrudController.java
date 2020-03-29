/**
 * 
 */
package io.crs.mws.server.controller;

import static org.springframework.http.HttpStatus.OK;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import io.crs.mws.server.entity.Account;
import io.crs.mws.server.entity.BaseEntity;
import io.crs.mws.server.service.AccountService;
import io.crs.mws.server.service.CrudService;
import io.crs.mws.shared.dto.BaseDto;
import io.crs.mws.shared.exception.RestApiException;

/**
 * @author robi
 *
 */
public abstract class CrudController<T extends BaseEntity, D extends BaseDto> extends BaseController {
	private static final Logger logger = LoggerFactory.getLogger(CrudController.class);

	@Autowired
	protected AccountService accountService;

	protected final CrudService<T> service;

	protected final ModelMapper modelMapper;

	private final Class<T> clazz;

	public CrudController(Class<T> clazz, CrudService<T> service, ModelMapper modelMapper) {
		logger.info("CrudController()");
		this.service = service;
		this.modelMapper = modelMapper;
		this.clazz = clazz;
	}

	public CrudService<T> getService() {
		return service;
	}

	public ModelMapper getModelMapper() {
		return modelMapper;
	}

	abstract protected D createDto(T entity);

	public ResponseEntity<List<D>> getAll(String email) {
		List<D> dtos = new ArrayList<D>();

		Account currentAccount = getCurrentAccount(email);
		if (currentAccount == null)
			return new ResponseEntity<List<D>>(dtos, OK);

		String accountWebSafeKey = currentAccount.getWebSafeKey();
		if (accountWebSafeKey == null)
			return new ResponseEntity<List<D>>(dtos, OK);

		for (T entity : service.getChildren(accountWebSafeKey))
			dtos.add(createDto(entity));

		return new ResponseEntity<List<D>>(dtos, OK);
	}

	public List<T> getAll2(String email) {
		List<T> dtos = new ArrayList<T>();

		Account appUser = getCurrentAccount(email);
		if (appUser == null)
			return dtos;
		
		String accountWebSafeKey = appUser.getWebSafeKey();
		if (accountWebSafeKey == null)
			return dtos;

		for (T entity : service.getChildren(accountWebSafeKey))
			dtos.add(entity);

		return dtos;
	}

	public ResponseEntity<D> get(String webSafeKey) throws RestApiException {
		try {
			T entity = service.read(webSafeKey);
			D dto = createDto(entity);
			dto = afterGet(dto, entity);
			return new ResponseEntity<D>(dto, HttpStatus.OK);
		} catch (Throwable e) {
			throw new RestApiException(e);
		}
	}

	protected D afterGet(D dto, T entity) throws RestApiException {
		return dto;
	}

	public ResponseEntity<D> saveOrCreate(D dto) throws RestApiException {
		beforeSaveOrCreate(dto);
		try {
			T entity = modelMapper.map(dto, clazz);
			
			if (dto.getId() == null) {
				entity = service.create(entity);
			} else {
				entity = service.update(entity);
			}

			D dto2 = createDto(entity);

			afterSaveOrCreate(dto, entity);
			return new ResponseEntity<D>(dto2, HttpStatus.OK);
		} catch (Throwable e) {
			e.printStackTrace();
			throw new RestApiException(e);
		}
	}

	protected void beforeSaveOrCreate(D dto) throws RestApiException {
	}

	protected void afterSaveOrCreate(D dto, T saved) throws RestApiException {
	}

	public void delete(String webSafeKey) throws RestApiException {
		try {
			service.delete(webSafeKey);
		} catch (Throwable e) {
			throw new RestApiException(e);
		}
	}

	public ResponseEntity<List<D>> getChildren(String parentWebSafeKey) {
		List<D> dtos = new ArrayList<D>();
		if (parentWebSafeKey == null)
			return new ResponseEntity<List<D>>(dtos, HttpStatus.OK);

		for (T entity : service.getChildren(parentWebSafeKey))
			dtos.add(createDto(entity));

		return new ResponseEntity<List<D>>(dtos, HttpStatus.OK);
	}

	public ResponseEntity<List<D>> getChildrenByFilters(String parentWebSafeKey, Map<String, Object> filters) {
		List<D> dtos = new ArrayList<D>();
		if (parentWebSafeKey == null)
			return new ResponseEntity<List<D>>(dtos, HttpStatus.OK);

		for (T entity : service.getChildrenByFilters(parentWebSafeKey, filters))
			dtos.add(createDto(entity));

		return new ResponseEntity<List<D>>(dtos, HttpStatus.OK);
	}

	public Account getCurrentAccount(String email) {
		Account account = accountService.findByEmail(email);
		if (account == null)
			return null;
		return account;
	}
	
}