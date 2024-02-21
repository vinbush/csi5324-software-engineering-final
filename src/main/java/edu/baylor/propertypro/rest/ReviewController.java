package edu.baylor.propertypro.rest;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.baylor.propertypro.domain.Buyer;
import edu.baylor.propertypro.domain.Review;
import edu.baylor.propertypro.domain.User;
import edu.baylor.propertypro.dto.ReviewCreationDTO;
import edu.baylor.propertypro.dto.ReviewDTO;
import edu.baylor.propertypro.service.ReviewService;
import edu.baylor.propertypro.service.serviceexceptions.RealtorAlreadyReviewedException;
import edu.baylor.propertypro.service.serviceexceptions.RealtorDoesNotExistException;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {
	@Autowired
	private ReviewService reviewService;
	
	private ModelMapper modelMapper = new ModelMapper();
	
	@PostMapping
	public ResponseEntity<ReviewDTO> createReview(@RequestBody ReviewCreationDTO rawReview, @AuthenticationPrincipal User user) throws RealtorAlreadyReviewedException, RealtorDoesNotExistException {
		Review createdReview;
		Review review = modelMapper.map(rawReview, Review.class);
		review.setReviewer((Buyer)user);
		createdReview = reviewService.createReview(review, rawReview.getRealtorId());
		
		ReviewDTO returnReview = modelMapper.map(createdReview, ReviewDTO.class);
		
		return new ResponseEntity<ReviewDTO>(returnReview, HttpStatus.CREATED);
	}
	
	@GetMapping("/realtor/{id}")
	public ResponseEntity<List<ReviewDTO>> getReviewsByRealtor(@PathVariable("id") long id) {
		List<Review> reviews = reviewService.getReviewsByRealtorId(id);
		List<ReviewDTO> returnReviews = reviews.stream().map(r -> modelMapper.map(r, ReviewDTO.class)).collect(Collectors.toList());
		return new ResponseEntity<List<ReviewDTO>>(returnReviews, HttpStatus.OK); 
	}
}
