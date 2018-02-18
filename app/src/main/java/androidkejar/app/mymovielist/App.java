package androidkejar.app.mymovielist;

import android.app.Application;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;

import androidkejar.app.mymovielist.restapi.RestAPIInterface;
import androidkejar.app.mymovielist.restapi.RestApi;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class App extends Application {
    private static App sInstance;
    private Retrofit mRetrofit;
    private EventBus mEventBus;

    public App() {
        sInstance = this;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        createEventBus();
        createRetrofitClient();

    }

    private void createRetrofitClient() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        Interceptor clientInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();
                HttpUrl originalHttpUrl = original.url();

                HttpUrl url = originalHttpUrl.newBuilder()
                        .addQueryParameter("api_key", getApplicationContext().getString(R.string.movie_api_key))
                        .build();

                Request.Builder requestBuilder = original.newBuilder()
                        .url(url);

                Request request = requestBuilder.build();
                return chain.proceed(request);
            }
        };

        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(loggingInterceptor).addInterceptor(clientInterceptor).build();

        mRetrofit = new Retrofit.Builder()
                .baseUrl(RestApi.getBaseUrl())
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
    }

    public Retrofit getRetrofitClient() {
        return mRetrofit;
    }

    private void createEventBus() {
        mEventBus = EventBus.builder()
                .logNoSubscriberMessages(false)
                .sendNoSubscriberEvent(false)
                .addIndex(new MyEventBusIndex())
                .build();
    }

    public static App getInstance() {
        return sInstance;
    }

    public EventBus getEventBus() {
        return mEventBus;
    }

    public RestAPIInterface getApiService() {
        return getRetrofitClient().create(RestAPIInterface.class);
    }
}