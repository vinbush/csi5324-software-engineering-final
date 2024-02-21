package edu.baylor.propertypro.dto;

import lombok.Data;

@Data
public class FavoriteDTO {
	private long id;
	private long listingId;
	private String listingTitle;
	private String note;
}
