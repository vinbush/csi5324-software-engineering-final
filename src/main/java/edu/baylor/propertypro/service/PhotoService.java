package edu.baylor.propertypro.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.baylor.propertypro.data.PhotoRepository;
import edu.baylor.propertypro.domain.Photo;

@Service
@Transactional
public class PhotoService {
	@Autowired
	PhotoRepository photoRepo;
	
	@Transactional
	public List<Photo> getListingPhotos(long id, Optional<Boolean> firstOnly) {
		List<Photo> returnPhotos = new ArrayList<>();
		if (firstOnly.orElse(false)) {
			Optional<Photo> photo = photoRepo.findFirstByListingId(id);
			if (photo.isPresent()) {
				returnPhotos.add(photo.get());
			}
		} else {
			returnPhotos = photoRepo.findByListingId(id);
		}
		return returnPhotos;
	}
	
	@Transactional
	public Optional<Photo> getRealtorPhoto(long realtorId) {
		Optional<Photo> photo = photoRepo.findFirstByRealtorId(realtorId);
		return photo;
	}
}
