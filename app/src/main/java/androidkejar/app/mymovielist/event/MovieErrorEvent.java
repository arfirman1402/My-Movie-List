package androidkejar.app.mymovielist.event;

public class MovieErrorEvent extends BaseEvent {
    public MovieErrorEvent(String message) {
        super(message);
    }
}