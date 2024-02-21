package edu.baylor.propertypro.domain;

import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@NoArgsConstructor(force=true)
public class BaseRequest {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(nullable = false)
	private Long id;
	
	private Instant createdAt;
	
	@NotNull
	@NotEmpty
	private String textBody;
	
	public boolean hasResponse() {
		return this.response != null && this.response.getId() != null;
	}
	
	@OneToOne(mappedBy = "request", fetch = FetchType.EAGER)
	private Response response;
	
	@NotNull
	@ManyToOne
	private Buyer buyer;
	
	@ManyToOne
	private Listing listing;
	
	@PrePersist
	void placedAt() {
		this.createdAt = Instant.now();
	}
}
