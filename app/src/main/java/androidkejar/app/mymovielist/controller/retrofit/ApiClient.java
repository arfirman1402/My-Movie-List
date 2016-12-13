package androidkejar.app.mymovielist.controller.retrofit;

import androidkejar.app.mymovielist.restapi.RestAPIURL;
import retrofit2.Retrofit;

/**
 * Created by alodokter-it on 11/12/16.
 */

public class ApiClient {
    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(RestAPIURL.getBaseUrl())
                    .build();
        }
        return retrofit;
    }
}
