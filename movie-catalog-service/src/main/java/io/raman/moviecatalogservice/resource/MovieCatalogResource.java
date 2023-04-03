package io.raman.moviecatalogservice.resource;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import io.raman.moviecatalogservice.models.CatalogItem;
import io.raman.moviecatalogservice.models.Rating;
import io.raman.moviecatalogservice.models.Movie;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource{

    /*
     * Autowired is a consumer and Bean is a producer.
     */
    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping("/{userId}")
    public List<CatalogItem> getCatalog(@PathVariable("userId") String userId) {

        List<Rating> ratings = Arrays.asList(
            new Rating("1234", 4),
            new Rating("5678", 3)
        );

        return ratings.stream().map(rating -> {
            Movie movie = restTemplate.getForObject("http://localhost:8082/movies/" + rating.getMovieId(), Movie.class);
            return new CatalogItem(movie.getName(), "Test", rating.getRating());
        })
        .collect(Collectors.toList());


    }

}