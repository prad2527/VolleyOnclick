package com.stp.volleyonclick;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.stp.volleyonclick.Utils.ApplicationConstant;
import com.stp.volleyonclick.Utils.Util;
import com.stp.volleyonclick.adapter.ListAdapterDetail;
import com.stp.volleyonclick.model.BasicResponce;
import com.stp.volleyonclick.model.InsideData;
import com.stp.volleyonclick.network.NetworkResponseHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    Map<String, String> params;
    String ApiType;
    protected RequestQueue mRequestQueue;
    List<String> areaRecsname = new ArrayList<>();
    List<InsideData> areaRecs = new ArrayList<>();

    ListAdapterDetail listAdapterDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.list);

        if (Util.isConnected(MainActivity.this)) {
            params = new HashMap<>();
            params.put("controller", "masterData");
            ApiType = "masterData";
            getSubmit(MainActivity.this);
        } else {
            Toast.makeText(MainActivity.this, getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
        }


    }

    private void getSubmit(Context mContext) {
        Log.e("Login", "@@URL" + ApplicationConstant.URL);
        mRequestQueue = Volley.newRequestQueue(mContext);
        NetworkResponseHelper<BasicResponce> myReq = new NetworkResponseHelper<>(
                Request.Method.POST,
                ApplicationConstant.URL,
                BasicResponce.class,
                params,
                createMyReqSuccessListener(),
                createMyReqErrorListener());

        myReq.setRetryPolicy(new DefaultRetryPolicy(
                ApplicationConstant.SOCKET_TIMEOUT,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        mRequestQueue.add(myReq);
    }


    protected Response.ErrorListener createMyReqErrorListener() {
        return error -> {

            Log.e("Not found data ", error.toString());
        };

    }

    private Response.Listener<BasicResponce> createMyReqSuccessListener() {
        return response -> {
            Log.d("Login Response", "@@@Ak response" + response);
            try {
                if (response.getSuccess().equals(ApplicationConstant.RESPONSE_SUCCESS)) {
                    Log.d("@@AK ApiType", ApiType);
                    if (ApiType.equals("masterData")) {
                        areaRecs.clear();
                        areaRecs.addAll(response.getDropdown().getAreaRecs());

                        // set adapter
                        listAdapterDetail= new ListAdapterDetail(areaRecs,getApplicationContext());
                        listView.setAdapter(listAdapterDetail);
                        listAdapterDetail.notifyDataSetChanged();


                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        };

    }
}