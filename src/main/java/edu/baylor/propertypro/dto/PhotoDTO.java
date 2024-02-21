package edu.baylor.propertypro.dto;

import lombok.Data;

@Data
public class PhotoDTO {
	private String caption;
	private String picture; // base64 encoded
	private String contentType;
}
