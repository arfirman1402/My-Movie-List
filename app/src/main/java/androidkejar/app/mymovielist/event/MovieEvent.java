package androidkejar.app.mymovielist.event;

import androidkejar.app.mymovielist.model.MovieResponse;

public class MovieEvent {
    private String message;
    private MovieResponse body;

    public MovieEvent(String message, MovieResponse body) {
        this.message = message;
        this.body = body;
    }

    public String getMessage() {
        return message;
    }

    public MovieResponse getBody() {
        return body;
    }
}
