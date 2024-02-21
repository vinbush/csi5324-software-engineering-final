package edu.baylor.propertypro.rest;

import java.util.Base64;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.baylor.propertypro.domain.Photo;
import edu.baylor.propertypro.domain.Realtor;
import edu.baylor.propertypro.dto.PhotoDTO;
import edu.baylor.propertypro.dto.RealtorDisplayDTO;
import edu.baylor.propertypro.service.PhotoService;
import edu.baylor.propertypro.service.RealtorService;

@RestController
@RequestMapping("/api/realtors")
public class RealtorController {
	@Autowired
	RealtorService realtorService;
	
	@Autowired
	PhotoService photoService;
	
	private ModelMapper modelMapper = new ModelMapper();
	
	@GetMapping("/{id}")
	public ResponseEntity<RealtorDisplayDTO> getRealtor(@PathVariable long id) {
		Optional<Realtor> realtor = realtorService.getRealtor(id);
		if (realtor.isPresent()) {
			return ResponseEntity.ok(modelMapper.map(realtor.get(), RealtorDisplayDTO.class));
		} else {
			return new ResponseEntity<RealtorDisplayDTO>(HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/{id}/photo")
	public ResponseEntity<PhotoDTO> getRealtorPhoto(@PathVariable long id) {
		Optional<Photo> photo = photoService.getRealtorPhoto(id);
		
		if (!photo.isPresent()) {
			return new ResponseEntity<PhotoDTO>(new PhotoDTO(), HttpStatus.OK);
		}
		
		Photo p = photo.get();
		
		PhotoDTO dto = new PhotoDTO();
		dto.setPicture(Base64.getEncoder().withoutPadding().encodeToString(p.getPicture()));
		dto.setContentType(p.getContentType());
		dto.setCaption(p.getCaption());
		return new ResponseEntity<PhotoDTO>(dto, HttpStatus.OK);
	}
}
