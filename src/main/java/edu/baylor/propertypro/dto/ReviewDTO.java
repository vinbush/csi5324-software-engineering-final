package edu.baylor.propertypro.dto;

import lombok.Data;

@Data
public class ReviewDTO {
	private long id;
	private int rating;
	private String textBody;
	private String reviewerName;
}
