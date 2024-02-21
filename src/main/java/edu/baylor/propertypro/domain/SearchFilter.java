package edu.baylor.propertypro.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SearchFilter {
	List<ListingType> listingTypes = new ArrayList<>();
	Optional<Integer> minPrice = Optional.empty();
	Optional<Integer> maxPrice = Optional.empty();
	Optional<Integer> minPropertySize = Optional.empty();
	Optional<Integer> maxPropertySize = Optional.empty();
	Optional<Integer> minHouseSize = Optional.empty();
	Optional<Integer> maxHouseSize = Optional.empty();
	Optional<Integer> minNumBed = Optional.empty();
	Optional<Integer> maxNumBed = Optional.empty();
	Optional<Integer> minNumBath = Optional.empty();
	Optional<Integer> maxNumBath = Optional.empty();
	Optional<Integer> numFloors = Optional.empty();
	Optional<String> street = Optional.empty();
	Optional<String> unitNum = Optional.empty();
	Optional<String> city = Optional.empty();
	Optional<String> state = Optional.empty();
	Optional<String> zipCode = Optional.empty();
}
