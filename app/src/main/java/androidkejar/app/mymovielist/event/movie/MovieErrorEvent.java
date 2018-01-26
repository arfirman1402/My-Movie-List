package androidkejar.app.mymovielist.event.movie;

import androidkejar.app.mymovielist.event.BaseEvent;

public class MovieErrorEvent extends BaseEvent {
    public MovieErrorEvent(String message) {
        super(message);
    }
}