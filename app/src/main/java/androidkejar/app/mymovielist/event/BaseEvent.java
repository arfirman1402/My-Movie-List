package androidkejar.app.mymovielist.event;

public class BaseEvent {
    private String message;

    public BaseEvent(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}