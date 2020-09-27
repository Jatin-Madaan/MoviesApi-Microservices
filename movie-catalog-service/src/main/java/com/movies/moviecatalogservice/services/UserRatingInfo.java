package com.movies.moviecatalogservice.services;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.movies.moviecatalogservice.models.Rating;
import com.movies.moviecatalogservice.models.UserRating;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

@Service
public class UserRatingInfo {
	@Autowired
	private RestTemplate restTemplate;

	@HystrixCommand(fallbackMethod = "getFallbackUserRating",
//			commandProperties = {
//					  @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000"),
//	                  @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "5"),
//	                  @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50"),
//	                  @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "5000"),
//			}
			
			// USING BULKHEAD PATTERN AND PROVIDING SEPERATE THREAD POOL
			threadPoolKey = "movieInfoPool",
            threadPoolProperties = {
                    @HystrixProperty(name = "coreSize", value = "20"),
                    @HystrixProperty(name = "maxQueueSize", value = "10")
	})
	public UserRating getUserRating(String userId) {
		return restTemplate.getForObject("http://ratings-data-service/ratingsdata/users/" + userId, UserRating.class);
	}
	
	public UserRating getFallbackUserRating(String userId) {
		UserRating ratings  = new UserRating();
		ratings.setUserRatings(Arrays.asList(
				new Rating("0", 0)
		));
		return ratings;
	}
}
