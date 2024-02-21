package edu.baylor.propertypro.data;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import edu.baylor.propertypro.domain.Review;

@Repository
public interface ReviewRepository extends PagingAndSortingRepository<Review, Long> {
	Optional<Review> findFirstByReviewerIdAndRealtorId(long reviewerId, long realtorId);
	List<Review> findByRealtorId(long realtorId);
}
