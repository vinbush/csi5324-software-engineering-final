package edu.baylor.propertypro.service;

import static org.mockito.Mockito.when;

import java.util.Optional;

import static org.mockito.Mockito.any;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import edu.baylor.propertypro.data.ListingRepository;
import edu.baylor.propertypro.data.RealtorRepository;
import edu.baylor.propertypro.domain.Listing;
import edu.baylor.propertypro.domain.ListingType;
import edu.baylor.propertypro.domain.Realtor;
import edu.baylor.propertypro.service.serviceexceptions.InvalidListingException;

@SpringBootTest
public class ListingServiceTests {
	@Mock
	private ListingRepository listingRepo;
	
	@Mock
	private RealtorRepository realtorRepo;
	
	@InjectMocks
	private ListingService listingService = new ListingService();
	
	private Listing oldListing;
	private Realtor dude;
	
	@BeforeEach
	public void setup() {
		when(listingRepo.save(any(Listing.class))).then(i -> i.getArgument(0));
		when(listingRepo.findOptionalByStreetAndUnitNumAndCityAndStateAndZipCode(any(String.class), any(String.class), any(String.class), any(String.class), any(String.class))).thenReturn(Optional.empty());
		dude = new Realtor();
		dude.setName("George Realtor");dude.setPhone("9999999999");
		dude.setUsername("realtorguy");
		dude.setPassword("password");
		dude.setStreet("1111 Broad");
		dude.setUnitNum("");
		dude.setCity("Marlville");
		dude.setState("Iowa");
		dude.setZipCode("50171");
		dude.setAgency("Real Realty");
		dude.setYearsExperience(20);
		dude.setBrokerage("Real Brokerage");
		dude.setWebsite("real.com");
		oldListing = new Listing(ListingType.HOUSE, 200000, 2000, 1500, 2, 2, false, 1, "Test house", "Really nice", 9, 9, "1100 Main", "Montezuma", "Iowa", "50171");
		oldListing.setRealtor(dude);
	}
	
	@Test
	void testCreateSuccess() throws InvalidListingException {
		Listing newListing; 
		newListing = listingService.createListing(oldListing);
		Assertions.assertTrue(newListing.equals(oldListing));
	}
	
	@Test
	void testCreateFail() throws InvalidListingException {
		when(listingRepo.findOptionalByStreetAndUnitNumAndCityAndStateAndZipCode(any(String.class), any(String.class), any(String.class), any(String.class), any(String.class))).thenReturn(Optional.of(oldListing));
		boolean excepThrow = false;
		try{
			listingService.createListing(oldListing);
		} 
		catch(Exception e) {
			excepThrow = true;
			System.out.println(e.toString());
		}
		Assertions.assertTrue(excepThrow);
	}
	
	@Test
	void testListingPrice() {
		oldListing.setListingPrice(0);
		boolean excepThrow = false;
		try{
			listingService.createListing(oldListing);
		} 
		catch(Exception e) {
			excepThrow = true;
		}
		Assertions.assertTrue(excepThrow);
	}
	
	@Test
	void testListingHouseSize() {
		oldListing.setHouseSize(0);
		boolean excepThrow = false;
		try{
			listingService.createListing(oldListing);
		} 
		catch(Exception e) {
			excepThrow = true;
		}
		Assertions.assertTrue(excepThrow);
	}
	
	@Test
	void testListingPropertySize() {
		oldListing.setPropertySize(0);
		boolean excepThrow = false;
		try{
			listingService.createListing(oldListing);
		} 
		catch(Exception e) {
			excepThrow = true;
		}
		Assertions.assertTrue(excepThrow);
	}
	
	@Test
	void testListingNumBed() {
		oldListing.setNumBed(0);
		boolean excepThrow = false;
		try{
			listingService.createListing(oldListing);
		} 
		catch(Exception e) {
			excepThrow = true;
		}
		Assertions.assertTrue(excepThrow);
	}
	
	@Test
	void testListingNumBath() {
		oldListing.setNumBath(0);
		boolean excepThrow = false;
		try{
			listingService.createListing(oldListing);
		} 
		catch(Exception e) {
			excepThrow = true;
		}
		Assertions.assertTrue(excepThrow);
	}
	
	@Test
	void testListingNumFloorsLess() {
		oldListing.setNumFloors(0);
		boolean excepThrow = false;
		try{
			listingService.createListing(oldListing);
		} 
		catch(Exception e) {
			excepThrow = true;
		}
		Assertions.assertTrue(excepThrow);
	}
	
	@Test
	void testListingNumFloorsMore() {
		oldListing.setNumFloors(11);
		boolean excepThrow = false;
		try{
			listingService.createListing(oldListing);
		} 
		catch(Exception e) {
			excepThrow = true;
		}
		Assertions.assertTrue(excepThrow);
	}
	
	@Test
	void testListingTitle() {
		oldListing.setTitle("");
		boolean excepThrow = false;
		try{
			listingService.createListing(oldListing);
		} 
		catch(Exception e) {
			excepThrow = true;
		}
		Assertions.assertTrue(excepThrow);
	}
	
	@Test
	void testListingDesc() {
		oldListing.setDescription("");
		boolean excepThrow = false;
		try{
			listingService.createListing(oldListing);
		} 
		catch(Exception e) {
			excepThrow = true;
		}
		Assertions.assertTrue(excepThrow);
	}
	
	@Test
	void testListingStreet() {
		oldListing.setStreet("");
		boolean excepThrow = false;
		try{
			listingService.createListing(oldListing);
		} 
		catch(Exception e) {
			excepThrow = true;
		}
		Assertions.assertTrue(excepThrow);
	}
	
	@Test
	void testListingCity() {
		oldListing.setCity("");
		boolean excepThrow = false;
		try{
			listingService.createListing(oldListing);
		} 
		catch(Exception e) {
			excepThrow = true;
		}
		Assertions.assertTrue(excepThrow);
	}
	
	@Test
	void testListingState() {
		oldListing.setState("");
		boolean excepThrow = false;
		try{
			listingService.createListing(oldListing);
		} 
		catch(Exception e) {
			excepThrow = true;
		}
		Assertions.assertTrue(excepThrow);
	}
	
	@Test
	void testListingZipCode() {
		oldListing.setZipCode("");
		boolean excepThrow = false;
		try{
			listingService.createListing(oldListing);
		} 
		catch(Exception e) {
			excepThrow = true;
		}
		Assertions.assertTrue(excepThrow);
	}
	
	@Test
	void testListingLatLess() {
		oldListing.setLatitude(-91);
		boolean excepThrow = false;
		try{
			listingService.createListing(oldListing);
		} 
		catch(Exception e) {
			excepThrow = true;
		}
		Assertions.assertTrue(excepThrow);
	}
	
	@Test
	void testListingLatMore() {
		oldListing.setLatitude(91);
		boolean excepThrow = false;
		try{
			listingService.createListing(oldListing);
		} 
		catch(Exception e) {
			excepThrow = true;
		}
		Assertions.assertTrue(excepThrow);
	}
	
	@Test
	void testListingLongLess() {
		oldListing.setLongitude(-181);
		boolean excepThrow = false;
		try{
			listingService.createListing(oldListing);
		} 
		catch(Exception e) {
			excepThrow = true;
		}
		Assertions.assertTrue(excepThrow);
	}
	
	@Test
	void testListingLongMore() {
		oldListing.setLongitude(181);
		boolean excepThrow = false;
		try{
			listingService.createListing(oldListing);
		} 
		catch(Exception e) {
			excepThrow = true;
		}
		Assertions.assertTrue(excepThrow);
	}
	
	@Test
	void testListingRealtor() {
		oldListing.setRealtor(null);
		boolean excepThrow = false;
		try{
			listingService.createListing(oldListing);
		} 
		catch(Exception e) {
			excepThrow = true;
		}
		Assertions.assertTrue(excepThrow);
	}
	
	@Test
	void testListingAPTUnitNumBad() {
		oldListing.setListingType(ListingType.APT);
		oldListing.setUnitNum("");
		boolean excepThrow = false;
		try{
			listingService.createListing(oldListing);
		} 
		catch(Exception e) {
			excepThrow = true;
		}
		Assertions.assertTrue(excepThrow);
	}
	
	@Test
	void testListingAPTUnitNumGood() {
		oldListing.setListingType(ListingType.APT);
		oldListing.setUnitNum("205W");
		boolean excepThrow = false;
		try{
			listingService.createListing(oldListing);
		} 
		catch(Exception e) {
			excepThrow = true;
		}
		Assertions.assertFalse(excepThrow);
	}
	
	@Test
	void testListingCondoUnitNumBad() {
		oldListing.setListingType(ListingType.CONDO);
		oldListing.setUnitNum("");
		boolean excepThrow = false;
		try{
			listingService.createListing(oldListing);
		} 
		catch(Exception e) {
			excepThrow = true;
		}
		Assertions.assertTrue(excepThrow);
	}
	
	@Test
	void testListingCondoUnitNumGood() {
		oldListing.setListingType(ListingType.CONDO);
		oldListing.setUnitNum("205W");
		boolean excepThrow = false;
		try{
			listingService.createListing(oldListing);
		} 
		catch(Exception e) {
			excepThrow = true;
		}
		Assertions.assertFalse(excepThrow);
	}
	
	@Test
	void testDeleteGood() {
		when(listingRepo.findById(any(Long.class))).thenReturn(Optional.of(oldListing));
		boolean excepThrow = false;
		try{
			listingService.deleteListing(0);
		} 
		catch(Exception e) {
			excepThrow = true;
		}
		Assertions.assertFalse(excepThrow);
	}
	
	@Test
	void testDeleteBad() {
		boolean excepThrow = false;
		try{
			listingService.deleteListing(100);
		} 
		catch(Exception e) {
			excepThrow = true;
		}
		Assertions.assertTrue(excepThrow);
	}
	
	@Test
	void testUpdateGood() {
		when(listingRepo.findById(any(Long.class))).thenReturn(Optional.of(oldListing));
		oldListing.setId(0L);
		oldListing.setTitle("Temp House");
		boolean excepThrow = false;
		try{
			listingService.updateListing(oldListing);
		} 
		catch(Exception e) {
			excepThrow = true;
		}
		Assertions.assertFalse(excepThrow);
	}
	
	@Test
	void testUpdateBad() {
		oldListing.setId(2L);
		oldListing.setTitle("Temp House");
		boolean excepThrow = false;
		try{
			listingService.updateListing(oldListing);
		} 
		catch(Exception e) {
			excepThrow = true;
		}
		Assertions.assertTrue(excepThrow);
	}
}
