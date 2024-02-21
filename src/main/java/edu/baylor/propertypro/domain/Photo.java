package edu.baylor.propertypro.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access=AccessLevel.PUBLIC, force=true)
@Entity
public class Photo {
	
	@Id
	@GeneratedValue
	private Long id;
	
	private String caption;
	
	@Column(nullable = false)
	private String contentType;
	
	@ManyToOne
	private Listing listing;
	
	@OneToOne
	private Realtor realtor;
	
	//@Type(type="org.hibernate.type.BinaryType")
	@Lob
	@Column(nullable = false)
	private byte[] picture;
}
