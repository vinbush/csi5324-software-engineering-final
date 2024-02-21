package edu.baylor.propertypro.data;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

import edu.baylor.propertypro.domain.User;

@NoRepositoryBean
public interface UserBaseRepository<T extends User> extends PagingAndSortingRepository<T, Long> {
	@Query("select u from #{#entityName} as u where u.username = ?1")
	T findByUsername(String username);
	
	@Query("select u from #{#entityName} as u where u.email = ?1")
	T findByEmail(String email);
}
