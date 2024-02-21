package edu.baylor.propertypro.dto;

import lombok.Data;

@Data
public class ResponseCreationDTO {
	private String textBody;
	private long originalRequestId;
	private Boolean isOffer;
}
