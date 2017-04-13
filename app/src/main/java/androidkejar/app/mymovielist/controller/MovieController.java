package androidkejar.app.mymovielist.controller;

import org.greenrobot.eventbus.EventBus;

import androidkejar.app.mymovielist.App;
import androidkejar.app.mymovielist.event.MovieDetailErrorEvent;
import androidkejar.app.mymovielist.event.MovieDetailEvent;
import androidkejar.app.mymovielist.event.MovieErrorEvent;
import androidkejar.app.mymovielist.event.MovieEvent;
import androidkejar.app.mymovielist.model.Movie;
import androidkejar.app.mymovielist.model.MovieResponse;
import androidkejar.app.mymovielist.restapi.RestAPIURL;
import androidkejar.app.mymovielist.utility.AppConstant;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieController {
    private EventBus eventBus = App.getInstance().getEventBus();

    public void getDataSearch(String query, int page) {
        Call<MovieResponse> movieResponseCall = App.getInstance().getApiService().getSearchMovies(RestAPIURL.getApiKey(), query, RestAPIURL.getLangSource(), page);
        movieResponseCall.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                eventBus.post(new MovieEvent(response.message(), response.body()));
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                eventBus.post(new MovieErrorEvent(t.getMessage()));
            }
        });
    }

    public void getMovies(int type, int page) {
        Call<MovieResponse> movieResponseCall = App.getInstance().getApiService().getMovies(AppConstant.MOVIE_LIST_TYPE[type], RestAPIURL.getApiKey(), RestAPIURL.getLangSource(), page, RestAPIURL.getMoviesRegion());
        movieResponseCall.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                eventBus.post(new MovieEvent(response.message(), response.body()));
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                eventBus.post(new MovieErrorEvent(t.getMessage()));
            }
        });
    }

    public void getMovieDetail(int idMovies) {
        Call<Movie> movieCall = App.getInstance().getApiService().getMovieDetails(idMovies, RestAPIURL.getApiKey(), RestAPIURL.getLangSource(), RestAPIURL.getMovieAppendToResponse());
        movieCall.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                eventBus.post(new MovieDetailEvent(response.message(), response.body()));
            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {
                eventBus.post(new MovieDetailErrorEvent(t.getMessage()));
            }
        });
    }
}