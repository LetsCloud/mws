/**
 * 
 */
package io.crs.mws.shared.exception;

import io.crs.mws.shared.dto.ErrorResponseDto;
import io.crs.mws.shared.exception.cnst.ErrorTitleCode;

/**
 * @author robi
 *
 */
@SuppressWarnings("serial")
public class EntityValidationException extends BaseException {

	private ErrorTitleCode type;

	private String entity;

	private String property;

	public EntityValidationException(ErrorTitleCode type, String entity, String property) {
		super("Entities (" + entity + ") property (" + property + ") has no value!");
		this.type = type;
		this.entity = entity;
		this.property = property;
	}

	public ErrorTitleCode getType() {
		return type;
	}

	public String getEntity() {
		return entity;
	}

	public String getProperty() {
		return property;
	}

	@Override
	public ErrorResponseDto getErrorResponse() {
		ErrorResponseDto error = new ErrorResponseDto();
		error.setTitleCode(ErrorTitleCode.FAILD_ENTITIY_VALIDATION);
		error.setMessage(ErrorTitleCode.FAILD_ENTITIY_VALIDATION.toString() + "-" + getMessage());
		return error;
	}
}
