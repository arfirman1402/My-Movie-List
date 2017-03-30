package androidkejar.app.mymovielist.event;

import androidkejar.app.mymovielist.model.Movie;
import androidkejar.app.mymovielist.model.MovieResponse;

/**
 * Created by alodokter-it on 30/03/17.
 */

public class MovieDetailEvent {
    private String message;
    private Movie body;

    public MovieDetailEvent(String message, Movie body) {
        this.message = message;
        this.body = body;
    }

    public String getMessage() {
        return message;
    }

    public Movie getBody() {
        return body;
    }
}
