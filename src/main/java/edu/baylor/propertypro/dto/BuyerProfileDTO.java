package edu.baylor.propertypro.dto;

import java.util.List;

import lombok.Data;

@Data
public class BuyerProfileDTO extends BaseProfileDTO {
	private List<FavoriteDTO> favorites;
}
