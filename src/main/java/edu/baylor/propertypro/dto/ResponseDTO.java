package edu.baylor.propertypro.dto;

import java.io.Serializable;
import java.time.Instant;

import lombok.Data;

@Data
public class ResponseDTO implements Serializable {
	private static final long serialVersionUID = -7770883512574402244L;
	private long id;
	private String textBody;
	private String realtorName;
	private long requestId;
	private Instant createdAt;
	private String requestListingRealtorUsername;
	private String requestBuyerUsername;
	private int requestOfferPrice;
}
