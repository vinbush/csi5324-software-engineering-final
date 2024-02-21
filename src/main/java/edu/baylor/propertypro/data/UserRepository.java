package edu.baylor.propertypro.data;

import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import edu.baylor.propertypro.domain.User;

@Repository
@Transactional
public interface UserRepository extends UserBaseRepository<User> {

}
