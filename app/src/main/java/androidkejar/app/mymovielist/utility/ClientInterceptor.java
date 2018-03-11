package androidkejar.app.mymovielist.utility;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

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
    private static final String TAG = "ClientInterceptor";

    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request original = chain.request();
        HttpUrl originalHttpUrl = original.url();

        Context context = App.getInstance().getApplicationContext();
        HttpUrl url = originalHttpUrl.newBuilder()
                .addQueryParameter(context.getString(R.string.param_api_key), context.getString(R.string.movie_api_key))
                .build();

        Request.Builder requestBuilder = original.newBuilder()
                .url(url);

        Request request = requestBuilder.build();
        Response response = chain.proceed(request);
        if (response.code() > 500) Log.d(TAG, "intercept: Server Error");
        else if (response.code() > 400) Log.d(TAG, "intercept: Client Error");
        return response;
    }
}
