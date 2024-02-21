package edu.baylor.propertypro.service;

import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import edu.baylor.propertypro.data.FavoriteRepository;
import edu.baylor.propertypro.data.OfferRepository;
import edu.baylor.propertypro.data.RequestRepository;
import edu.baylor.propertypro.domain.Buyer;
import edu.baylor.propertypro.domain.Realtor;
import edu.baylor.propertypro.dto.BaseProfileDTO;
import edu.baylor.propertypro.dto.BuyerProfileDTO;
import edu.baylor.propertypro.dto.FavoriteDTO;
import edu.baylor.propertypro.dto.OfferDTO;
import edu.baylor.propertypro.dto.RealtorProfileDTO;
import edu.baylor.propertypro.dto.RequestDTO;

@Component
@Transactional
public class FetchProfileUserVisitor implements UserVisitor {
	@Autowired
	RequestRepository requestRepo;
	
	@Autowired
	OfferRepository offerRepo;
	
	@Autowired
	FavoriteRepository favoriteRepo;
	
	@Autowired
	ModelMapper modelMapper;
	
	public FetchProfileUserVisitor(RequestRepository requestRepo, OfferRepository offerRepo,
			FavoriteRepository favoriteRepo, ModelMapper modelMapper) {
		this.requestRepo = requestRepo;
		this.offerRepo = offerRepo;
		this.favoriteRepo = favoriteRepo;
		this.modelMapper = modelMapper;
	}
	
	private BaseProfileDTO profile;
	
	public BaseProfileDTO getProfile() {
		return profile;
	}

	@Override
	@Transactional
	public void visit(Buyer buyer) {
		BuyerProfileDTO buyerProfile = new BuyerProfileDTO();
		buyerProfile.setRequests(
			requestRepo.findByBuyerId(buyer.getId())
				.stream().map(r -> modelMapper.map(r, RequestDTO.class)).collect(Collectors.toList())
		);
		
		buyerProfile.setOffers(
			offerRepo.findByBuyerId(buyer.getId())
				.stream().map(o -> modelMapper.map(o, OfferDTO.class)).collect(Collectors.toList())
		);
		
		buyerProfile.setFavorites(
			favoriteRepo.findByBuyerId(buyer.getId())
				.stream().map(o -> modelMapper.map(o, FavoriteDTO.class)).collect(Collectors.toList())
		);
		
		this.profile = buyerProfile;
	}

	@Override
	@Transactional
	public void visit(Realtor realtor) {
		RealtorProfileDTO realtorProfile = new RealtorProfileDTO();
		realtorProfile.setRequests(
			requestRepo.findByListingRealtorId(realtor.getId())
				.stream().map(r -> modelMapper.map(r, RequestDTO.class)).collect(Collectors.toList())
		);
		
		realtorProfile.setOffers(
			offerRepo.findByListingRealtorId(realtor.getId())
				.stream().map(o -> modelMapper.map(o, OfferDTO.class)).collect(Collectors.toList())
		);
		
		this.profile = realtorProfile;
	}

}
