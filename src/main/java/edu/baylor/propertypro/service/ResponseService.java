package edu.baylor.propertypro.service;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import edu.baylor.propertypro.data.OfferRepository;
import edu.baylor.propertypro.data.RequestRepository;
import edu.baylor.propertypro.data.ResponseRepository;
import edu.baylor.propertypro.domain.BaseRequest;
import edu.baylor.propertypro.domain.Response;
import edu.baylor.propertypro.domain.User;
import edu.baylor.propertypro.dto.RequestDTO;
import edu.baylor.propertypro.dto.ResponseDTO;
import edu.baylor.propertypro.service.serviceexceptions.AlreadyRespondedException;
import edu.baylor.propertypro.service.serviceexceptions.EntityDoesNotExistException;
import edu.baylor.propertypro.service.serviceexceptions.UnauthorizedActionException;

@Service
public class ResponseService {
	@Autowired
	private ResponseRepository responseRepo;
	
	@Autowired
	private RequestRepository requestRepo;
	
	@Autowired
	private OfferRepository offerRepo;
	
	@Autowired
	private JmsTemplate jmsTemplate;
	
	@Autowired
	private ModelMapper modelMapper;
	
	public Response makeResponse(Response response, long requestId, boolean isOffer, User user) throws EntityDoesNotExistException, UnauthorizedActionException, AlreadyRespondedException {
		Optional<? extends BaseRequest> existingRequest;
		if (isOffer) {
			existingRequest = offerRepo.findById(requestId);
		} else {
			existingRequest = requestRepo.findById(requestId);
		}
		
		if (!existingRequest.isPresent()) {
			throw new EntityDoesNotExistException("You tried to respond to a nonexistent request!");
		}
		
		if (!(user.getId() == existingRequest.get().getListing().getRealtor().getId())) {
			throw new UnauthorizedActionException("You can't respond to that message!");
		}
		
		if (existingRequest.get().hasResponse()) {
			throw new AlreadyRespondedException("You've already responded to that message!");
		}
		
		response.setRequest(existingRequest.get());
		
		Response newResponse = responseRepo.save(response);
		
		ResponseDTO requestMessage = modelMapper.map(newResponse, ResponseDTO.class);
		
		jmsTemplate.convertAndSend("propertypro.response.topic", requestMessage);
		
		return newResponse;
	}
}
