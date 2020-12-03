package com.telifoun.mqttchat.app;

import com.telifoun.mqttchat.core.Callback;
import com.telifoun.mqttchat.sdk.sdk;
import com.telifoun.mqttchat.volley.Response;
import com.telifoun.mqttchat.volley.VolleyError;
import com.telifoun.mqttchat.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.util.HashMap;

public class restRequest {

    public void request(int method, String url, HashMap<String,Object> data, final Callback clb){

        JsonObjectRequest newReq = new JsonObjectRequest(method,
                url, (data!=null)?new JSONObject(data):null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if(response.getString("ok").equals("true")){
                                clb.OK(response);
                            }else{
                                clb.KO(response.getString("error"));
                            }
                        } catch (Exception ex) {
                            ex.printStackTrace();
                            clb.KO(ex.getMessage());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        try {
                            if (volleyError.networkResponse == null) {
                                clb.KO(volleyError.getMessage());
                            } else {
                                clb.KO(new JSONObject(new String(volleyError.networkResponse.data, "UTF-8")).getString("error"));
                            }
                        }catch (Exception ex) {
                            ex.printStackTrace();
                            clb.KO(ex.getMessage());
                        }

                    }
                });

        sdk.getInstance().addToRequestQueue(newReq);
    }

}
