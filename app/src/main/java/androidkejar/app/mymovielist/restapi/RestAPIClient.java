package androidkejar.app.mymovielist.restapi;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

class RestAPIClient {
    private static Retrofit retrofit = null;

    static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(RestAPIURL.getBaseUrl())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
