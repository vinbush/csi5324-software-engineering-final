package edu.baylor.propertypro.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class ReviewCreationDTO {
	@Min(1)
	@Max(10)
	private int rating;
	
	@NotNull
	private String textBody;
	
	private long realtorId;
}
