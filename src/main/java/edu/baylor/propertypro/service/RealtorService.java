package edu.baylor.propertypro.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.baylor.propertypro.data.PhotoRepository;
import edu.baylor.propertypro.data.RealtorRepository;
import edu.baylor.propertypro.domain.Realtor;

@Service
public class RealtorService {
	@Autowired
	RealtorRepository realtorRepo;
	
	@Autowired
	PhotoRepository photoRepo;
	
	public Optional<Realtor> getRealtor(long id) {
		Optional<Realtor> realtor = realtorRepo.findById(id);
		return realtor;
	}
	
}
