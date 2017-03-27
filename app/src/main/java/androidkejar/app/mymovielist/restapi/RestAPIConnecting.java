package androidkejar.app.mymovielist.restapi;

import androidkejar.app.mymovielist.model.Movie;
import androidkejar.app.mymovielist.model.MovieResponse;
import androidkejar.app.mymovielist.model.Person;
import androidkejar.app.mymovielist.utility.AppConstant;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RestAPIConnecting {
    private RestAPIInterface apiService = RestAPIClient.getClient().create(RestAPIInterface.class);

    public void getMovies(int type, int pages, final RestAPI.MovieResponseResult result) {
        Call<MovieResponse> call = apiService.getMovies(AppConstant.MOVIE_LIST_TYPE[type], RestAPIURL.getApiKey(), RestAPIURL.getLangSource(), pages, RestAPIURL.getMoviesRegion());
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

    public void getDataSearch(String query, int pages, final RestAPI.MovieResponseResult result) {
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

    public void getDataMovie(int idMovies, final RestAPI.MovieResult result) {
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

    public void getDataPerson(int idPerson, final RestAPI.PersonResult result) {
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