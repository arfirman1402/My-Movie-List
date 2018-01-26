package androidkejar.app.mymovielist.event.moviedetail;

import androidkejar.app.mymovielist.event.BaseEvent;
import androidkejar.app.mymovielist.model.Movie;

public class MovieDetailEvent extends BaseEvent {
    private Movie body;

    public MovieDetailEvent(String message, Movie body) {
        super(message);
        this.body = body;
    }

    public Movie getBody() {
        return body;
    }
}