package androidkejar.app.mymovielist.event.person;

import androidkejar.app.mymovielist.event.BaseEvent;

public class PersonErrorEvent extends BaseEvent {
    public PersonErrorEvent(String message) {
        super(message);
    }
}