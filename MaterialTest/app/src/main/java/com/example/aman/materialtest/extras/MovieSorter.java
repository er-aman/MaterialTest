package com.example.aman.materialtest.extras;

import com.example.aman.materialtest.pojo.Movie;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by aman on 9/6/16.
 */
public class MovieSorter {
    public void sortMoviesByName(ArrayList<Movie> movies){
        Collections.sort(movies, new Comparator<Movie>() {
            @Override
            public int compare(Movie lhs, Movie rhs) {
                return lhs.getTitle().compareTo(rhs.getTitle());
            }
        });
    }
    public void sortMoviesByDate(ArrayList<Movie> movies){
        Collections.sort(movies, new Comparator<Movie>() {
            @Override
            public int compare(Movie lhs, Movie rhs) {
                return lhs.getReleaseDateTheater().compareTo(rhs.getReleaseDateTheater());
            }
        });
    }
    public void sortMoviesByRatings(ArrayList<Movie> movies){
        Collections.sort(movies, new Comparator<Movie>() {
                @Override
                public int compare(Movie lhs, Movie rhs) {
                    return Integer.compare(rhs.getAudienceScore(),lhs.getAudienceScore());
                }
            });
    }
}
