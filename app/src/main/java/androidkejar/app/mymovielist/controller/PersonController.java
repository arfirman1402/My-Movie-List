package androidkejar.app.mymovielist.controller;

import android.support.annotation.NonNull;
import android.util.Log;

import org.greenrobot.eventbus.EventBus;

import java.net.HttpURLConnection;

import androidkejar.app.mymovielist.App;
import androidkejar.app.mymovielist.event.person.PersonErrorEvent;
import androidkejar.app.mymovielist.event.person.PersonEvent;
import androidkejar.app.mymovielist.model.Person;
import androidkejar.app.mymovielist.model.ResultResponse;
import androidkejar.app.mymovielist.restapi.RestApi;
import androidkejar.app.mymovielist.restapi.RestApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by alodokter-arfirman on 18/03/18.
 */

public class PersonController {
    private EventBus mEventBus = App.getInstance().getEventBus();
    private RestApiInterface service = App.getInstance().getRetrofitService(RestApiInterface.class);
    private static final String TAG = "PersonController";


    public void getPersons(String personType, int page) {
        Call<ResultResponse<Person>> personResponseCall = service.getPersons(personType, page, RestApi.getPersonOptional());
        personResponseCall.enqueue(new Callback<ResultResponse<Person>>() {
            @Override
            public void onResponse(@NonNull Call<ResultResponse<Person>> call, @NonNull Response<ResultResponse<Person>> response) {
                if (response.isSuccessful()) {
                    if (response.code() == HttpURLConnection.HTTP_OK)
                        mEventBus.post(new PersonEvent(response.message(), response.body()));
                    else mEventBus.post(new PersonErrorEvent(response.message()));
                } else mEventBus.post(new PersonErrorEvent(response.message()));
            }

            @Override
            public void onFailure(@NonNull Call<ResultResponse<Person>> call, @NonNull Throwable t) {
                if (call.isCanceled()) {
                    Log.e(TAG, "onFailure: getPersons isCanceled", t);
                }
                mEventBus.post(new PersonErrorEvent(t.getMessage()));
            }
        });
    }
}