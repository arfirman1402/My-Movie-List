package androidkejar.app.mymovielist.controller;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

/**
 * Created by alodokter-it on 05/11/16.
 */

public class MoviesConnectingVolley {
    public void getData(Context context, String url, final MoviesResult result) {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                result.resultData(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                result.errorResultData(error.toString());
            }
        });
        requestQueue.add(stringRequest);
    }
}
