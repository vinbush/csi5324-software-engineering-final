package edu.baylor.propertypro.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import edu.baylor.propertypro.data.FavoriteRepository;
import edu.baylor.propertypro.data.ListingRepository;
import edu.baylor.propertypro.data.OfferRepository;
import edu.baylor.propertypro.data.PhotoRepository;
import edu.baylor.propertypro.data.RealtorRepository;
import edu.baylor.propertypro.data.RequestRepository;
import edu.baylor.propertypro.domain.Buyer;
import edu.baylor.propertypro.domain.Favorite;
import edu.baylor.propertypro.domain.Listing;
import edu.baylor.propertypro.domain.ListingType;
import edu.baylor.propertypro.domain.Offer;
import edu.baylor.propertypro.domain.Request;
import edu.baylor.propertypro.domain.SearchFilter;
import edu.baylor.propertypro.dto.OfferDTO;
import edu.baylor.propertypro.dto.RequestDTO;
import edu.baylor.propertypro.service.serviceexceptions.EntityDoesNotExistException;
import edu.baylor.propertypro.service.serviceexceptions.InvalidListingException;

@Service
@Transactional
public class ListingService {
	@Autowired
	private ListingRepository listingRepo;
	
	@Autowired
	private OfferRepository offerRepo;
	
	@Autowired
	private RequestRepository requestRepo;
	
	@Autowired
	private FavoriteRepository favoriteRepo;
	
	@Autowired
	private JmsTemplate jmsTemplate;
	
	@Autowired
	private ModelMapper modelMapper;
	
	public List<Listing> searchListings(SearchFilter filter) {
		boolean validStreet = filter.getStreet().isPresent() && !(filter.getStreet().get().trim().equals(""));
		boolean validCity = filter.getCity().isPresent() && !(filter.getCity().get().trim().equals(""));
		boolean validState = filter.getState().isPresent() && !(filter.getState().get().trim().equals(""));
		boolean validZipCode = filter.getZipCode().isPresent() && !(filter.getZipCode().get().trim().equals(""));
		
		if (validStreet && validCity && validState) {
			return listingRepo.findListingsByStreet(filter.getStreet().get(),
					filter.getUnitNum(), filter.getCity().get(), filter.getState().get());
		} else if (validCity && validState) {
			return listingRepo.findListingsByCityState(filter.getCity().get(), filter.getState().get(),
					filter.getMinPrice(), filter.getMaxPrice(),
					filter.getMinPropertySize(), filter.getMaxPropertySize(),
					filter.getMinHouseSize(), filter.getMaxHouseSize(),
					filter.getMinNumBed(), filter.getMaxNumBed(),
					filter.getMinNumBath(), filter.getMaxNumBath(),
					filter.getNumFloors());
		} else if (validState) {
			return listingRepo.findListingsByState(filter.getState().get(),
					filter.getMinPrice(), filter.getMaxPrice(),
					filter.getMinPropertySize(), filter.getMaxPropertySize(),
					filter.getMinHouseSize(), filter.getMaxHouseSize(),
					filter.getMinNumBed(), filter.getMaxNumBed(),
					filter.getMinNumBath(), filter.getMaxNumBath(),
					filter.getNumFloors());
		} else if (validZipCode) {
			return listingRepo.findListingsByZipCode(filter.getZipCode().get(),
					filter.getMinPrice(), filter.getMaxPrice(),
					filter.getMinPropertySize(), filter.getMaxPropertySize(),
					filter.getMinHouseSize(), filter.getMaxHouseSize(),
					filter.getMinNumBed(), filter.getMaxNumBed(),
					filter.getMinNumBath(), filter.getMaxNumBath(),
					filter.getNumFloors());
		} else {
			return new ArrayList<>();
		}
	}
	
	public Optional<Listing> getListing(long id) {
		Optional<Listing> listing = listingRepo.findById(id);
		return listing;
	}
	
	
	@Transactional
	public Listing createListing(Listing listing) throws InvalidListingException {
		Optional<Listing> foundListing = listingRepo.findOptionalByStreetAndUnitNumAndCityAndStateAndZipCode(
				listing.getStreet(), listing.getUnitNum(), listing.getCity(), listing.getState(), listing.getZipCode());
		if (foundListing.isPresent()) {
			throw new IllegalArgumentException("Listing already exists");
		}
		
		tryVerifyListing(listing);
		
		return listingRepo.save(listing);
	}
	
	public Listing updateListing(Listing listing) throws InvalidListingException {
		Optional<Listing> foundListing = listingRepo.findById(listing.getId());
		if (!foundListing.isPresent()) {
			throw new IllegalArgumentException("That listing doesn't exist");
		}
		
		tryVerifyListing(listing);
		
		return listingRepo.save(listing);
	}
	
	public void deleteListing(long id) {
		Optional<Listing> foundListing = listingRepo.findById(id);
		if (!foundListing.isPresent()) {
			throw new IllegalArgumentException("That listing doesn't exist");
		}
		listingRepo.deleteById(id);
	}
	
	public Request makeRequest(Request request, long listingId) {
		Optional<Listing> foundListing = listingRepo.findById(listingId);
		if (!foundListing.isPresent()) {
			throw new IllegalArgumentException("That listing doesn't exist!");
		}
		
		Optional<Request> existingRequest = requestRepo.findFirstByBuyerIdAndListingId(request.getBuyer().getId(), foundListing.get().getId());
		
		if (existingRequest.isPresent()) {
			throw new IllegalArgumentException("You've already inquired about this property!");
		}
		
		request.setListing(foundListing.get());
		
		Request newRequest = requestRepo.save(request);
		
		RequestDTO requestMessage = modelMapper.map(newRequest, RequestDTO.class);
		
		jmsTemplate.convertAndSend("propertypro.request.topic", requestMessage);
		
		return newRequest;
	}
	
	public Offer makeOffer(Offer offer, long listingId) {
		Optional<Listing> foundListing = listingRepo.findById(listingId);
		if (!foundListing.isPresent()) {
			throw new IllegalArgumentException("That listing doesn't exist!");
		}
		
		Optional<Offer> existingOffer = offerRepo.findFirstByBuyerIdAndListingId(offer.getBuyer().getId(), foundListing.get().getId());
		
		if (existingOffer.isPresent()) {
			throw new IllegalArgumentException("You've already made an offer on this property!");
		}
		
		offer.setListing(foundListing.get());
		
		Offer newOffer = offerRepo.save(offer);
		
		OfferDTO offerMessage = modelMapper.map(newOffer, OfferDTO.class);
		
		jmsTemplate.convertAndSend("propertypro.offer.topic", offerMessage);
		
		return newOffer;
	}
	
	private void tryVerifyListing(Listing listing) throws InvalidListingException {

		if ((listing.getListingType() == ListingType.APT || listing.getListingType() == ListingType.CONDO)
				&& (listing.getUnitNum() == null || listing.getUnitNum().trim().isEmpty())) {
			throw new InvalidListingException("Apartments and condos must provide unit numbers.");
		}
		if (listing.getRealtor() == null) {
			throw new InvalidListingException("Listing must have a realtor.");
		}
		if (listing.getPhotos().size() == 0) {
			throw new InvalidListingException("Listing must have at least 1 photo.");
		}
		if (listing.getListingPrice() < 1) {
			throw new InvalidListingException("Listing prices must be greater than 0.");
		}
		if (listing.getHouseSize() < 1) {
			throw new InvalidListingException("House size must be greater than 0.");
		}
		if (listing.getPropertySize() < 1) {
			throw new InvalidListingException("Property size must be greater than 0.");
		}
		if (listing.getNumBed() < 1) {
			throw new InvalidListingException("Number of bedrooms must be greater than 0.");
		}
		if (listing.getNumBath() < 1) {
			throw new InvalidListingException("Number of bathrooms must be greater than 0.");
		}
		if (listing.getNumFloors() < 1 || listing.getNumFloors() > 10) {
			throw new InvalidListingException("Number of floors must be greater than 0.");
		}
		if (listing.getTitle().equals("")) {
			throw new InvalidListingException("Title can not be empty.");
		}
		if (listing.getDescription().equals("")) {
			throw new InvalidListingException("Description can not be empty.");
		}
		if (listing.getStreet().equals("")) {
			throw new InvalidListingException("Street can not be empty.");
		}
		if (listing.getCity().equals("")) {
			throw new InvalidListingException("City can not be empty.");
		}
		if (listing.getState().equals("")) {
			throw new InvalidListingException("State can not be empty.");
		}
		if (listing.getZipCode().equals("")) {
			throw new InvalidListingException("Zip code can not be empty.");
		}
		if (listing.getLatitude() < -90 || listing.getLatitude() > 90) {
			throw new InvalidListingException("Latitude must be in proper range.");
		}
		if (listing.getLongitude() < -180 || listing.getLongitude() > 180) {
			throw new InvalidListingException("Longitude must be in proper range.");
		}
	}

	public Favorite addFavorite(long listingId, String note, Buyer buyer) throws EntityDoesNotExistException {
		Optional<Listing> foundListing = listingRepo.findById(listingId);
		
		if (!foundListing.isPresent()) {
			throw new EntityDoesNotExistException("That listing doesn't exist!");
		}
		
		Favorite fav = new Favorite();
		
		fav.setListing(foundListing.get());
		fav.setNote(note);
		fav.setBuyer(buyer);
		
		fav = favoriteRepo.save(fav);
		
		return fav;
	}
	
	public boolean isUserFavorite(long buyerId, long listingId) {
		Optional<Favorite> foundFav = favoriteRepo.findByBuyerIdAndListingId(buyerId, listingId);
		
		return foundFav.isPresent();
	}
}
