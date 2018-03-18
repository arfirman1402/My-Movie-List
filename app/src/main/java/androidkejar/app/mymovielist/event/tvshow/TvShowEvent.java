package androidkejar.app.mymovielist.event.tvshow;

import androidkejar.app.mymovielist.event.BaseEvent;
import androidkejar.app.mymovielist.model.ResultResponse;
import androidkejar.app.mymovielist.model.TvShow;

public class TvShowEvent extends BaseEvent {
    private ResultResponse<TvShow> body;

    public TvShowEvent(String message, ResultResponse<TvShow> body) {
        super(message);
        this.body = body;
    }

    public ResultResponse<TvShow> getBody() {
        return body;
    }
}