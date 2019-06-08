package com.github.sewerina.reek;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MapViewModel extends ViewModel {
    private static final String REQUEST_TAG_INDUSTRIAL_COMPANIES = "IndustrialCompaniesRequest";
    private static final String REQUEST_TAG_LARGE_WASTE_BINS = "LargeWasteBinsRequest";
    private final RequestQueue mRequestQueue;
    private final MutableLiveData<List<ReekMarker>> mIndustrialCompaniesMarkers = new MutableLiveData<>();
    private final MutableLiveData<List<ReekMarker>> mLargeWasteBinsMarkers = new MutableLiveData<>();

    public MapViewModel(RequestQueue requestQueue) {
        mRequestQueue = requestQueue;
    }

    public LiveData<List<ReekMarker>> getIndustrialCompaniesMarkers() {
        return mIndustrialCompaniesMarkers;
    }

    public LiveData<List<ReekMarker>> getLargeWasteBinsMarkers() {
        return mLargeWasteBinsMarkers;
    }

    public void loadIndustrialCompanies() {
        String url = "https://apidata.mos.ru/v1/datasets/2601/rows?api_key=" + BuildConfig.MosApiKey;

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            List<ReekMarker> reekObjects = new ArrayList<>();
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject obj = (JSONObject) response.get(i);
                                JSONObject cells = (JSONObject) obj.get("Cells");
                                String fullName = cells.get("FullName").toString();
                                JSONObject geoData = (JSONObject) cells.get("geoData");
                                JSONArray coordinates = (JSONArray) geoData.get("coordinates");
                                double latitude = coordinates.getDouble(1);
                                double longitude = coordinates.getDouble(0);
                                reekObjects.add(new ReekMarker(fullName, latitude, longitude));
                            }
                            mIndustrialCompaniesMarkers.postValue(reekObjects);
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
        jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(20000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        jsonArrayRequest.setTag(REQUEST_TAG_INDUSTRIAL_COMPANIES);
        mRequestQueue.add(jsonArrayRequest);

    }

    public void loadLargeWasteBins() {
        String url = "https://apidata.mos.ru/v1/datasets/2470/rows?api_key=" + BuildConfig.MosApiKey;

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            List<ReekMarker> reekObjects = new ArrayList<>();
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject obj = (JSONObject) response.get(i);
                                JSONObject cells = (JSONObject) obj.get("Cells");
                                String name = cells.get("Name").toString();
                                JSONObject geoData = (JSONObject) cells.get("geoData");
                                JSONArray coordinates = (JSONArray) geoData.get("coordinates");
                                double latitude = coordinates.getDouble(1);
                                double longitude = coordinates.getDouble(0);
                                reekObjects.add(new ReekMarker(name, latitude, longitude));
                            }
                            mLargeWasteBinsMarkers.postValue(reekObjects);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("loadLargeWasteBins", "onErrorResponse: " + error.getMessage());
                    }
                });

        jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(20000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        jsonArrayRequest.setTag(REQUEST_TAG_LARGE_WASTE_BINS);
        mRequestQueue.add(jsonArrayRequest);
    }

    public void cancelRequests() {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(REQUEST_TAG_INDUSTRIAL_COMPANIES);
            mRequestQueue.cancelAll(REQUEST_TAG_LARGE_WASTE_BINS);
        }
    }

}
