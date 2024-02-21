package edu.baylor.propertypro.rest;

import java.io.IOException;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import edu.baylor.propertypro.domain.Buyer;
import edu.baylor.propertypro.domain.Photo;
import edu.baylor.propertypro.domain.Realtor;
import edu.baylor.propertypro.domain.User;
import edu.baylor.propertypro.dto.UserDTO;
import edu.baylor.propertypro.security.UserRepositoryUserDetailsService;
import edu.baylor.propertypro.service.serviceexceptions.EmailAlreadyExistsException;
import edu.baylor.propertypro.service.serviceexceptions.UsernameAlreadyExistsException;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
	@Autowired
	private UserRepositoryUserDetailsService userService;
	
	@GetMapping("/user")
	public UserDTO user(Principal user) {
		if (user == null) {
			return null;
		}
		
		UserDTO returnUser = new UserDTO((User)SecurityContextHolder.getContext().getAuthentication().getPrincipal());
		
		return returnUser;
	}
	
	@PostMapping("/register/buyer")
	public ResponseEntity registerBuyer(@RequestPart("user") String jsonUser) throws JsonMappingException, IOException {
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			Buyer buyer = objectMapper.readValue(jsonUser, Buyer.class);
			userService.createUser(buyer);
			Map<Object, Object> response = new HashMap<>();
			response.put("message", "User created");
			return ResponseEntity.ok(response);
		} catch (UsernameAlreadyExistsException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username already exists");
		} catch (EmailAlreadyExistsException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email already registered");
		} catch (JsonParseException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to parse content");
		}
	}
	
	@PostMapping("/register/realtor")
	public ResponseEntity registerRealtor(@RequestPart("user") String jsonUser,
			@RequestPart("file") MultipartFile file) throws JsonMappingException, IOException {
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			Realtor realtor = objectMapper.readValue(jsonUser, Realtor.class);
			if (file == null) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("You must upload an image");
			}
			Photo photo = new Photo();
			photo.setContentType(file.getContentType());
			photo.setPicture(file.getBytes());
			realtor.addPhoto(photo);
			userService.createUser(realtor);
			Map<Object, Object> response = new HashMap<>();
			response.put("message", "User created");
			return ResponseEntity.ok(response);
		} catch (UsernameAlreadyExistsException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username already exists");
		} catch (EmailAlreadyExistsException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email already registered");
		} catch (JsonParseException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to parse content");
		}
	}
}
