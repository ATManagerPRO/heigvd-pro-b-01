package com.heig.atmanager.userData;

import android.content.Context;
import android.icu.text.SimpleDateFormat;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.heig.atmanager.MainActivity;
import com.heig.atmanager.goals.GoalTodo;
import com.heig.atmanager.tasks.Task;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class PatchRequests {
    private static final String TAG = "PatchRequests";

    static public void patchTaskDoneDate(Task newTask, Context context) {
        //post request to the server
        try {
            String URL = "https://atmanager.gollgot.app/api/v1/todos/"+ newTask.getId() +"/done";
            JSONObject jsonBody = new JSONObject();

            SimpleDateFormat sdf  = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            jsonBody.put("todo_id", newTask.getId());
            jsonBody.put("done",    newTask.getDoneDate() == null ? JSONObject.NULL : sdf.format(newTask.getDoneDate()));

            JsonObjectRequest jsonObject = new JsonObjectRequest(Request.Method.PATCH, URL, jsonBody, new Response.Listener<JSONObject>() {
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

    static public void patchTaskFavorite(Task newTask, Context context) {
        //post request to the server
        try {
            String URL = "https://atmanager.gollgot.app/api/v1/todos/"+ newTask.getId() +"/favorite";
            JSONObject jsonBody = new JSONObject();

            jsonBody.put("todo_id", newTask.getId());
            jsonBody.put("favorite", newTask.isFavorite());

            JsonObjectRequest jsonObject = new JsonObjectRequest(Request.Method.PATCH, URL, jsonBody, new Response.Listener<JSONObject>() {
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

    static public void patchGoalTodoDoneDate(GoalTodo newGoalTodo, Context context) {
        //post request to the server
        try {
            String URL = "https://atmanager.gollgot.app/api/v1/todos/"+ newGoalTodo.getGoalTodoId() +"/done";
            JSONObject jsonBody = new JSONObject();

            android.icu.text.SimpleDateFormat sdf  = new android.icu.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            jsonBody.put("todo_id", newGoalTodo.getGoalTodoId());
            if(newGoalTodo.getDoneDate() != null) {
                jsonBody.put("done", sdf.format(newGoalTodo.getDoneDate()));
            }else{
                jsonBody.put("done", null);
            }

            JsonObjectRequest jsonObject = new JsonObjectRequest(Request.Method.PATCH, URL, jsonBody, new Response.Listener<JSONObject>() {
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
