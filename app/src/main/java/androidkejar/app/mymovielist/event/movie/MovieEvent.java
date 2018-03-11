package androidkejar.app.mymovielist.event.movie;

import androidkejar.app.mymovielist.event.BaseEvent;
import androidkejar.app.mymovielist.model.Movie;
import androidkejar.app.mymovielist.model.ResultResponse;

public class MovieEvent extends BaseEvent {
    private ResultResponse<Movie> body;

    public MovieEvent(String message, ResultResponse<Movie> body) {
        super(message);
        this.body = body;
    }

    public ResultResponse<Movie> getBody() {
        return body;
    }
}