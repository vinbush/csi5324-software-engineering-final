package edu.baylor.propertypro.dto;

import lombok.Data;

@Data
public class RealtorDisplayDTO {
	public long id;
	public String name;
    public String phone;
    public String street;
    public String unitNum;
    public String city;
    public String state;
    public String zipCode;
    public String agency;
    public int yearsExperience;
    public String brokerage;
    public String website;
    public double averageRating;
}
