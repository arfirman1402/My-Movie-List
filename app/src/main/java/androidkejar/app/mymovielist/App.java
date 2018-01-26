package androidkejar.app.mymovielist;

import android.app.Application;

import org.greenrobot.eventbus.EventBus;

import androidkejar.app.mymovielist.restapi.RestAPIInterface;
import androidkejar.app.mymovielist.restapi.RestAPIURL;
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
        mRetrofit = new Retrofit.Builder()
                .baseUrl(RestAPIURL.getBaseUrl())
                .addConverterFactory(GsonConverterFactory.create())
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