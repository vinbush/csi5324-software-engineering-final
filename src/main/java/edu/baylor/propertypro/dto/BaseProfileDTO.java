package edu.baylor.propertypro.dto;

import java.util.List;

import lombok.Data;

@Data
public abstract class BaseProfileDTO {
	private List<RequestDTO> requests;
	private List<OfferDTO> offers;
}
