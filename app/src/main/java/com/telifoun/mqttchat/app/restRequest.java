package com.telifoun.mqttchat.app;


import com.telifoun.mqttchat.core.clbs.CallbackListener;
import com.telifoun.mqttchat.sdk.sdk;
import com.telifoun.mqttchat.volley.Response;
import com.telifoun.mqttchat.volley.VolleyError;
import com.telifoun.mqttchat.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.util.HashMap;

public class restRequest {

    public void request(int method, String url, HashMap<String,Object> data, final CallbackListener clb){

        JsonObjectRequest newReq = new JsonObjectRequest(method,
                url, (data!=null)?new JSONObject(data):null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if(response.getString("ok").equals("true")){
                                clb.onSuccess(response);
                            }else{
                                clb.onError(response.getString("error"));
                            }
                        } catch (Exception ex) {
                            ex.printStackTrace();
                            clb.onError(ex.getMessage());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        try {
                            if (volleyError.networkResponse == null) {
                                clb.onSuccess(volleyError.getMessage());
                            } else {
                                clb.onError(new JSONObject(new String(volleyError.networkResponse.data, "UTF-8")).getString("error"));
                            }
                        }catch (Exception ex) {
                            ex.printStackTrace();
                            clb.onError(ex.getMessage());
                        }

                    }
                });

        sdk.getInstance().addToRequestQueue(newReq);
    }

}
