package edu.baylor.propertypro.data;

import org.springframework.stereotype.Repository;

import edu.baylor.propertypro.domain.Offer;

@Repository
public interface OfferRepository extends RequestBaseRepository<Offer>{
//	Optional<Offer> findFirstByBuyerIdAndListingRealtorId(long buyerId, long listingRealtorId);
//	
//	List<Offer> findByListingRealtorId(long listingRealtorId);
//	List<Offer> findByBuyerId(long buyerId);
}
