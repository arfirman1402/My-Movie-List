package androidkejar.app.mymovielist.event.moviedetail;

import androidkejar.app.mymovielist.event.BaseEvent;

public class MovieDetailErrorEvent extends BaseEvent {
    public MovieDetailErrorEvent(String message) {
        super(message);
    }
}