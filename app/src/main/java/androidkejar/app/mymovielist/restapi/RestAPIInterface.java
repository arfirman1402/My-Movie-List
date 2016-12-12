package androidkejar.app.mymovielist.restapi;

import androidkejar.app.mymovielist.model.Movie;
import androidkejar.app.mymovielist.model.MovieResponse;
import androidkejar.app.mymovielist.model.Person;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by alodokter-it on 12/12/16.
 */

public interface RestAPIInterface {
    @GET("movie/now_playing")
    Call<MovieResponse> getNowPlayingMovies(@Query("api_key") String apiKey, @Query("language") String language, @Query("page") int page);

    @GET("movie/upcoming")
    Call<MovieResponse> getUpcomingMovies(@Query("api_key") String apiKey, @Query("language") String language, @Query("page") int page);

    @GET("movie/popular")
    Call<MovieResponse> getPopularMovies(@Query("api_key") String apiKey, @Query("language") String language, @Query("page") int page);

    @GET("movie/top_rated")
    Call<MovieResponse> getTopRatedMovies(@Query("api_key") String apiKey, @Query("language") String language, @Query("page") int page);

    @GET("search/movie")
    Call<MovieResponse> getSearchMovies(@Query("api_key") String apiKey, @Query("query") String query, @Query("language") String language, @Query("page") int page);

    @GET("movie/{id}")
    Call<Movie> getMovieDetails(@Path("id") int id, @Query("api_key") String apiKey, @Query("language") String language, @Query("append_to_response") String appendToResponse);

    @GET("person/{id}")
    Call<Person> getPersonDetails(@Path("id") int id, @Query("api_key") String apiKey, @Query("language") String language, @Query("append_to_response") String appendToResponse);

}
