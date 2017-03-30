package androidkejar.app.mymovielist.event;

/**
 * Created by alodokter-it on 30/03/17.
 */

public class MovieDetailErrorEvent {
    private String message;

    public MovieDetailErrorEvent(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
