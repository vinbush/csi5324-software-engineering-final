package edu.baylor.propertypro.domain;

import javax.persistence.Entity;
import javax.validation.constraints.Min;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor(force=true)
public class Offer extends BaseRequest{

	@Min(1)
	private int offerPrice;
}
