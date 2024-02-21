package edu.baylor.propertypro.data;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

import edu.baylor.propertypro.domain.BaseRequest;

@NoRepositoryBean
public interface RequestBaseRepository<T extends BaseRequest> extends PagingAndSortingRepository<T, Long> {
	
	@Query("select r from #{#entityName} as r where r.buyer.id = ?1 and r.listing.realtor.id = ?2")
	Optional<T> findFirstByBuyerIdAndListingRealtorId(long buyerId, long listingRealtorId);
	
	@Query("select r from #{#entityName} as r where r.buyer.id = ?1 and r.listing.id = ?2")
	Optional<T> findFirstByBuyerIdAndListingId(long buyerId, Long listingId);
	
	@Query("select r from #{#entityName} as r where r.listing.realtor.id = ?1")
	List<T> findByListingRealtorId(long listingRealtorId);
	
	@Query("select r from #{#entityName} as r where r.buyer.id = ?1")
	List<T> findByBuyerId(long buyerId);
}
