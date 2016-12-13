package androidkejar.app.mymovielist.restapi;

import androidkejar.app.mymovielist.model.Movie;
import androidkejar.app.mymovielist.model.MovieResponse;
import androidkejar.app.mymovielist.model.Person;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by alodokter-it on 12/12/16.
 */

public class RestAPIConnecting {
    String TAG = this.getClass().getSimpleName();
    RestAPIInterface apiService = RestAPIClient.getClient().create(RestAPIInterface.class);

    public void getDataNowPlaying(int pages, final RestAPIMovieResponseResult result) {
        Call<MovieResponse> call = apiService.getNowPlayingMovies(RestAPIURL.getApiKey(), RestAPIURL.getLangSource(), pages, RestAPIURL.getMoviesRegion());
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                result.resultData(response.message(), response.body());
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                result.errorResultData(t.getMessage());
            }
        });
    }

    public void getDataUpcoming(int pages, final RestAPIMovieResponseResult result) {
        Call<MovieResponse> call = apiService.getUpcomingMovies(RestAPIURL.getApiKey(), RestAPIURL.getLangSource(), pages, RestAPIURL.getMoviesRegion());
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                result.resultData(response.message(), response.body());
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                result.errorResultData(t.getMessage());
            }
        });
    }

    public void getDataPopular(int pages, final RestAPIMovieResponseResult result) {
        Call<MovieResponse> call = apiService.getPopularMovies(RestAPIURL.getApiKey(), RestAPIURL.getLangSource(), pages, RestAPIURL.getMoviesRegion());
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                result.resultData(response.message(), response.body());
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                result.errorResultData(t.getMessage());
            }
        });
    }

    public void getDataTopRated(int pages, final RestAPIMovieResponseResult result) {
        Call<MovieResponse> call = apiService.getTopRatedMovies(RestAPIURL.getApiKey(), RestAPIURL.getLangSource(), pages, RestAPIURL.getMoviesRegion());
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                result.resultData(response.message(), response.body());
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                result.errorResultData(t.getMessage());
            }
        });
    }

    public void getDataSearch(String query, int pages, final RestAPIMovieResponseResult result) {
        Call<MovieResponse> call = apiService.getSearchMovies(RestAPIURL.getApiKey(), query, RestAPIURL.getLangSource(), pages);
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                result.resultData(response.message(), response.body());
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                result.errorResultData(t.getMessage());
            }
        });
    }

    public void getDataMovie(int idMovies, final RestAPIMovieResult result) {
        Call<Movie> call = apiService.getMovieDetails(idMovies, RestAPIURL.getApiKey(), RestAPIURL.getLangSource(), RestAPIURL.getMovieAppendToResponse());
        call.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                result.resultData(response.message(), response.body());
            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {
                result.errorResultData(t.getMessage());
            }
        });
    }

    public void getDataPerson(int idPerson, final RestAPIPersonResult result) {
        Call<Person> call = apiService.getPersonDetails(idPerson, RestAPIURL.getApiKey(), RestAPIURL.getLangSource(), RestAPIURL.getPersonAppendToResponse());
        call.enqueue(new Callback<Person>() {
            @Override
            public void onResponse(Call<Person> call, Response<Person> response) {
                result.resultData(response.message(), response.body());
            }

            @Override
            public void onFailure(Call<Person> call, Throwable t) {
                result.errorResultData(t.getMessage());
            }
        });
    }


}
