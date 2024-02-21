package edu.baylor.propertypro.data;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import edu.baylor.propertypro.domain.Listing;

@Repository
@Transactional
public interface ListingRepository extends PagingAndSortingRepository<Listing, Long> {

	Optional<Listing> findOptionalByStreetAndUnitNumAndCityAndStateAndZipCode(String street, String unitNum, String city, String state, String zipCode);
	
	//List<Listing> searchListings(Optional<String> street, Optional<String> unitNum, Optional<String> city, Optional<String> state);
	
	@Transactional
	@Query(value = "SELECT l FROM Listing l WHERE"
			+ " lower(l.street) = lower(:street)"
			+ " AND l.city = lower(:city)"
			+ " AND l.state = lower(:state)"
			+ " AND (:unitNum is null or :unitNum = '' or lower(l.unitNum) = lower(:unitNum))")
	List<Listing> findListingsByStreet(String street, Optional<String> unitNum, String city, String state);
	
	@Transactional
	@Query(value = "SELECT l FROM Listing l WHERE"
			+ " lower(l.city) = lower(:city)"
			+ " AND lower(l.state) = lower(:state)"
			+ " AND (:minPrice is null or l.listingPrice >= :minPrice)"
			+ " AND (:maxPrice is null or l.listingPrice <= :maxPrice)"
			+ " AND (:minPropertySize is null or l.propertySize >= :minPropertySize)"
			+ " AND (:maxPropertySize is null or l.propertySize <= :maxPropertySize)"
			+ " AND (:minHouseSize is null or l.houseSize >= :minHouseSize)"
			+ " AND (:maxHouseSize is null or l.houseSize <= :maxHouseSize)"
			+ " AND (:minNumBed is null or l.numBed >= :minNumBed)"
			+ " AND (:maxNumBed is null or l.numBed <= :maxNumBed)"
			+ " AND (:minNumBath is null or l.numBath >= :minNumBath)"
			+ " AND (:maxNumBath is null or l.numBath <= :maxNumBath)"
			+ " AND (:numFloors is null or l.numFloors >= :numFloors)")
	List<Listing> findListingsByCityState(String city, String state,
			Optional<Integer> minPrice, Optional<Integer> maxPrice,
			Optional<Integer> minPropertySize, Optional<Integer> maxPropertySize,
			Optional<Integer> minHouseSize, Optional<Integer> maxHouseSize,
			Optional<Integer> minNumBed, Optional<Integer> maxNumBed,
			Optional<Integer> minNumBath, Optional<Integer> maxNumBath,
			Optional<Integer> numFloors);

	@Transactional
	@Query(value = "SELECT l FROM Listing l WHERE"
			+ " l.zipCode = :zipCode"
			+ " AND (:minPrice is null or l.listingPrice >= :minPrice)"
			+ " AND (:maxPrice is null or l.listingPrice <= :maxPrice)"
			+ " AND (:minPropertySize is null or l.propertySize >= :minPropertySize)"
			+ " AND (:maxPropertySize is null or l.propertySize <= :maxPropertySize)"
			+ " AND (:minHouseSize is null or l.houseSize >= :minHouseSize)"
			+ " AND (:maxHouseSize is null or l.houseSize <= :maxHouseSize)"
			+ " AND (:minNumBed is null or l.numBed >= :minNumBed)"
			+ " AND (:maxNumBed is null or l.numBed <= :maxNumBed)"
			+ " AND (:minNumBath is null or l.numBath >= :minNumBath)"
			+ " AND (:maxNumBath is null or l.numBath <= :maxNumBath)"
			+ " AND (:numFloors is null or l.numFloors >= :numFloors)")
	List<Listing> findListingsByZipCode(String zipCode,
			Optional<Integer> minPrice, Optional<Integer> maxPrice,
			Optional<Integer> minPropertySize, Optional<Integer> maxPropertySize,
			Optional<Integer> minHouseSize, Optional<Integer> maxHouseSize,
			Optional<Integer> minNumBed, Optional<Integer> maxNumBed,
			Optional<Integer> minNumBath, Optional<Integer> maxNumBath,
			Optional<Integer> numFloors);

	@Transactional
	@Query(value = "SELECT l FROM Listing l WHERE"
			+ " l.state = :state"
			+ " AND (:minPrice is null or l.listingPrice >= :minPrice)"
			+ " AND (:maxPrice is null or l.listingPrice <= :maxPrice)"
			+ " AND (:minPropertySize is null or l.propertySize >= :minPropertySize)"
			+ " AND (:maxPropertySize is null or l.propertySize <= :maxPropertySize)"
			+ " AND (:minHouseSize is null or l.houseSize >= :minHouseSize)"
			+ " AND (:maxHouseSize is null or l.houseSize <= :maxHouseSize)"
			+ " AND (:minNumBed is null or l.numBed >= :minNumBed)"
			+ " AND (:maxNumBed is null or l.numBed <= :maxNumBed)"
			+ " AND (:minNumBath is null or l.numBath >= :minNumBath)"
			+ " AND (:maxNumBath is null or l.numBath <= :maxNumBath)"
			+ " AND (:numFloors is null or l.numFloors >= :numFloors)")
	List<Listing> findListingsByState(String state,
			Optional<Integer> minPrice, Optional<Integer> maxPrice,
			Optional<Integer> minPropertySize, Optional<Integer> maxPropertySize,
			Optional<Integer> minHouseSize, Optional<Integer> maxHouseSize,
			Optional<Integer> minNumBed, Optional<Integer> maxNumBed,
			Optional<Integer> minNumBath, Optional<Integer> maxNumBath,
			Optional<Integer> numFloors);
	
}
