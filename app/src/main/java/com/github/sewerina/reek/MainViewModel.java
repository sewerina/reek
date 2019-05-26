package com.github.sewerina.reek;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainViewModel extends ViewModel {
    private final RequestQueue mRequestQueue;
    private static final String ARRAY_REQUEST_TAG = "jsonArrayRequest";

    private final MutableLiveData<List<String>> mNames = new MutableLiveData<>();

    public LiveData<List<String>> getNames() {
        return mNames;
    }

    public MainViewModel(RequestQueue requestQueue) {
        mRequestQueue = requestQueue;
    }

    public void load() {
        String url = "https://apidata.mos.ru/v1/datasets/2601/rows?api_key=" + BuildConfig.MosApiKey;

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest
                (Request.Method.GET,
                        url,
                        null,
                        new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {
                                try {
                                    List<String> names = new ArrayList<>();
                                    for (int i = 0; i < response.length(); i++) {
                                        JSONObject obj = (JSONObject) response.get(i);
                                        JSONObject cells = (JSONObject) obj.get("Cells");
                                        String shortName = cells.get("ShortName").toString();
                                        names.add(shortName);
                                    }
                                    mNames.postValue(names);

//                                    mTextView.setText(builder.toString());
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        });
        jsonArrayRequest.setTag(ARRAY_REQUEST_TAG);
        mRequestQueue.add(jsonArrayRequest);

    }

    public void cancelRequests() {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(ARRAY_REQUEST_TAG);
        }
    }

}
