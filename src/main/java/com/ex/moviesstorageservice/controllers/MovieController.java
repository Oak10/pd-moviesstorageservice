package com.ex.moviesstorageservice.controllers;

import com.ex.moviesstorageservice.entities.Movie;
import com.ex.moviesstorageservice.services.MovieService;
import org.keycloak.common.VerificationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/v1")
public class MovieController {
    @Autowired
    MovieService movieService;

    @GetMapping(value="/movie")
    public ResponseEntity<List<Movie>> getMovies(@RequestHeader("Authorization") String token) throws VerificationException {
        return new ResponseEntity<>(movieService.getMovies(token), HttpStatus.OK);
    }

    @GetMapping(value = "movie/{movieId}")
    public ResponseEntity<Movie> getMovie(  @PathVariable Long movieId, @RequestHeader("Authorization") String token) throws VerificationException {
        return movieService.getMovie(movieId, token);
    }

    @PostMapping(value = "/movie")
    public ResponseEntity<Movie> postMovie(@RequestBody Movie movie, @RequestHeader("Authorization") String token) throws VerificationException {
        return new ResponseEntity<>(movieService.createMovie(movie, token), HttpStatus.CREATED);
    }

    @DeleteMapping(value = "movie/{movieId}")
    public ResponseEntity deleteMovie(  @PathVariable Long movieId, @RequestHeader("Authorization") String token) throws VerificationException {
        return movieService.deleteMovie(movieId, token);
    }

    @PutMapping(value = "movie/{movieId}")
    public ResponseEntity<Movie> updateMovie(  @PathVariable Long movieId,@RequestBody Movie movie, @RequestHeader("Authorization") String token) throws VerificationException {
        return movieService.updateMovie(movieId, movie, token);
    }

    // POST	Create 201
    // GET	Read 200
    // PUT	Update/Replace
    // DELETE 204
    //  PATCH	Update/Modify 200

}
