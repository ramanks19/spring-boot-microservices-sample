package io.raman.moviecatalogservice.resource;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import io.raman.moviecatalogservice.models.CatalogItem;
import io.raman.moviecatalogservice.models.Rating;
import io.raman.moviecatalogservice.models.UserRating;
import io.raman.moviecatalogservice.services.MovieInfo;
import io.raman.moviecatalogservice.services.UserRatingInfo;
import io.raman.moviecatalogservice.models.Movie;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource{

    /*
     * Autowired is a consumer and Bean is a producer.
     */
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private MovieInfo movieInfo;

    @Autowired
    private UserRatingInfo userRatingInfo;

/*  This approach of having one fallback method for all the services is not ideal as it doesn't provide
    any information as to for which service the circuit has tripped. So, as a next step we will break the
    single getCatalog method into two method one for each service to have more granualrity. Thus we can 
    have one fallback method for each of the method.

    @RequestMapping("/{userId}")
    @HystrixCommand(fallbackMethod = "getFallbackCatalog")
    public List<CatalogItem> getCatalog(@PathVariable("userId") String userId) {

        UserRating ratings = restTemplate.getForObject("http://ratings-data-service/ratingsdata/user/" + userId, UserRating.class);

        return ratings.getUserRating().stream().map(rating -> {
            Movie movie = restTemplate.getForObject("http://movie-info-service/movies/" + rating.getMovieId(), Movie.class);
            return new CatalogItem(movie.getName(), "Test", rating.getRating());
        })
        .collect(Collectors.toList());
    }

    public List<CatalogItem> getFallbackCatalog(@PathVariable("userId") String userId) {
        return Arrays.asList(new CatalogItem("No movie", "", 0));
    }
*/
    @RequestMapping("/{userId}")
    public List<CatalogItem> getCatalog(@PathVariable("userId") String userId) {
        UserRating userRating = userRatingInfo.getUserRating(userId);

        return userRating.getUserRating().stream()
                         .map(rating -> movieInfo.getCatalogItem(rating))
                         .collect(Collectors.toList());
    }

}