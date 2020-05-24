package com.heig.atmanager.userData;

import android.content.Context;
import android.util.Log;

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
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import static com.android.volley.VolleyLog.TAG;

public class PostRequests {

    private final static String TAG = "PostRequests";

    static public void postFolder(Folder newFolder, Context context) {
        //post request to the server
        try {
            String URL = RequestConstant.FOLDER_URL;
            JSONObject jsonBody = new JSONObject();


            jsonBody.put(RequestConstant.FOLDERS_LABEL, newFolder.getName());

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
            String URL = RequestConstant.TODOLISTS_URL;
            JSONObject jsonBody = new JSONObject();


            jsonBody.put(RequestConstant.TASK_TITLE, newTaskList.getName());
            jsonBody.put(RequestConstant.FOLDERS_ID, newTaskList.getFolderId());

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
            String URL = RequestConstant.GOAL_URL;
            JSONObject jsonBody = new JSONObject();


            jsonBody.put(RequestConstant.GOAL_INTERVAL_LABEL, newGoal.getInterval().toString());
            jsonBody.put(RequestConstant.GOAL_LABEL, newGoal.getUnit());
            jsonBody.put(RequestConstant.GOAL_QUANTITY, newGoal.getQuantity());
            jsonBody.put(RequestConstant.GOAL_INTERVAL_NUMBER, newGoal.getIntervalNumber());
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            String date = df.format(newGoal.getDueDate());
            jsonBody.put(RequestConstant.GOAL_END_DATE, date);
            jsonBody.put(RequestConstant.GOAL_BEGIN_DATE, df.format(Calendar.getInstance().getTime()));

            JsonObjectRequest jsonObject = new JsonObjectRequest(Request.Method.POST, URL, jsonBody, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d(TAG, "onErrorResponse: " + error.getMessage());
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

            android.icu.text.SimpleDateFormat sdf = new android.icu.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            jsonBody.put("todo_list_id", newTask.getTasklist().getId());
            jsonBody.put("title", newTask.getTitle());
            if (!newTask.getDescription().isEmpty()) {
                jsonBody.put("details", newTask.getDescription());
            }
            if (newTask.getDueDate() != null) {
                jsonBody.put("dueDate", sdf.format(newTask.getDueDate()));
            }
            /*
           if(newTask.getReminderDate() != null) {
                jsonBody.put(RequestConstant.TASK_REMINDER_DATE", sdf.format(newTask.getReminderDate()));
            }*/

            if (newTask.getTags().size() != 0) {
                jsonBody.put("tags", newTask.getTags().toString());
            }


            JsonObjectRequest jsonObject = new JsonObjectRequest(Request.Method.POST, URL, jsonBody, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d(TAG, "onErrorResponse: " + error.getMessage());
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
            String URL = RequestConstant.TAGS_URL;
            JSONObject jsonBody = new JSONObject();

            jsonBody.put(RequestConstant.TAG_LABEL, newTag);

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

    static public void postShared(long todoListId, String inviteeEmail, Context context){
        try{
            String URL = "https://atmanager.gollgot.app/api/v1/todolists/" + todoListId + "/share";

            JSONObject jsonBody = new JSONObject();

            jsonBody.put("invitedEmail", inviteeEmail);

            JsonObjectRequest jsonObject = new JsonObjectRequest(Request.Method.POST, URL, jsonBody, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.d(TAG, "onResponse: ");
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d(TAG, "onErrorResponse: " + error.getMessage());
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