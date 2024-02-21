package edu.baylor.propertypro.data;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import edu.baylor.propertypro.domain.Favorite;

@Repository
public interface FavoriteRepository extends PagingAndSortingRepository<Favorite, Long> {
	List<Favorite> findByBuyerId(long buyerId);
	Optional<Favorite> findByBuyerIdAndListingId(long buyerId, long listingId);
}
