package androidkejar.app.mymovielist.utility;

import java.io.IOException;

import androidkejar.app.mymovielist.App;
import androidkejar.app.mymovielist.R;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by alodokter-arfirman on 18/02/18.
 */

public class ClientInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();
        HttpUrl originalHttpUrl = original.url();

        HttpUrl url = originalHttpUrl.newBuilder()
                .addQueryParameter("api_key", App.getInstance().getApplicationContext().getString(R.string.movie_api_key))
                .build();

        Request.Builder requestBuilder = original.newBuilder()
                .url(url);

        Request request = requestBuilder.build();
        return chain.proceed(request);
    }
}
