package androidkejar.app.mymovielist.event;

import androidkejar.app.mymovielist.model.Movie;

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