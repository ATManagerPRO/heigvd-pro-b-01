package com.heig.atmanager.userData;

import android.content.Context;
import android.icu.text.SimpleDateFormat;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.heig.atmanager.Interval;
import com.heig.atmanager.MainActivity;
import com.heig.atmanager.folders.Folder;
import com.heig.atmanager.goals.Goal;
import com.heig.atmanager.goals.GoalTodo;
import com.heig.atmanager.taskLists.TaskList;
import com.heig.atmanager.tasks.Task;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Author : Stéphane Bottin
 * Date   : 13.04.2020
 *
 * JSON Parser for user's data
 */
public class UserJsonParser {

    private static final String TAG = "UserJsonParser";

    // User identification
    private static final int USER_ID = 4;




    private String baseUserURL;

    // User reference by context
    private Context mainContext;
    private User user;

    public UserJsonParser(Context mainContext) {
        this.mainContext = mainContext;
        this.user        = MainActivity.getUser();
        Log.d(TAG, "updateUI: testingSignIn creating UserJsonParson");

        // Update urls with user's id
        baseUserURL = RequestConstant.USER_BASE_URL + "/"+ MainActivity.getUser().getUserId() ;

        Log.d(TAG, "updateUI: loading for : testingSignIn " + MainActivity.getUser().getUserId());
    }

    public void loadAllDataIntoUser(RequestQueue queue) {

        // Side drawer view
        Log.d(TAG, "loadAllDataIntoUser: loading folders and tasklists...");
        loadFoldersAndTasklists(queue);

    }

    /**
     * Loads the folders and tasklists of the user
     */
    private void loadFoldersAndTasklists(final RequestQueue queue) {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,
                baseUserURL + RequestConstant.TODOLISTS_EXTENSION, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                try {
                    if(isRequestValid(response)) {
                        parseAndLoadTaskListsAndFolders(response.getJSONObject(RequestConstant.RESPONSE_RESOURCE));

                        // Home Fragment view (today's activities)
                        Log.d(TAG, "loadAllDataIntoUser: loading today's activity...");
                        //loadTodaysTasks(queue); only ssems to load tasks for today twice causing bugs (the other time in loadAllTasks)
                        //loadTodaysGoalsTodo(queue);

                        // Calendar view
                        Log.d(TAG, "loadAllDataIntoUser: loading all tasks...");
                        loadAllTasks(queue);

                        // Goals view
                        Log.d(TAG, "loadAllDataIntoUser: loading all goaltodos...");
                        loadAllGoalTodos(queue);

                    }
                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                final Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + MainActivity.getUser().getBackEndToken());//put your token here
                return headers;
            }

            @Override
            public Priority getPriority() {
                return Priority.IMMEDIATE; //doesn't seem to do shit
            }

        };

        queue.add(request);
    }

    /**
     * Loads the tasks of the user for today
     */
    private void loadTodaysTasks(RequestQueue queue) {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,
                baseUserURL + RequestConstant.TODOS_EXTENSION + RequestConstant.TODAY_EXTENSION, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if(isRequestValid(response)) {
                        parseAndLoadTasks(response.getJSONObject(RequestConstant.RESPONSE_RESOURCE));
                    }
                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                } catch (ParseException e) {
                    Log.e(TAG, "Parsing error : " + e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                final Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + MainActivity.getUser().getBackEndToken());//put your token here
                return headers;
            }
        };

        queue.add(request);
    }

    /**
     * Loads the goals of the user for today
     */
    private void loadTodaysGoalsTodo(RequestQueue queue) {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,
                baseUserURL + RequestConstant.GOAL_TODOS_EXTENSION + RequestConstant.TODAY_EXTENSION, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if(isRequestValid(response)) {
                        parseAndLoadGoalTodos(response.getJSONObject(RequestConstant.RESPONSE_RESOURCE));
                    }
                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                } catch (ParseException e) {
                    Log.e(TAG, "Parsing error : " + e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                final Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + MainActivity.getUser().getBackEndToken());//put your token here
                return headers;
            }
        };

        queue.add(request);
    }


    /**
     * Loads all the tasks of the user
     */
    private void loadAllTasks(RequestQueue queue) {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,
                baseUserURL + RequestConstant.TODOS_EXTENSION, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if(isRequestValid(response)) {
                        parseAndLoadTasks(response.getJSONObject(RequestConstant.RESPONSE_RESOURCE));
                    }
                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                } catch (ParseException e) {
                    Log.e(TAG, "Parsing error : " + e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                final Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + MainActivity.getUser().getBackEndToken());//put your token here
                return headers;
            }
        };

        queue.add(request);
    }

    /**
     * Loads all the tasks of the user
     */
    private void loadAllGoalTodos(RequestQueue queue) {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,
                baseUserURL + RequestConstant.GOAL_TODOS_EXTENSION, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if(isRequestValid(response)) {
                        parseAndLoadGoalTodos(response.getJSONObject(RequestConstant.RESPONSE_RESOURCE));
                    }
                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                } catch (ParseException e) {
                    Log.e(TAG, "Parsing error : " + e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                final Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + MainActivity.getUser().getBackEndToken());//put your token here
                return headers;
            }
        };

        queue.add(request);
    }

    private void parseAndLoadTaskListsAndFolders(JSONObject response) throws JSONException {

        // Getting JSON Array node
        JSONArray tasklists = response.getJSONArray(RequestConstant.TASKLISTS_KEY);
        JSONArray folders   = response.getJSONArray(RequestConstant.FOLDERS_KEY);

        // looping through all taskLists
        for (int i = 0; i < tasklists.length(); i++) {
            JSONObject c = tasklists.getJSONObject(i);

            // Tasklist data
            String id        = c.getString(RequestConstant.ID);
            String title     = c.getString(RequestConstant.TASKLISTS_TITLE);
            String folder_id = c.isNull(RequestConstant.TASKLISTS_FOLDER_ID) ? "-1" : c.getString(RequestConstant.TASKLISTS_FOLDER_ID);

            // Creating the tasklist and adding it to the current user
            TaskList tl = new TaskList(Long.parseLong(id), title, Long.parseLong(folder_id));
            user.addTaskList(tl);
        }

        // looping through all folders
        for (int i = 0; i < folders.length(); i++) {
            JSONObject c = folders.getJSONObject(i);
            // Folder data
            String id    = c.getString(RequestConstant.ID);
            String title = c.getString(RequestConstant.FOLDERS_LABEL);
            ArrayList<TaskList> folder_tasklists = new ArrayList<>();

            JSONArray folder_tasklists_json = c.getJSONArray(RequestConstant.FOLDERS_TASKLISTS);
            for (int j = 0; j < folder_tasklists_json.length(); j++) {
                JSONObject tasklist = folder_tasklists_json.getJSONObject(j);
                // Tasklist data
                String folder_tasklist_id    = tasklist.getString(RequestConstant.ID);
                String folder_tasklist_title = tasklist.getString(RequestConstant.TASKLISTS_TITLE);

                // Creating the tasklist and adding it to the list
                folder_tasklists.add(new TaskList(Long.parseLong(folder_tasklist_id),
                        folder_tasklist_title, Long.parseLong(id)));
            }

            // Creating the folder and adding it to the user
            Folder folder = new Folder(Long.parseLong(id), title, folder_tasklists);
            user.addFolder(folder);
        }

        // Update the items
        ((MainActivity) mainContext).updateDrawerItems();
    }

    private void parseAndLoadTasks(JSONObject response) throws JSONException, ParseException {
        // Getting JSON Array node
        JSONArray tasks = response.getJSONArray(RequestConstant.TASK_KEY);

        // looping through all tasks
        for (int i = 0; i < tasks.length(); i++) {
            JSONObject c = tasks.getJSONObject(i);

            // Task data
            long id                = c.getLong(RequestConstant.ID);
            long taskListId        = c.getLong(RequestConstant.TASK_TASKLIST_ID);
            String title           = c.getString(RequestConstant.TASK_TITLE);
            String description     = c.getString(RequestConstant.TASK_DESCRIPTION).equals("null") ?
                    null : c.getString(RequestConstant.TASK_DESCRIPTION);
            boolean done           = !c.isNull(RequestConstant.TASK_DONE_DATE);
            boolean favorite       = c.getInt(RequestConstant.TASK_FAVORITE) != 0;
            boolean archived       = c.getInt(RequestConstant.TASK_ARCHIVED) != 0;
            String dueDateStr      = c.getString(RequestConstant.TASK_DUE_DATE);
            String doneDateStr     = c.getString(RequestConstant.TASK_DONE_DATE);
            String reminderDateStr = c.getString(RequestConstant.TASK_REMINDER_DATE);
            JSONArray tagsJson     = c.getJSONArray(RequestConstant.TAG_KEY);

            ArrayList<String> tags = new ArrayList<>();
            if(tagsJson.length() != 0) {

                for (int j = 0; j < tagsJson.length(); j++) {
                    tags.add(tagsJson.getJSONObject(j).getString(RequestConstant.TAG_LABEL));
                }
            }


            // Date parser
            SimpleDateFormat sdf  = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date dueDate          = dueDateStr.equals("null")      ? null : sdf.parse(dueDateStr);
            Date doneDate         = doneDateStr.equals("null")     ? null : sdf.parse(doneDateStr);
            Date reminderDate     = reminderDateStr.equals("null") ? null : sdf.parse(reminderDateStr);

            // Creating the task and adding it to the current user
            Task task = new Task(id, title, description, done, favorite, dueDate, doneDate, reminderDate, tags);
            task.setTasklist(user.getTaskList(taskListId));
            task.setArchived(archived);
            user.addTask(task);
        }
    }

    private void parseAndLoadGoalTodos(JSONObject response) throws JSONException, ParseException {
        // Getting JSON Array node
        JSONArray goalsTodo = response.getJSONArray(RequestConstant.GOAL_TODO_KEY);

        // looping through all tasks
        for (int i = 0; i < goalsTodo.length(); i++) {
            JSONObject c = goalsTodo.getJSONObject(i);

            // Goal data
            long goal_id          = c.getLong(RequestConstant.GOAL_ID);
            String unit           = c.getString(RequestConstant.GOAL_LABEL);
            int quantity          = c.getInt(RequestConstant.GOAL_QUANTITY);
            int intervalNumber    = c.getInt(RequestConstant.GOAL_INTERVAL_NUMBER);
            long intervalId       = c.getLong(RequestConstant.GOAL_INTERVAL);
            String createDateStr  = c.getString(RequestConstant.GOAL_TODO_CREATED_DATE);
            String dueDateGoalStr = c.getString(RequestConstant.GOAL_DUE_DATE);

            // GoalsTodo data
            long goalTodoId    = c.getLong(RequestConstant.ID);
            int quantityDone   = c.isNull(RequestConstant.GOAL_TODO_QUANTITY_DONE) ? 0 : c.getInt(RequestConstant.GOAL_TODO_QUANTITY_DONE);
            String doneDateStr = c.getString(RequestConstant.GOAL_TODO_DONE_DATE);
            String dueDateStr  = c.getString(RequestConstant.GOAL_TODO_DUE_DATE);

            // Date parser
            SimpleDateFormat sdf  = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            SimpleDateFormat sdf2  = new SimpleDateFormat("yyyy-MM-dd");
            Date dueDate          = dueDateStr.equals("null")    ? null : sdf.parse(dueDateStr);
            Date dueDateGoal      = dueDateStr.equals("null")    ? null : sdf2.parse(dueDateGoalStr);
            Date doneDate         = doneDateStr.equals("null")   ? null : sdf.parse(doneDateStr);
            Date createDate       = createDateStr.equals("null") ? null : sdf.parse(createDateStr);

            // Create the goal if it doesn't exist
            if(!user.hasGoal(goal_id)) {
                user.addGoal(
                        new Goal(goal_id, unit, quantity, intervalNumber,
                                getIntervalFromId(intervalId), dueDateGoal, createDate)
                );
            }

            // Create and link the goalTodo to the goal
            GoalTodo goalTodo = new GoalTodo(goalTodoId, goal_id, quantityDone, doneDate, dueDate);
            user.getGoal(goal_id).addGoalTodo(goalTodo);
        }
    }

    private boolean isRequestValid(JSONObject response) throws JSONException {
        // Get the response code
        int responseCode = response.getInt(RequestConstant.RESPONSE_CODE);
        String responseMsg = response.getString(RequestConstant.RESPONSE_MESSAGE);

        if(responseCode == RequestConstant.RESPONSE_CODE_SUCCESS) {
            return true;
        } else {
            Log.e(TAG, "Error " + responseMsg);
            return false;
        }
    }

    private Interval getIntervalFromId(long interval_id) {
        // TODO : Can probably do better than a switch
        switch((int) interval_id) {
            case 0:
                return Interval.DAY;
            case 1:
                return Interval.WEEK;
            case 2:
                return Interval.MONTH;
            case 3:
                return Interval.YEAR;
        }

        return null;
    }
}
