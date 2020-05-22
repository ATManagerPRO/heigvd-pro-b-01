package com.heig.atmanager.userData;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.heig.atmanager.MainActivity;
import com.heig.atmanager.tasks.Task;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class DeleteRequests {
    static public void deleteTask (Task oldTask, Context context) {
        //post request to the server
        try {
            String URL = RequestConstant.TASK_URL + oldTask.getId();
            JSONObject jsonBody = new JSONObject();

            jsonBody.put(RequestConstant.TODO_ID, oldTask.getId());

            JsonObjectRequest jsonObject = new JsonObjectRequest(Request.Method.DELETE, URL, jsonBody, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                }
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    final Map<String, String> headers = new HashMap<>();
                    headers.put("Authorization", "Bearer " + MainActivity.getUser().getBackEndToken());//put your token here
                    return headers;
                }
            };
            Volley.newRequestQueue(context).add(jsonObject);


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
