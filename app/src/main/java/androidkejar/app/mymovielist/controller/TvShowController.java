package androidkejar.app.mymovielist.controller;

import android.support.annotation.NonNull;
import android.util.Log;

import org.greenrobot.eventbus.EventBus;

import java.net.HttpURLConnection;

import androidkejar.app.mymovielist.App;
import androidkejar.app.mymovielist.event.movie.MovieErrorEvent;
import androidkejar.app.mymovielist.event.movie.MovieEvent;
import androidkejar.app.mymovielist.event.tvshow.TvShowErrorEvent;
import androidkejar.app.mymovielist.event.tvshow.TvShowEvent;
import androidkejar.app.mymovielist.model.ResultResponse;
import androidkejar.app.mymovielist.model.TvShow;
import androidkejar.app.mymovielist.restapi.RestApi;
import androidkejar.app.mymovielist.restapi.RestApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by alodokter-arfirman on 18/03/18.
 */

public class TvShowController {
    private EventBus mEventBus = App.getInstance().getEventBus();
    private RestApiInterface service = App.getInstance().getRetrofitService(RestApiInterface.class);
    private static final String TAG = "TvShowController";

    public void getTvShows(String tvShowType, int page) {
        Call<ResultResponse<TvShow>> tvShowResponseCall = service.getTvShows(tvShowType, page, RestApi.getTvShowOptional());
        tvShowResponseCall.enqueue(new Callback<ResultResponse<TvShow>>() {
            @Override
            public void onResponse(@NonNull Call<ResultResponse<TvShow>> call, @NonNull Response<ResultResponse<TvShow>> response) {
                if (response.isSuccessful()) {
                    if (response.code() == HttpURLConnection.HTTP_OK)
                        mEventBus.post(new TvShowEvent(response.message(), response.body()));
                    else mEventBus.post(new TvShowErrorEvent(response.message()));
                } else mEventBus.post(new TvShowErrorEvent(response.message()));
            }

            @Override
            public void onFailure(@NonNull Call<ResultResponse<TvShow>> call, @NonNull Throwable t) {
                if (call.isCanceled()) {
                    Log.e(TAG, "onFailure: getMovies isCanceled", t);
                }
                mEventBus.post(new TvShowErrorEvent(t.getMessage()));
            }
        });
    }
}
