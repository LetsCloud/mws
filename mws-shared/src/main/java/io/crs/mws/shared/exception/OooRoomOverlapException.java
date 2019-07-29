/**
 * 
 */
package io.crs.mws.shared.exception;

import java.util.Date;

import io.crs.mws.shared.dto.ErrorResponseDto;
import io.crs.mws.shared.exception.cnst.ErrorMessageCode;
import io.crs.mws.shared.exception.cnst.ErrorTitleCode;
import io.crs.mws.shared.exception.cnst.ValueCode;

/**
 * @author robi
 *
 */
@SuppressWarnings("serial")
public class OooRoomOverlapException extends BaseException {

	private String roomCode;
	private Date fromDate;
	private Date toDate;
	
	public OooRoomOverlapException(String roomCode, Date fromDate, Date toDate) {
		super();
		this.roomCode = roomCode;
		this.fromDate = fromDate;
		this.toDate = toDate;
	}

	@Override
	public ErrorResponseDto getErrorResponse() {
		ErrorResponseDto error = new ErrorResponseDto();
		error.setTitleCode(ErrorTitleCode.CRUD_CANNOT_BE_SAVED);
		error.setMessageCode(ErrorMessageCode.OOO_ROOM_OVERLAP);
		error.addValue(ValueCode.VC_ROOM, roomCode);
		error.addValue(ValueCode.VC_FROM_DATE, fromDate.getTime());
		error.addValue(ValueCode.VC_TO_DATE, toDate.getTime());
		return error;
	}
	
}
