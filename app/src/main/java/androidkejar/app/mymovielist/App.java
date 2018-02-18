package androidkejar.app.mymovielist;

import android.app.Application;

import org.greenrobot.eventbus.EventBus;

import androidkejar.app.mymovielist.restapi.RestApi;
import androidkejar.app.mymovielist.utility.ClientInterceptor;
import okhttp3.OkHttpClient;
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
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(RestApi.getBaseUrl())
                .addConverterFactory(GsonConverterFactory.create());

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.BODY);

        ClientInterceptor clientInterceptor = new ClientInterceptor();

        OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder();
        okHttpClient.addInterceptor(loggingInterceptor);
        okHttpClient.addInterceptor(clientInterceptor);

        builder.client(okHttpClient.build());
        mRetrofit = builder.build();
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

    public <T> T getRetrofitService(Class<T> serviceClass) {
        return getRetrofitClient().create(serviceClass);
    }
}