package edu.baylor.propertypro.domain;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import edu.baylor.propertypro.service.UserVisitor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Entity
@Data
@ToString(exclude = {"reviews"})
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper=true, exclude="photo")
public class Realtor extends User {
	private static final long serialVersionUID = 1L;

	@Column(nullable = false)
	private String agency;
	
	@Column(nullable = false)
	private int yearsExperience;
	
	@Column(nullable = false)
	private String brokerage;
	
	@Column(nullable = false)
	private String website;
	
	@OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, mappedBy = "realtor")
	private Photo photo;
	
	@OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE}, mappedBy = "realtor")
	private List<Review> reviews;
	
	private double averageRating = 0;
	
	public void addReview(Review review) {
		this.reviews.add(review);
		review.setRealtor(this);
    }
 
    public void removeReview(Review review) {
    	review.setRealtor(null);
        this.reviews.remove(review);
    }
    
    public void addPhoto(Photo photo) {
    	this.photo = photo;
    	photo.setRealtor(this);
    }
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Arrays.asList(new SimpleGrantedAuthority("ROLE_REALTOR"));
	}

	@Override
	public void accept(UserVisitor visitor) {
		visitor.visit(this);
	}
}
