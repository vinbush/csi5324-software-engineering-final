package edu.baylor.propertypro.dto;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;

import edu.baylor.propertypro.domain.User;
import lombok.Data;

@Data
public class UserDTO {
	public UserDTO(User user) {
		this.name = user.getName();
		this.authorities = user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
	}
	
	private final String name;
	private final List<String> authorities;
}
