package edu.baylor.propertypro.domain;

import java.util.Arrays;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import edu.baylor.propertypro.service.UserVisitor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@NoArgsConstructor(force=true)
public abstract class User implements UserDetails {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(updatable = false, nullable = false)
	protected long id;
	
	@Column(nullable = false)
	@Size(min = 5, message = "Username must be at least 5 characters long")
	protected String username;
	
	@Column(nullable = false)
	@Email(message = "Must be a valid email address")
	protected String email;
	
	@Column(nullable = false)
	@Size(min = 6, message = "Password must be at least 6 characters long")
	protected String password;
	
	@Column(nullable = false)
	protected String name;
	
	@Column(nullable = false)
	@Pattern(regexp = "^[0-9]{10}$", message = "Must be valid phone number")
	protected String phone;
	
	@Column(nullable = false)
	protected String street;
	
	@Column(nullable = false)
	protected String unitNum;
	
	@Column(nullable = false)
	protected String city;
	
	@Column(nullable = false)
	protected String state;
	
	@Column(nullable = false)
	protected String zipCode;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"));
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
	
	public abstract void accept(UserVisitor visitor);
}
