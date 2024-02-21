package edu.baylor.propertypro.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.baylor.propertypro.data.RealtorRepository;
import edu.baylor.propertypro.data.ReviewRepository;
import edu.baylor.propertypro.domain.Realtor;
import edu.baylor.propertypro.domain.Review;
import edu.baylor.propertypro.service.serviceexceptions.RealtorAlreadyReviewedException;
import edu.baylor.propertypro.service.serviceexceptions.RealtorDoesNotExistException;

@Service
public class ReviewService {
	@Autowired
	private ReviewRepository reviewRepo;
	
	@Autowired
	private RealtorRepository realtorRepo;
	
	public Review createReview(Review review, long realtorId) throws RealtorAlreadyReviewedException, RealtorDoesNotExistException {
		Optional<Review> foundReview = reviewRepo.findFirstByReviewerIdAndRealtorId(
				review.getReviewer().getId(), realtorId);
		if (foundReview.isPresent()) {
			throw new RealtorAlreadyReviewedException("You've already reviewed this realtor!");
		}
		
		Optional<Realtor> optRealtor = realtorRepo.getRealtorByIdWithReviews(realtorId);
		if (!optRealtor.isPresent()) {
			throw new RealtorDoesNotExistException("That realtor doesn't exist!");
		}
		
		Realtor realtor = optRealtor.get();
		
		double ratingTotal = realtor.getReviews().stream().map(Review::getRating).collect(Collectors.summingInt(n -> n));
		double avgRating = (ratingTotal + review.getRating()) / (realtor.getReviews().size() + 1);
		
		realtor.setAverageRating(avgRating);
		realtorRepo.save(realtor);
		
		review.setRealtor(realtor);
		return reviewRepo.save(review);
	}
	
	public List<Review> getReviewsByRealtorId(long realtorId) {
		return reviewRepo.findByRealtorId(realtorId);
	}
}
