package androidkejar.app.mymovielist.event.person;

import androidkejar.app.mymovielist.event.BaseEvent;
import androidkejar.app.mymovielist.model.Person;
import androidkejar.app.mymovielist.model.ResultResponse;

public class PersonEvent extends BaseEvent {
    private ResultResponse<Person> body;

    public PersonEvent(String message, ResultResponse<Person> body) {
        super(message);
        this.body = body;
    }

    public ResultResponse<Person> getBody() {
        return body;
    }
}