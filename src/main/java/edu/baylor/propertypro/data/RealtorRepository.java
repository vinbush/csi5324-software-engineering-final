package edu.baylor.propertypro.data;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import edu.baylor.propertypro.domain.Realtor;

@Repository
@Transactional
public interface RealtorRepository extends UserBaseRepository<Realtor> {
	@Query("select r from Realtor as r left join fetch r.reviews as reviews where r.id = :id")
	Optional<Realtor> getRealtorByIdWithReviews(long id);
}
