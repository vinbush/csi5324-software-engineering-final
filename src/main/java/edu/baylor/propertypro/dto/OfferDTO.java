package edu.baylor.propertypro.dto;

import java.io.Serializable;
import java.time.Instant;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OfferDTO extends RequestDTO {
	private static final long serialVersionUID = -6401101188917433758L;
	private int offerPrice;
}
