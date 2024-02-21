package edu.baylor.propertypro.security;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import edu.baylor.propertypro.data.UserRepository;
import edu.baylor.propertypro.domain.User;
import edu.baylor.propertypro.service.serviceexceptions.EmailAlreadyExistsException;
import edu.baylor.propertypro.service.serviceexceptions.UsernameAlreadyExistsException;

@Service
@Transactional
public class UserRepositoryUserDetailsService implements UserDetailsService {
	
	private UserRepository repo;
	private PasswordEncoder encoder;
	
	@Autowired
	public UserRepositoryUserDetailsService(UserRepository repo, PasswordEncoder encoder) {
		this.repo = repo;
		this.encoder = encoder;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = repo.findByUsername(username);
		if (user != null) {
			return user;
		}
		throw new UsernameNotFoundException("User " + username + " doesn't exist");
	}
	
	@Transactional
	public void createUser(User user) throws UsernameAlreadyExistsException, EmailAlreadyExistsException {
		user.setPassword(encoder.encode(user.getPassword()));
		User foundByUsername = repo.findByUsername(user.getUsername());
		User foundByEmail = repo.findByEmail(user.getEmail());
		
		if (foundByUsername != null)
			throw new UsernameAlreadyExistsException("That username is unavailable");
		
		if (foundByEmail!= null)
			throw new EmailAlreadyExistsException("That email is unavailable");
		
		repo.save(user);
	}

}
