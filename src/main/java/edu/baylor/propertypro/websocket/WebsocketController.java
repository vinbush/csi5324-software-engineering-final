package edu.baylor.propertypro.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import edu.baylor.propertypro.dto.OfferDTO;
import edu.baylor.propertypro.dto.RequestDTO;
import edu.baylor.propertypro.dto.ResponseDTO;

@Controller
public class WebsocketController {
	
	@Autowired
	private SimpMessagingTemplate template;
	
	@JmsListener(destination = "propertypro.request.topic")
	public void sendRequest(RequestDTO request) {
		template.convertAndSendToUser(request.getListingRealtorUsername(), "/queue/realtorprofile", request);
		template.convertAndSendToUser(request.getBuyerUsername(), "/queue/buyerprofile", request);
	}
	
	@JmsListener(destination = "propertypro.offer.topic")
	public void sendOffer(OfferDTO offer) {
		template.convertAndSendToUser(offer.getListingRealtorUsername(), "/queue/realtorprofile", offer);
		template.convertAndSendToUser(offer.getBuyerUsername(), "/queue/buyerprofile", offer);
	}
	
	@JmsListener(destination = "propertypro.response.topic")
	public void sendOffer(ResponseDTO response) {
		template.convertAndSendToUser(response.getRequestListingRealtorUsername(), "/queue/realtorprofile", response);
		template.convertAndSendToUser(response.getRequestBuyerUsername(), "/queue/buyerprofile", response);
	}
}
