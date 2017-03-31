package androidkejar.app.mymovielist.event;

public class MovieErrorEvent {
    private String message;

    public MovieErrorEvent(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}