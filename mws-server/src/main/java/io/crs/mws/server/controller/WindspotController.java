/**
 * 
 */
package io.crs.mws.server.controller;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.crs.mws.server.entity.Windspot;
import io.crs.mws.server.service.WindspotService;
import io.crs.mws.shared.dto.WindspotDto;
import io.crs.mws.shared.exception.RestApiException;

import static io.crs.mws.shared.api.ApiParameters.WEBSAFEKEY;
import static io.crs.mws.shared.api.ApiPaths.PATH_WEBSAFEKEY;
import static io.crs.mws.shared.api.ApiPaths.APIv1.WINDSPOT;
import static io.crs.mws.shared.api.ApiPaths.APIv1.ROOT;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * @author robi
 *
 */
@RestController
@RequestMapping(value = ROOT + WINDSPOT, produces = MediaType.APPLICATION_JSON_VALUE)
public class WindspotController extends CrudController<Windspot, WindspotDto> {

	@Autowired
	WindspotController(WindspotService service, ModelMapper modelMapper) {
		super(Windspot.class, service, modelMapper);
	}

	@Override
	protected WindspotDto createDto(Windspot entity) {
		return modelMapper.map(entity, WindspotDto.class);
	}

	@Override
	@RequestMapping(value = PATH_WEBSAFEKEY, method = GET)
	public ResponseEntity<WindspotDto> get(@PathVariable String webSafeKey) throws RestApiException {
		return super.get(webSafeKey);
	}

	@RequestMapping(method = GET)
	public ResponseEntity<List<WindspotDto>> getAll() {
		List<WindspotDto> result = new ArrayList<WindspotDto>();
		for (Windspot windspot : getService().getAll()) {
			result.add(getModelMapper().map(windspot, WindspotDto.class));
		}
		return new ResponseEntity<List<WindspotDto>>(result, OK);
	}

	@Override
	@RequestMapping(method = POST)
	public ResponseEntity<WindspotDto> saveOrCreate(@RequestBody WindspotDto dto) throws RestApiException {
		return super.saveOrCreate(dto);
	}

	@Override
	@RequestMapping(value = PATH_WEBSAFEKEY, method = DELETE)
	@ResponseStatus(HttpStatus.OK)
	public void delete(@PathVariable(WEBSAFEKEY) String webSafeKey) throws RestApiException {
		super.delete(webSafeKey);
	}
}