package androidkejar.app.mymovielist.restapi;

import androidkejar.app.mymovielist.model.Movie;
import androidkejar.app.mymovielist.model.MovieResponse;
import androidkejar.app.mymovielist.model.Person;

public interface RestAPI {
    interface MovieResponseResult {
        void resultData(String message, MovieResponse body);

        void errorResultData(String errorResponse);
    }

    interface MovieResult {
        void resultData(String message, Movie body);

        void errorResultData(String errorResponse);
    }

    interface PersonResult {
        void resultData(String message, Person body);

        void errorResultData(String errorResponse);
    }
}
