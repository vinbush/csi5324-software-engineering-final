package edu.baylor.propertypro.service;

import java.util.List;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.baylor.propertypro.data.FavoriteRepository;
import edu.baylor.propertypro.data.OfferRepository;
import edu.baylor.propertypro.data.RequestRepository;
import edu.baylor.propertypro.domain.User;
import edu.baylor.propertypro.dto.BaseProfileDTO;

@Service
public class ProfileService {
	
	@Autowired
	RequestRepository requestRepo;
	
	@Autowired
	OfferRepository offerRepo;
	
	@Autowired
	FavoriteRepository favoriteRepo;
	
	@Autowired
	ModelMapper modelMapper;
	
	@Transactional
	public BaseProfileDTO getUserProfile(User user) {
		FetchProfileUserVisitor visitor = new FetchProfileUserVisitor(requestRepo, offerRepo, favoriteRepo, modelMapper);
		user.accept(visitor);
		BaseProfileDTO dto = visitor.getProfile();
		return dto;
	}

}
