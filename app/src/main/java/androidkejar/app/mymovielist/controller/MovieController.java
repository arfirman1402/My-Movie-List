package androidkejar.app.mymovielist.controller;

import org.greenrobot.eventbus.EventBus;

import androidkejar.app.mymovielist.App;
import androidkejar.app.mymovielist.event.movie.MovieErrorEvent;
import androidkejar.app.mymovielist.event.movie.MovieEvent;
import androidkejar.app.mymovielist.event.moviedetail.MovieDetailErrorEvent;
import androidkejar.app.mymovielist.event.moviedetail.MovieDetailEvent;
import androidkejar.app.mymovielist.model.Movie;
import androidkejar.app.mymovielist.model.MovieResponse;
import androidkejar.app.mymovielist.restapi.RestAPIURL;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieController {
    private EventBus mEventBus = App.getInstance().getEventBus();

    public void getDataSearch(String query, int page) {
        Call<MovieResponse> movieResponseCall = App.getInstance().getApiService().getSearchMovies(RestAPIURL.getApiKey(), query, RestAPIURL.getLangSource(), page);
        movieResponseCall.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                mEventBus.post(new MovieEvent(response.message(), response.body()));
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                mEventBus.post(new MovieErrorEvent(t.getMessage()));
            }
        });
    }

    public void getMovies(String movieType, int page) {
        Call<MovieResponse> movieResponseCall = App.getInstance().getApiService().getMovies(movieType, RestAPIURL.getApiKey(), RestAPIURL.getLangSource(), page, RestAPIURL.getMoviesRegion());
        movieResponseCall.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                mEventBus.post(new MovieEvent(response.message(), response.body()));
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                mEventBus.post(new MovieErrorEvent(t.getMessage()));
            }
        });
    }

    public void getMovieDetail(int movieId) {
        Call<Movie> movieCall = App.getInstance().getApiService().getMovieDetails(movieId, RestAPIURL.getApiKey(), RestAPIURL.getLangSource(), RestAPIURL.getMovieAppendToResponse());
        movieCall.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                mEventBus.post(new MovieDetailEvent(response.message(), response.body()));
            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {
                mEventBus.post(new MovieDetailErrorEvent(t.getMessage()));
            }
        });
    }
}