package androidkejar.app.mymovielist.event.tvshow;

import androidkejar.app.mymovielist.event.BaseEvent;

public class TvShowErrorEvent extends BaseEvent {
    public TvShowErrorEvent(String message) {
        super(message);
    }
}