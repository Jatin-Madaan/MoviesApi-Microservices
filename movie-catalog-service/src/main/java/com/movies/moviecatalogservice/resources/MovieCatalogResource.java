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

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private WebClient.Builder webClient;
	
	@RequestMapping("/{userId}")
	public List<CatalogItem> getCatalog(@PathVariable String userId){
		
		//getting url("localhost") from eureka server
		UserRating ratings = restTemplate.getForObject("http://ratings-data-service/ratingsdata/users/" + userId, UserRating.class);
		return ratings.getUserRatings().stream().map(rating -> {
			Movie movie = restTemplate.getForObject("http://movie-info-service/movies/" + rating.getMovieId(), Movie.class);
			
			/* Movie movie = webClient.build()
			.get()
			.uri("http://localhost:8082/movies/" + rating.getMovieId())
			.retrieve()
			.bodyToMono(Movie.class)
			.block();
			*/
			
			return new CatalogItem(movie.getName(), "desc", rating.getRating());
		}).collect(Collectors.toList());
		
	}
}
