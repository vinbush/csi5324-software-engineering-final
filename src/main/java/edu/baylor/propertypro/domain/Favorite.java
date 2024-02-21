package edu.baylor.propertypro.domain;

import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor(access=AccessLevel.PUBLIC, force=true)
public class Favorite {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(nullable = false)
	private long id;
	
	private Instant createdAt;
	
	private String note;
	
	@ManyToOne
	private Buyer buyer;
	
	@ManyToOne
	private Listing listing;
	
	@PrePersist
	void placedAt() {
		this.createdAt = Instant.now();
	}
}
