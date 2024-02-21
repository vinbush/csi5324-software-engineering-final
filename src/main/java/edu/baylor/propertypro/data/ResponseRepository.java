package edu.baylor.propertypro.data;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import edu.baylor.propertypro.domain.Response;

@Repository
public interface ResponseRepository extends PagingAndSortingRepository<Response, Long> {

}
