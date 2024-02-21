package edu.baylor.propertypro.domain;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor(access=AccessLevel.PUBLIC, force=true)
@RequiredArgsConstructor(access=AccessLevel.PUBLIC)
@Entity
@EqualsAndHashCode(exclude="photos")
public class Listing {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(updatable = false, nullable = false)
	@NotNull
	private Long id;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	@NotNull
	@NonNull // for lombok to include in constructor
	private ListingType listingType;
	
	@Column(nullable = false)
	@NotNull
	@NonNull
	private int listingPrice;
	
	@Column(nullable = false)
	@NotNull
	@NonNull
	private int propertySize;
	
	@Column(nullable = false)
	@NotNull
	@NonNull
	private int houseSize;
	
	@Column(nullable = false)
	@NotNull
	@NonNull
	private int numBed;
	
	@Column(nullable = false)
	@NotNull
	@NonNull
	private int numBath;
	
	@Column(nullable = false)
	@NotNull
	@NonNull
	private boolean basement;
	
	@Column(nullable = false)
	@NotNull
	@NonNull
	private int numFloors;
	
	@Column(nullable = false)
	@NotNull
	@NonNull
	private String title;
	
	@Column(nullable = false)
	@NotNull
	@NonNull
	@Lob
	private String description;
	
	@ManyToOne
    private Realtor realtor;
	
	@OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, mappedBy = "listing")
	private List<Photo> photos = new ArrayList<Photo>();
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "listing")
	private Set<Favorite> favorites = new HashSet<Favorite>();
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "listing")
	private Set<Request> requests = new HashSet<Request>();
	
	//Look at Latitude and Longitude when implementation
	@Column(nullable = false)
	@NotNull
	@NonNull
	private double latitude;
	
	@Column(nullable = false)
	@NotNull
	@NonNull
	private double longitude;
	
	//Look at time when implementation
	@Column(updatable = false, nullable = false)
	private ZonedDateTime addedOnDate;
	
	@Column(nullable = false)
	@NotNull
	@NonNull
	private String street;
	
	@Column()
	private String unitNum;
	
	@Pattern(regexp = "[\\w\\s]+", message = "must contain valid city")
	@Column(nullable = false)
	@NotNull
	@NonNull
	private String city;
	
	@Pattern(regexp = "[\\w\\s]+", message = "must contain valid state")
	@Column(nullable = false)
	@NotNull
	@NonNull
	private String state;
	
	//@Pattern(regexp = "[0-9]{5}", message = "must contain valid zip code")
	@Column(nullable = false)
	@NotNull
	@NonNull
	private String zipCode;
	
	@Column(nullable = false)
	@NotNull
	private boolean sold = false;
	
	@PrePersist
	void createdAt() {
		this.addedOnDate = ZonedDateTime.now();
	}
	
	public void addPhoto(Photo photo) {
		this.photos.add(photo);
        photo.setListing(this);
    }
 
    public void removePhoto(Photo photo) {
    	photo.setListing(null);
        this.photos.remove(photo);
    }
	
}
