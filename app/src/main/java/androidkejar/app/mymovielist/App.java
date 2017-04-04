package androidkejar.app.mymovielist;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.util.Log;

import org.greenrobot.eventbus.EventBus;

import androidkejar.app.mymovielist.restapi.RestAPIInterface;
import androidkejar.app.mymovielist.restapi.RestAPIURL;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class App extends Application implements Application.ActivityLifecycleCallbacks {
    private static App instance;
    private static Retrofit retrofit;
    private EventBus eventBus;

    public App() {
        instance = this;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("App", "onCreate: " + "Created App");

        createEventBus();
        createRetrofitClient();

        registerActivityLifecycleCallbacks(this);
    }

    private void createRetrofitClient() {
        retrofit = new Retrofit.Builder()
                .baseUrl(RestAPIURL.getBaseUrl())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public Retrofit getRetrofitClient() {
        return retrofit;
    }

    private void createEventBus() {
        eventBus = EventBus.builder().logNoSubscriberMessages(false).sendNoSubscriberEvent(false).build();
    }

    public static App getInstance() {
        return instance;
    }

    public EventBus getEventBus() {
        return eventBus;
    }

    public RestAPIInterface getApiService() {
        return getRetrofitClient().create(RestAPIInterface.class);
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        Log.d("App", "onActivityCreated: " + activity.getClass().getSimpleName());
    }

    @Override
    public void onActivityStarted(Activity activity) {
        Log.d("App", "onActivityStarted: " + activity.getClass().getSimpleName());
    }

    @Override
    public void onActivityResumed(Activity activity) {
        Log.d("App", "onActivityResumed: " + activity.getClass().getSimpleName());
    }

    @Override
    public void onActivityPaused(Activity activity) {
        Log.d("App", "onActivityPaused: " + activity.getClass().getSimpleName());
    }

    @Override
    public void onActivityStopped(Activity activity) {
        Log.d("App", "onActivityStopped: " + activity.getClass().getSimpleName());
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
        Log.d("App", "onActivitySaveInstanceState: " + activity.getClass().getSimpleName());
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        Log.d("App", "onActivityDestroyed: " + activity.getClass().getSimpleName());
    }
}
