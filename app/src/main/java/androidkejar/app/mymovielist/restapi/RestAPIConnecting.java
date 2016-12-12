package androidkejar.app.mymovielist.restapi;

import androidkejar.app.mymovielist.pojo.ItemObject;
import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by alodokter-it on 12/12/16.
 */

public class RestAPIConnecting {
    String TAG = this.getClass().getSimpleName();
    RestAPIInterface apiService = RestAPIClient.getClient().create(RestAPIInterface.class);

    public void getDataTopRated(final RestAPIResult result) {
        /*Call<ItemObject.ListOfMovie> call = apiService.getTopRatedMovies(RestAPIURL.getApiKey(), RestAPIURL.getLangSource(), 1);
        call.enqueue(new Callback<ItemObject.ListOfMovie>() {
            @Override
            public void onResponse(Call<ItemObject.ListOfMovie> call, retrofit2.Response<ItemObject.ListOfMovie> response) {
                result.resultData(response.toString());
            }

            @Override
            public void onFailure(Call<ItemObject.ListOfMovie> call, Throwable t) {
                result.resultData(t.toString());
            }
        });*/
    }
}
