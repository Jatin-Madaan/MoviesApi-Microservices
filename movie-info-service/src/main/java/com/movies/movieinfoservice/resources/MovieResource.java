package com.movies.movieinfoservice.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.movies.movieinfoservice.models.Movie;
import com.movies.movieinfoservice.models.MovieSummary;

@RestController
@RequestMapping("/movies")
public class MovieResource {
	
	@Value("${api.key}")
	private String apiKey;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@RequestMapping("/{movieId}")
	public Movie getMovieInfo(@PathVariable String movieId) {
		
		MovieSummary movieSummary = restTemplate.getForObject("https://api.themoviedb.org/3/movie/" + movieId + "?api_key=" + apiKey, MovieSummary.class);
		System.out.println(movieSummary.getOverview());
		return new Movie(movieId, movieSummary.getTitle(), movieSummary.getOverview());
	}
}
