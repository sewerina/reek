package com.github.sewerina.reek;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    private TextView mTextView;

    private RequestQueue mRequestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextView = findViewById(R.id.tv_text);

        mRequestQueue = Volley.newRequestQueue(this);
        String url = "https://apidata.mos.ru/v1/datasets/2601/rows?api_key=" + BuildConfig.MosApiKey;

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest
                (Request.Method.GET,
                        url,
                        null,
                        new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {
                                try {
                                    StringBuilder builder = new StringBuilder();
                                    for (int i = 0; i < response.length(); i++) {
                                        JSONObject obj = (JSONObject) response.get(i);
                                        JSONObject cells = (JSONObject) obj.get("Cells");
//                                        String shortName = cells.get("ShortName").toString();
                                        builder.append(cells.get("ShortName")).append("\n");
                                    }

//                                    JSONObject obj = (JSONObject) response.get(0);
//                                    JSONObject cells = (JSONObject) obj.get("Cells");
//                                    String shortName = cells.get("ShortName").toString();
                                    mTextView.setText(builder.toString());
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
        jsonArrayRequest.setTag("jsonArrayRequest");
        mRequestQueue.add(jsonArrayRequest);

    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll("jsonArrayRequest");
        }
    }
}
