package androidkejar.app.mymovielist.controller.retrofit;

import androidkejar.app.mymovielist.pojo.ItemObject;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by alodokter-it on 11/12/16.
 */

public interface ApiInterface {
    @GET("movie/top_rated")
    Call<ItemObject.ListOfMovie> getTopRatedMovies(@Query("api_key") String apiKey);

    @GET("movie/{id}")
    Call<ItemObject.ListOfMovie> getMovieDetails(@Path("id") int id, @Query("api_key") String apiKey);
}