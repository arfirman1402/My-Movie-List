package androidkejar.app.mymovielist.event;

public class MovieDetailErrorEvent {
    private String message;

    public MovieDetailErrorEvent(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}