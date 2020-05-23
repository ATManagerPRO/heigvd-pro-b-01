package com.heig.atmanager.userData;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.heig.atmanager.MainActivity;
import com.heig.atmanager.folders.Folder;
import com.heig.atmanager.goals.Goal;
import com.heig.atmanager.taskLists.TaskList;
import com.heig.atmanager.tasks.Task;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

public class PostRequests {

    static public void postFolder(Folder newFolder, Context context) {
        //post request to the server
        try {
            String URL = "https://atmanager.gollgot.app/api/v1/folders";
            JSONObject jsonBody = new JSONObject();


            jsonBody.put("label", newFolder.getName());

            JsonObjectRequest jsonObject = new JsonObjectRequest(Request.Method.POST, URL, jsonBody, new Response.Listener<JSONObject>() {
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


    static public void postTaskList(TaskList newTaskList, Context context) {
        //post request to the server
        try {
            String URL = "https://atmanager.gollgot.app/api/v1/todolists";
            JSONObject jsonBody = new JSONObject();


            jsonBody.put("title", newTaskList.getName());
            jsonBody.put("folder_id", newTaskList.getFolder_id());

            JsonObjectRequest jsonObject = new JsonObjectRequest(Request.Method.POST, URL, jsonBody, new Response.Listener<JSONObject>() {
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

    static public void postGoal(Goal newGoal, Context context) {
        //post request to the server
        try {
            String URL = "https://atmanager.gollgot.app/api/v1/goals";
            JSONObject jsonBody = new JSONObject();


            jsonBody.put("intervalLabel", newGoal.getInterval().toString());
            jsonBody.put("label", newGoal.getUnit());
            jsonBody.put("quantity", newGoal.getQuantity());
            jsonBody.put("intervalValue", newGoal.getIntervalNumber());
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            String date = df.format(newGoal.getDueDate());
            jsonBody.put("endDate", date);



            JsonObjectRequest jsonObject = new JsonObjectRequest(Request.Method.POST, URL, jsonBody, new Response.Listener<JSONObject>() {
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

    static public void postTask(Task newTask, Context context) {
        //post request to the server
        try {
            String URL = "https://atmanager.gollgot.app/api/v1/todos";
            JSONObject jsonBody = new JSONObject();

            android.icu.text.SimpleDateFormat sdf  = new android.icu.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            jsonBody.put("todo_list_id", newTask.getTasklist().getId());
            jsonBody.put("title", newTask.getTitle());
            jsonBody.put("details", newTask.getDescription());
            if(newTask.getDueDate() != null) {
                jsonBody.put("dueDate", sdf.format(newTask.getDueDate()));
            }
            /*
            if(newTask.getReminderDate() != null) {
                jsonBody.put("reminderDate", sdf.format(newTask.getReminderDate()));
            }*/
            jsonBody.put("tags", newTask.getTags().toString());

            JsonObjectRequest jsonObject = new JsonObjectRequest(Request.Method.POST, URL, jsonBody, new Response.Listener<JSONObject>() {
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

    static public void postTag(String newTag, Context context) {
        //post request to the server
        try {
            String URL = "https://atmanager.gollgot.app/api/v1/tags";
            JSONObject jsonBody = new JSONObject();

            jsonBody.put("label", newTag);

            JsonObjectRequest jsonObject = new JsonObjectRequest(Request.Method.POST, URL, jsonBody, new Response.Listener<JSONObject>() {
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