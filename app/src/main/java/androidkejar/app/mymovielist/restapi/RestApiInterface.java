package androidkejar.app.mymovielist.restapi;

import java.util.HashMap;

import androidkejar.app.mymovielist.model.Movie;
import androidkejar.app.mymovielist.model.ResultResponse;
import androidkejar.app.mymovielist.model.TvShow;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface RestApiInterface {
    @GET("movie/{type}")
    Call<ResultResponse<Movie>> getMovies(@Path("type") String type, @Query("page") Integer page, @QueryMap HashMap<String, String> optionQuery);

    @GET("search/movie")
    Call<ResultResponse<Movie>> getSearchMovies(@Query("query") String query, @Query("page") Integer page, @QueryMap HashMap<String, String> optionQuery);

    @GET("tv/{type}")
    Call<ResultResponse<TvShow>> getTvShows(@Path("type") String type, @Query("page") Integer page, @QueryMap HashMap<String, String> optionQuery);

    @GET("movie/{id}")
    Call<Movie> getMovieDetails(@Path("id") Integer id, @QueryMap HashMap<String, String> optionQuery);
}