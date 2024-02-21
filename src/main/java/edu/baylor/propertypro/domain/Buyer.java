package edu.baylor.propertypro.domain;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import edu.baylor.propertypro.service.UserVisitor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor(force=true)
public class Buyer extends User {
	private static final long serialVersionUID = 1L;
	
	private LocalDate birthdate;
	private String maritalStatus;
	private int dependents;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "reviewer")
	private List<Review> reviews;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "buyer")
	private List<Favorite> favorites;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "buyer")
	private List<Request> requests;
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Arrays.asList(new SimpleGrantedAuthority("ROLE_BUYER"));
	}

	@Override
	public void accept(UserVisitor visitor) {
		visitor.visit(this);
	}
}
