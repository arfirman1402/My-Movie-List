package androidkejar.app.mymovielist.event;

/**
 * Created by alodokter-it on 30/03/17.
 */

public class MovieErrorEvent {
    private String message;

    public MovieErrorEvent(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
