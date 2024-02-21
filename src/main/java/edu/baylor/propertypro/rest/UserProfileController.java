package edu.baylor.propertypro.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.baylor.propertypro.domain.Request;
import edu.baylor.propertypro.domain.User;
import edu.baylor.propertypro.dto.BaseProfileDTO;
import edu.baylor.propertypro.service.ProfileService;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/profile")
@Slf4j
public class UserProfileController {
	@Autowired
	ProfileService profileService;
	
	@GetMapping
	public ResponseEntity<BaseProfileDTO> getProfile(@AuthenticationPrincipal User user) {
		BaseProfileDTO profile = profileService.getUserProfile(user);
		
		return new ResponseEntity<BaseProfileDTO>(profile, HttpStatus.OK);
	}
}
