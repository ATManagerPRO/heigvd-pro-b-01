package com.heig.atmanager.userData;

import android.content.Context;
import android.icu.text.CaseMap;
import android.icu.text.SimpleDateFormat;
import android.os.AsyncTask;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.heig.atmanager.HomeFragment;
import com.heig.atmanager.Interval;
import com.heig.atmanager.MainActivity;
import com.heig.atmanager.R;
import com.heig.atmanager.folders.Folder;
import com.heig.atmanager.goals.Goal;
import com.heig.atmanager.goals.GoalTodo;
import com.heig.atmanager.taskLists.TaskList;
import com.heig.atmanager.tasks.Task;
import com.heig.atmanager.tasks.TaskFeedAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import androidx.recyclerview.widget.RecyclerView;

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

    // Http urls
    private String URL_FOLDERS_AND_TASKLISTS;
    private String URL_TODAY_TASKS;
    private String URL_TODAY_GOALS_TODO;
    private String URL_ALL_TASKS;
    private String URL_ALL_GOAL_TODOS;

    // Response codes
    private static final String RESPONSE_CODE      = "status code";
    private static final String RESPONSE_MESSAGE   = "message";
    private static final String RESPONSE_RESOURCE  = "resource";
    private static final int RESPONSE_CODE_SUCCESS = 200;


    // Data keywords
    // - Folders
    private static final String FOLDERS_KEY       = "folders";
    private static final String FOLDERS_ID        = "id";
    private static final String FOLDERS_TITLE     = "label";
    private static final String FOLDERS_TASKLISTS = "todolist";
    // - TaskLists
    private static final String TASKLISTS_KEY       = "todoLists";
    private static final String TASKLISTS_ID        = "id";
    private static final String TASKLISTS_TITLE     = "title";
    private static final String TASKLISTS_FOLDER_ID = "folder_id";
    // - Tasks
    private static final String TASK_KEY           = "todos";
    private static final String TASK_ID            = "id";
    private static final String TASK_TASKLIST_ID   = "todo_list_id";
    private static final String TASK_TITLE         = "title";
    private static final String TASK_DESCRIPTION   = "details";
    private static final String TASK_FAVORITE      = "favorite";
    private static final String TASK_DUE_DATE      = "dueDate";
    private static final String TASK_DONE_DATE     = "dateTimeDone";
    private static final String TASK_REMINDER_DATE = "reminderDateTime";
    // - Goals
    private static final String GOAL_KEY               = "goals";
    private static final String GOAL_ID                = "goal_id";
    private static final String GOAL_DUE_DATE          = "endDate_goal";
    private static final String GOAL_INTERVAL          = "interval_id";
    private static final String GOAL_INTERVAL_NUMBER   = "intervalValue";
    private static final String GOAL_TODO_CREATED_DATE = "created_at";
    private static final String GOAL_TODO_UPDATED_DATE = "updated_at";
    private static final String GOAL_LABEL             = "label";
    private static final String GOAL_QUANTITY          = "quantity";
    // - GoalTodo
    private static final String GOAL_TODO_KEY           = "goalTodos";
    private static final String GOAL_TODO_ID            = "id";
    private static final String GOAL_TODO_QUANTITY_DONE = "quantityDone";
    private static final String GOAL_TODO_DONE_DATE     = "dateTimeDone";
    private static final String GOAL_TODO_DUE_DATE      = "dueDate";

    // User reference by context
    private Context mainContext;
    private User user;

    public UserJsonParser(Context mainContext) {
        this.mainContext = mainContext;
        this.user        = MainActivity.getUser();
        Log.d(TAG, "updateUI: testingSignIn creating UserJsonParson");

        // Update urls with user's id
        String base_url = "https://atmanager.gollgot.app/api/v1/users/" + MainActivity.getUser().getUserId() ;
        URL_FOLDERS_AND_TASKLISTS = base_url + "/todolists";
        URL_TODAY_TASKS           = base_url + "/todos/today";
        URL_TODAY_GOALS_TODO      = base_url + "/goaltodos/today";
        URL_ALL_TASKS             = base_url + "/todos";
        URL_ALL_GOAL_TODOS        = base_url + "/goaltodos";

        Log.d(TAG, "updateUI: loading for : testingSignIn " + MainActivity.getUser().getUserId());
    }

    public void loadAllDataIntoUser(RequestQueue queue) {

        // Side drawer view
        Log.d(TAG, "loadAllDataIntoUser: loading folders and tasklists...");
        loadFoldersAndTasklists(queue);

        // Home Fragment view (today's activities)
        Log.d(TAG, "loadAllDataIntoUser: loading today's activity...");
        loadTodaysTasks(queue);
        loadTodaysGoalsTodo(queue);

        // Calendar view
        Log.d(TAG, "loadAllDataIntoUser: loading all tasks...");
        loadAllTasks(queue);

        // Goals view
        Log.d(TAG, "loadAllDataIntoUser: loading all goaltodos...");
        loadAllGoalTodos(queue);
    }

    /**
     * Loads the folders and tasklists of the user
     */
    private void loadFoldersAndTasklists(RequestQueue queue) {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,
                URL_FOLDERS_AND_TASKLISTS, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if(isRequestValid(response)) {
                        parseAndLoadTaskListsAndFolders(response.getJSONObject(RESPONSE_RESOURCE));
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
        };

        queue.add(request);
    }

    /**
     * Loads the tasks of the user for today
     */
    private void loadTodaysTasks(RequestQueue queue) {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,
                URL_TODAY_TASKS, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if(isRequestValid(response)) {
                        parseAndLoadTasks(response.getJSONObject(RESPONSE_RESOURCE));

                        // Update home fragment
                        /*((HomeFragment) ((MainActivity) mainContext).getSupportFragmentManager()
                                .findFragmentByTag(HomeFragment.FRAG_HOME_ID))
                                .updateHomeFragment();*/
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
                URL_TODAY_GOALS_TODO, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if(isRequestValid(response)) {
                        parseAndLoadGoalTodos(response.getJSONObject(RESPONSE_RESOURCE));

                        // Update home fragment
                        /*((HomeFragment) ((MainActivity) mainContext).getSupportFragmentManager()
                                .findFragmentByTag(HomeFragment.FRAG_HOME_ID))
                                .updateHomeFragment();*/

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
                URL_ALL_TASKS, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if(isRequestValid(response)) {
                        parseAndLoadTasks(response.getJSONObject(RESPONSE_RESOURCE));
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
                URL_ALL_GOAL_TODOS, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if(isRequestValid(response)) {
                        parseAndLoadGoalTodos(response.getJSONObject(RESPONSE_RESOURCE));
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
        JSONArray tasklists = response.getJSONArray(TASKLISTS_KEY);
        JSONArray folders   = response.getJSONArray(FOLDERS_KEY);

        // looping through all taskLists
        for (int i = 0; i < tasklists.length(); i++) {
            JSONObject c = tasklists.getJSONObject(i);

            // Tasklist data
            String id        = c.getString(TASKLISTS_ID);
            String title     = c.getString(TASKLISTS_TITLE);
            String folder_id = c.isNull(TASKLISTS_FOLDER_ID) ? "-1" : c.getString(TASKLISTS_FOLDER_ID);

            // Creating the tasklist and adding it to the current user
            TaskList tl = new TaskList(Long.parseLong(id), title, Long.parseLong(folder_id));
            user.addTaskList(tl);
        }

        // looping through all folders
        for (int i = 0; i < folders.length(); i++) {
            JSONObject c = folders.getJSONObject(i);
            // Folder data
            String id    = c.getString(FOLDERS_ID);
            String title = c.getString(FOLDERS_TITLE);
            ArrayList<TaskList> folder_tasklists = new ArrayList<>();

            JSONArray folder_tasklists_json = c.getJSONArray(FOLDERS_TASKLISTS);
            for (int j = 0; j < folder_tasklists_json.length(); j++) {
                JSONObject tasklist = folder_tasklists_json.getJSONObject(j);
                // Tasklist data
                String folder_tasklist_id    = tasklist.getString(TASKLISTS_ID);
                String folder_tasklist_title = tasklist.getString(TASKLISTS_TITLE);

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
        JSONArray tasks = response.getJSONArray(TASK_KEY);

        // looping through all tasks
        for (int i = 0; i < tasks.length(); i++) {
            JSONObject c = tasks.getJSONObject(i);

            // Task data
            long id                = c.getLong(TASK_ID);
            long taskListId        = c.getLong(TASK_TASKLIST_ID);
            String title           = c.getString(TASK_TITLE);
            String description     = c.getString(TASK_DESCRIPTION);
            boolean done           = c.isNull(TASK_DONE_DATE);
            boolean favorite       = c.getInt(TASK_FAVORITE) != 0;
            String dueDateStr      = c.getString(TASK_DUE_DATE);
            String doneDateStr     = c.getString(TASK_DONE_DATE);
            String reminderDateStr = c.getString(TASK_REMINDER_DATE);

            // Date parser
            SimpleDateFormat sdf  = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date dueDate          = dueDateStr.equals("null")      ? null : sdf.parse(dueDateStr);
            Date doneDate         = doneDateStr.equals("null")     ? null : sdf.parse(doneDateStr);
            Date reminderDate     = reminderDateStr.equals("null") ? null : sdf.parse(reminderDateStr);

            // Creating the task and adding it to the current user
            Task task = new Task(id, title, description, done, favorite, dueDate, doneDate, reminderDate);
            user.addTask(task);

            if(user.hasTaskList(taskListId)) {
                user.getTaskList(taskListId).addTask(task);
            }
        }
    }

    private void parseAndLoadGoalTodos(JSONObject response) throws JSONException, ParseException {
        // Getting JSON Array node
        JSONArray goalsTodo = response.getJSONArray(GOAL_TODO_KEY);

        // looping through all tasks
        for (int i = 0; i < goalsTodo.length(); i++) {
            JSONObject c = goalsTodo.getJSONObject(i);

            // Goal data
            long goal_id          = c.getLong(GOAL_ID);
            String unit           = c.getString(GOAL_LABEL);
            int quantity          = c.getInt(GOAL_QUANTITY);
            int intervalNumber    = c.getInt(GOAL_INTERVAL_NUMBER);
            long intervalId       = c.getLong(GOAL_INTERVAL);
            String createDateStr  = c.getString(GOAL_TODO_CREATED_DATE);
            String dueDateGoalStr = c.getString(GOAL_DUE_DATE);

            // GoalsTodo data
            long goalTodoId    = c.getLong(GOAL_TODO_ID);
            int quantityDone   = c.isNull(GOAL_TODO_QUANTITY_DONE) ? 0 : c.getInt(GOAL_TODO_QUANTITY_DONE);
            String doneDateStr = c.getString(GOAL_TODO_DONE_DATE);
            String dueDateStr  = c.getString(GOAL_TODO_DUE_DATE);

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
        int responseCode = response.getInt(RESPONSE_CODE);
        String responseMsg = response.getString(RESPONSE_MESSAGE);

        if(responseCode == RESPONSE_CODE_SUCCESS) {
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
                return Interval.HOUR;
            case 1:
                return Interval.DAY;
            case 2:
                return Interval.WEEK;
            case 3:
                return Interval.MONTH;
            case 4:
                return Interval.YEAR;
        }

        return null;
    }
}
