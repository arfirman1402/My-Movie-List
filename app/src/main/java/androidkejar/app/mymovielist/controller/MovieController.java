package androidkejar.app.mymovielist.controller;

import org.greenrobot.eventbus.EventBus;

import java.net.HttpURLConnection;

import androidkejar.app.mymovielist.App;
import androidkejar.app.mymovielist.event.movie.MovieErrorEvent;
import androidkejar.app.mymovielist.event.movie.MovieEvent;
import androidkejar.app.mymovielist.event.moviedetail.MovieDetailErrorEvent;
import androidkejar.app.mymovielist.event.moviedetail.MovieDetailEvent;
import androidkejar.app.mymovielist.model.Movie;
import androidkejar.app.mymovielist.model.MovieResponse;
import androidkejar.app.mymovielist.restapi.RestApi;
import androidkejar.app.mymovielist.restapi.RestApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieController {
    private EventBus mEventBus = App.getInstance().getEventBus();
    private RestApiInterface service = App.getInstance().getRetrofitService(RestApiInterface.class);

    public void getDataSearch(String query, int page) {
        Call<MovieResponse> movieResponseCall = service.getSearchMovies(query, page, RestApi.getMoviesSearchOptional());
        movieResponseCall.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                if (response.code() == HttpURLConnection.HTTP_OK)
                    mEventBus.post(new MovieEvent(response.message(), response.body()));
                else mEventBus.post(new MovieErrorEvent(response.message()));
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                mEventBus.post(new MovieErrorEvent(t.getMessage()));
            }
        });
    }

    public void getMovies(String movieType, int page) {
        Call<MovieResponse> movieResponseCall = service.getMovies(movieType, page, RestApi.getMoviesOptional());
        movieResponseCall.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                if (response.code() == HttpURLConnection.HTTP_OK)
                    mEventBus.post(new MovieEvent(response.message(), response.body()));
                else mEventBus.post(new MovieErrorEvent(response.message()));
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                mEventBus.post(new MovieErrorEvent(t.getMessage()));
            }
        });
    }

    public void getMovieDetail(int movieId) {
        Call<Movie> movieCall = service.getMovieDetails(movieId, RestApi.getMovieDetailOptional());
        movieCall.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                if (response.code() == HttpURLConnection.HTTP_OK)
                    mEventBus.post(new MovieDetailEvent(response.message(), response.body()));
                else mEventBus.post(new MovieDetailErrorEvent(response.message()));
            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {
                mEventBus.post(new MovieDetailErrorEvent(t.getMessage()));
            }
        });
    }
}