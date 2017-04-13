package androidkejar.app.mymovielist.event;

import androidkejar.app.mymovielist.model.MovieResponse;

public class MovieEvent extends BaseEvent {
    private MovieResponse body;

    public MovieEvent(String message, MovieResponse body) {
        super(message);
        this.body = body;
    }

    public MovieResponse getBody() {
        return body;
    }
}