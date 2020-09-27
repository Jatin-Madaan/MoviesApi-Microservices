package com.movies.ratingdataservice.resources;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.movies.ratingdataservice.models.Rating;
import com.movies.ratingdataservice.models.UserRating;

@RestController
@RequestMapping("/ratingsdata")
public class ratingResource {

	@RequestMapping("/{movieId}")
	public Rating getRating(@PathVariable String movieId) {
		return new Rating(movieId, 4);
	}
	
	@RequestMapping("/users/{userId}")
	public UserRating getUserRating(@PathVariable String userId){
		List<Rating> ratings =  Arrays.asList(
				new Rating("tt6723592", 4),
				new Rating("tt8850222", 3)
		);
		
		UserRating userRating = new UserRating();
		userRating.setUserRatings(ratings);
		return userRating;
	}
}
