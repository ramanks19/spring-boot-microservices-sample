package io.raman.movieinfoservice.resource;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.raman.movieinfoservice.models.Movie;

@RestController
@RequestMapping("/movies")
public class MovieResource {
    
    @RequestMapping("/{movieId}")
    public Movie getMovieInfo(@PathVariable("movieId") String movieId) {
        return new Movie(movieId, "Ethos", "Testing");
    }
}
