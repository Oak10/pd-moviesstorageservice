package com.ex.moviesstorageservice.services;


import com.ex.moviesstorageservice.entities.Account;
import com.ex.moviesstorageservice.entities.AccountRepository;
import com.ex.moviesstorageservice.entities.Movie;
import com.ex.moviesstorageservice.entities.MovieRepository;
import org.keycloak.TokenVerifier;
import org.keycloak.common.VerificationException;
import org.keycloak.representations.AccessToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
public class MovieService {
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    MovieRepository movieRepository;


    public List<Movie> getMovies(String token) throws VerificationException {
        Account account = accountRepository.findOneByMail(getMailFromToken(token));
        if (account == null)
            return new ArrayList<>();
        return account.getMovies();
    }

    public Movie createMovie(Movie movie, String token) throws VerificationException {
        String accountMail = getMailFromToken(token);
        Account account = accountRepository.findOneByMail(accountMail);
        if (account == null){
            account = new Account();
            account.setMail(accountMail);
        }
        accountRepository.save(account);
        movie.setAccount(account);
        movie.setId((long) -1);
        // TODO: find description
        return movieRepository.save(movie);
    }

    private static String getMailFromToken(String token) throws VerificationException {
        // TODO: Use spring security (spring-boot-starter-oauth2-resource-server)
        return TokenVerifier.create(
                token.replace("Bearer ", ""), AccessToken.class).getToken().getEmail();
    }


    public ResponseEntity deleteMovie(Long movieId, String token) throws VerificationException {
//        check permissions
        Account account = accountRepository.findOneByMail(getMailFromToken(token));
        Movie movie = movieRepository.findById(movieId).orElseThrow(
                ()-> new EntityNotFoundException(
                        String.format(
                                "Movie with id [%d] was not found!", movieId)));
        if (movie.getAccount() != account){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        movieRepository.delete(movie);

        return ResponseEntity.status(204).build();
    }

    public ResponseEntity<Movie> updateMovie(Long movieId, Movie movie, String token) throws VerificationException {
        // TODO: refactor
        Account account = accountRepository.findOneByMail(getMailFromToken(token));
        Movie movieBefore= movieRepository.findById(movieId).orElseThrow(
                ()-> new EntityNotFoundException(
                        String.format(
                                "Movie with id [%d] was not found!", movieId)));
        if (movieBefore.getAccount() != account){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        movie.setAccount(account);
        return new ResponseEntity<>(movieRepository.save(movie), HttpStatus.CREATED);
    }

    public ResponseEntity<Movie> getMovie(Long movieId, String token) throws VerificationException {
        Account account = accountRepository.findOneByMail(getMailFromToken(token));
        Movie movieBefore= movieRepository.findById(movieId).orElseThrow(
                ()-> new EntityNotFoundException(
                        String.format(
                                "Movie with id [%d] was not found!", movieId)));
        if (movieBefore.getAccount() != account){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        return new ResponseEntity<>(movieBefore, HttpStatus.CREATED);
    }
}
