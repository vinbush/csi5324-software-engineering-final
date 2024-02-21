package edu.baylor.propertypro.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ListingDTO {
	private long id;
	private String title;
	private String description;
	private String listingType;
	private int listingPrice;
	private int propertySize;
	private int houseSize;
	private int numBed;
	private int numBath;
	private boolean basement;
	private int numFloors;
	private double latitude;
	private double longitude;
	private String street;
	private String unitNum;
	private String city;
	private String state;
	private String zipCode;
	private long realtorId;
	private String realtorName;
	private String realtorAgency;
	private boolean isUserFavorite = false;
}
