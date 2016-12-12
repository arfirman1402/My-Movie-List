package androidkejar.app.mymovielist.restapi;

import androidkejar.app.mymovielist.controller.MoviesURL;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by alodokter-it on 12/12/16.
 */

public class RestAPIClient {
    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(RestAPIURL.getBaseUrl())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
