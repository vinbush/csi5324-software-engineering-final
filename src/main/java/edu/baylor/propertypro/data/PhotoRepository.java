package edu.baylor.propertypro.data;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import edu.baylor.propertypro.domain.Photo;

@Repository
@Transactional
public interface PhotoRepository extends PagingAndSortingRepository<Photo, Long>{
	@Transactional
	List<Photo> findByListingId(long listingId);

	@Transactional
	Optional<Photo> findFirstByRealtorId(long realtorId);

	@Transactional
	Optional<Photo> findFirstByListingId(long id);
}
