package edu.baylor.propertypro.rest;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;

import edu.baylor.propertypro.domain.Buyer;
import edu.baylor.propertypro.domain.Favorite;
import edu.baylor.propertypro.domain.Listing;
import edu.baylor.propertypro.domain.Offer;
import edu.baylor.propertypro.domain.Photo;
import edu.baylor.propertypro.domain.Realtor;
import edu.baylor.propertypro.domain.Request;
import edu.baylor.propertypro.domain.SearchFilter;
import edu.baylor.propertypro.domain.User;
import edu.baylor.propertypro.dto.FavoriteCreationDTO;
import edu.baylor.propertypro.dto.FavoriteDTO;
import edu.baylor.propertypro.dto.ListingDTO;
import edu.baylor.propertypro.dto.OfferDTO;
import edu.baylor.propertypro.dto.PhotoDTO;
import edu.baylor.propertypro.dto.RequestDTO;
import edu.baylor.propertypro.service.ListingService;
import edu.baylor.propertypro.service.PhotoService;
import edu.baylor.propertypro.service.serviceexceptions.EntityDoesNotExistException;
import edu.baylor.propertypro.service.serviceexceptions.InvalidListingException;
import edu.baylor.propertypro.service.serviceexceptions.UnauthorizedActionException;

@RestController
@RequestMapping("/api/listings")
public class ListingController {
	@Autowired
	private ListingService listingService;
	
	@Autowired
	private PhotoService photoService;
	
	private ModelMapper modelMapper = new ModelMapper();
	
	@PostMapping("/search")
	public List<ListingDTO> searchListings(@RequestBody SearchFilter filter) {
		
		List<Listing> listings = listingService.searchListings(filter);
		List<ListingDTO> returnListings = listings.stream().map(l -> modelMapper.map(l, ListingDTO.class)).collect(Collectors.toList());
		return returnListings;
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ListingDTO> getListing(@PathVariable long id,
			SecurityContextHolderAwareRequestWrapper requestWrapper, @AuthenticationPrincipal User user) {
		Optional<Listing> listing = listingService.getListing(id);
		if (listing.isPresent()) {
			ListingDTO returnListing = modelMapper.map(listing.get(), ListingDTO.class);
			if (requestWrapper.isUserInRole("BUYER")) {
				returnListing.setUserFavorite(listingService.isUserFavorite(user.getId(), id));
			}
			return ResponseEntity.ok(returnListing);
		} else {
			return new ResponseEntity<ListingDTO>(HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/{id}/photos")
	public ResponseEntity<List<PhotoDTO>> getListingPhotos(@PathVariable long id, @RequestParam Optional<Boolean> firstOnly) {
		List<Photo> photos = photoService.getListingPhotos(id, firstOnly);
		
		List<PhotoDTO> returnPhotos = photos.stream().map(p -> {
			PhotoDTO dto = new PhotoDTO();
			dto.setPicture(Base64.getEncoder().withoutPadding().encodeToString(p.getPicture()));
			dto.setContentType(p.getContentType());
			dto.setCaption(p.getCaption());
			return dto;
		}).collect(Collectors.toList());
		
		return new ResponseEntity<List<PhotoDTO>>(returnPhotos, HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<ListingDTO> createListing(@RequestPart("listing") String jsonListing, @RequestPart("files") MultipartFile[] files,
			@AuthenticationPrincipal User user) throws InvalidListingException, IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		
		Listing listing = objectMapper.readValue(jsonListing, Listing.class);
		
		listing.setRealtor((Realtor)user);
		
		for (MultipartFile file : files) {
			Photo photo = new Photo();
			photo.setContentType(file.getContentType());
			photo.setPicture(file.getBytes());
			listing.addPhoto(photo);
		}
		
		Listing createdListing = listingService.createListing(listing);
		ListingDTO returnListing = modelMapper.map(createdListing, ListingDTO.class);
		
		return new ResponseEntity<ListingDTO>(returnListing, HttpStatus.CREATED);
	}
	
	@PostMapping("/{id}/request")
	public ResponseEntity<RequestDTO> requestInfo(@PathVariable long id, @RequestBody Request request, @AuthenticationPrincipal User user) throws UnauthorizedActionException {
		Request createdRequest;
		
		try {
			request.setBuyer((Buyer)user);
		} catch (ClassCastException e) {
			throw new UnauthorizedActionException("You aren't allowed to create requests!");
		}
		
		createdRequest = listingService.makeRequest(request, id);
		
		RequestDTO returnReview = modelMapper.map(createdRequest, RequestDTO.class);
		
		return new ResponseEntity<RequestDTO>(returnReview, HttpStatus.CREATED);
	}
	
	@PostMapping("/{id}/offer")
	public ResponseEntity<OfferDTO> makeOffer(@PathVariable long id, @RequestBody Offer offer, @AuthenticationPrincipal User user) throws UnauthorizedActionException {
		Offer createdOffer;
		
		try {
			offer.setBuyer((Buyer)user);
		} catch (ClassCastException e) {
			throw new UnauthorizedActionException("You aren't allowed to make offers!");
		}
		
		createdOffer = listingService.makeOffer(offer, id);
		
		OfferDTO returnReview = modelMapper.map(createdOffer, OfferDTO.class);
		
		return new ResponseEntity<OfferDTO>(returnReview, HttpStatus.CREATED);
	}
	
	@PostMapping("/{id}/favorite")
	public ResponseEntity<FavoriteDTO> addFavorite(@RequestBody FavoriteCreationDTO newFavorite, @PathVariable("id") long id, @AuthenticationPrincipal User user) throws UnauthorizedActionException, EntityDoesNotExistException {
		Buyer buyer;
		try {
			buyer = ((Buyer)user);
		} catch (ClassCastException e) {
			throw new UnauthorizedActionException("You aren't allowed to add favorites!");
		}

		Favorite favorite = listingService.addFavorite(id, newFavorite.getNote(), buyer);
		
		FavoriteDTO returnFav = modelMapper.map(favorite, FavoriteDTO.class);
		
		return new ResponseEntity<FavoriteDTO>(returnFav, HttpStatus.OK);
	}
}
