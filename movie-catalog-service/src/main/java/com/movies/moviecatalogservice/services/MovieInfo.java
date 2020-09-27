package com.movies.moviecatalogservice.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.movies.moviecatalogservice.models.CatalogItem;
import com.movies.moviecatalogservice.models.Movie;
import com.movies.moviecatalogservice.models.Rating;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@Service
public class MovieInfo {
	@Autowired
	private RestTemplate restTemplate;

	@HystrixCommand(fallbackMethod = "getFallbackCatalogItem")
	public CatalogItem getCatalogItem(Rating rating) {
		Movie movie = restTemplate.getForObject("http://movie-info-service/movies/" + rating.getMovieId(), Movie.class);
		
		/* Movie movie = webClient.build()
		.get()
		.uri("http://localhost:8082/movies/" + rating.getMovieId())
		.retrieve()
		.bodyToMono(Movie.class)
		.block();
		*/
		
		return new CatalogItem(movie.getName(), movie.getDescription(), rating.getRating());
	}
	
	public CatalogItem getFallbackCatalogItem(Rating rating) {
		return new CatalogItem("Movie name not found", "", 0);
	}
}
