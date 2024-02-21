package edu.baylor.propertypro.rest;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.baylor.propertypro.domain.Response;
import edu.baylor.propertypro.domain.User;
import edu.baylor.propertypro.dto.ResponseCreationDTO;
import edu.baylor.propertypro.dto.ResponseDTO;
import edu.baylor.propertypro.service.ResponseService;
import edu.baylor.propertypro.service.serviceexceptions.AlreadyRespondedException;
import edu.baylor.propertypro.service.serviceexceptions.EntityDoesNotExistException;
import edu.baylor.propertypro.service.serviceexceptions.UnauthorizedActionException;

@RestController
@RequestMapping("/api/responses")
public class ResponseController {
	@Autowired
	private ResponseService responseService;
	
	private ModelMapper modelMapper = new ModelMapper();
	
	@PostMapping
	public ResponseEntity<ResponseDTO> makeResponse(@RequestBody ResponseCreationDTO responseCreation, @AuthenticationPrincipal User user) throws EntityDoesNotExistException, UnauthorizedActionException, AlreadyRespondedException {
		Response response = new Response();
		response.setTextBody(responseCreation.getTextBody());
		Response createdResponse = responseService.makeResponse(response, responseCreation.getOriginalRequestId(), responseCreation.getIsOffer(), user);
		
		ResponseDTO returnResponse = modelMapper.map(createdResponse, ResponseDTO.class);
		
		return new ResponseEntity<ResponseDTO>(returnResponse, HttpStatus.CREATED);
	}
}
