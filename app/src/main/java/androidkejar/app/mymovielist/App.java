package androidkejar.app.mymovielist;

import android.app.Application;

import com.facebook.stetho.Stetho;

import org.greenrobot.eventbus.EventBus;

import androidkejar.app.mymovielist.restapi.RestApi;
import androidkejar.app.mymovielist.utility.CommonFunction;
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

        declareStetho();

    }

    private void declareStetho() {
        Stetho.initializeWithDefaults(this);
    }

    private void createRetrofitClient() {
        Retrofit.Builder builder = new Retrofit.Builder();
        builder.baseUrl(RestApi.getBaseUrl());
        builder.addConverterFactory(GsonConverterFactory.create());

        builder.client(CommonFunction.createOkHttpClient());
        mRetrofit = builder.build();
    }

    private Retrofit getRetrofitClient() {
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