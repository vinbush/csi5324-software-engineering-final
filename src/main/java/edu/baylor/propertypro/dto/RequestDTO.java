package edu.baylor.propertypro.dto;

import java.io.Serializable;
import java.time.Instant;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RequestDTO implements Serializable {
	private static final long serialVersionUID = -2027048201544416504L;
	private long id;
	private Instant createdAt;
	private String textBody;
	private boolean hasResponse;
	private long listingId;
	private String buyerName;
	private String buyerUsername;
	private String listingRealtorUsername;
	private String realtorName;
	private String listingTitle;
	private ResponseDTO response;
}

