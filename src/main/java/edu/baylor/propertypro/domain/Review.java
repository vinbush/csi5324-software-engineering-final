package edu.baylor.propertypro.domain;

import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor(access=AccessLevel.PRIVATE, force=true)
public class Review {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(nullable = false)
	private long id;
	
	@Min(1)
	@Max(10)
	private int rating;
	
	@NotNull
	@NotEmpty
	@Column(nullable = false)
	private String textBody;
	
	private Instant createdAt;
	
	@ManyToOne(fetch = FetchType.EAGER)
	private Buyer reviewer;
	
	@ManyToOne
	private Realtor realtor;
	
	@PrePersist
	void placedAt() {
		this.createdAt = Instant.now();
	}
}
