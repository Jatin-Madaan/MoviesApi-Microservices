package com.movies.moviecatalogservice.resources;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import com.movies.moviecatalogservice.models.CatalogItem;
import com.movies.moviecatalogservice.models.Movie;
import com.movies.moviecatalogservice.models.Rating;
import com.movies.moviecatalogservice.models.UserRating;
import com.movies.moviecatalogservice.services.MovieInfo;
import com.movies.moviecatalogservice.services.UserRatingInfo;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private WebClient.Builder webClient;
	
	@Autowired
	MovieInfo movieInfo;
	
	@Autowired
	UserRatingInfo userRatingInfo;
	
	@RequestMapping("/{userId}")
	public List<CatalogItem> getCatalog(@PathVariable String userId){
		
		//getting url("localhost") from eureka server
		UserRating ratings = userRatingInfo.getUserRating(userId);
		return ratings.getUserRatings().stream().map(rating -> {
			return movieInfo.getCatalogItem(rating);
		}).collect(Collectors.toList());
		
	}
	
}
