package androidkejar.app.mymovielist.controller.retrofit;

import android.content.Context;
import android.util.Log;

import java.util.List;

import androidkejar.app.mymovielist.controller.MoviesResult;
import androidkejar.app.mymovielist.controller.MoviesURL;
import androidkejar.app.mymovielist.model.ItemObject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by alodokter-it on 05/11/16.
 */

public class MoviesConnectingRetrofit {
    private static Retrofit retrofit;
    private String TAG = this.getClass().getSimpleName();

    public void getData(Context context, String url, final MoviesResult result) {
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        Call<ItemObject.ListOfMovie> call = apiService.getTopRatedMovies(MoviesURL.getApiKey());
        call.enqueue(new Callback<ItemObject.ListOfMovie>() {
            @Override
            public void onResponse(Call<ItemObject.ListOfMovie>call, Response<ItemObject.ListOfMovie> response) {
                List<ItemObject.ListOfMovie.MovieDetail> movies = response.body().getResults();
                Log.d(TAG, "Number of movies received: " + movies.size());
            }

            @Override
            public void onFailure(Call<ItemObject.ListOfMovie>call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
            }
        });
    }
}
