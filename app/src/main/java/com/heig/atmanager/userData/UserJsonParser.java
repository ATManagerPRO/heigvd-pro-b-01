package com.heig.atmanager.userData;

import android.icu.text.CaseMap;
import android.icu.text.SimpleDateFormat;
import android.os.AsyncTask;
import android.util.Log;

import com.heig.atmanager.folders.Folder;
import com.heig.atmanager.taskLists.TaskList;
import com.heig.atmanager.tasks.Task;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * Author : Stéphane Bottin
 * Date   : 13.04.2020
 *
 * JSON Parser for user's data
 */
public class UserJsonParser extends AsyncTask<Void, Void, Void> {

    private static final String TAG = "UserJsonParser";

    // Http urls
    private static final String URL_FOLDERS_AND_TASKLISTS = "http://...";
    private static final String URL_TODAY_TASKS_GOALS     = "http://...";

    // Data keywords
    // - Folders
    private static final String FOLDERS_KEY   = "folders";
    private static final String FOLDERS_ID    = "folders_id";
    private static final String FOLDERS_TITLE = "folders_title";
    // - TaskLists
    private static final String TASKLISTS_KEY   = "tasklists";
    private static final String TASKLISTS_ID    = "tasklists_id";
    private static final String TASKLISTS_TITLE = "tasklists_title";
    // - Tasks
    private static final String TASK_KEY           = "task";
    private static final String TASK_ID            = "task_id";
    private static final String TASK_TITLE         = "task_title";
    private static final String TASK_DESCRIPTION   = "task_description";
    private static final String TASK_DONE          = "task_done";
    private static final String TASK_FAVORITE      = "task_favorite";
    private static final String TASK_DUE_DATE      = "task_due_date";
    private static final String TASK_DONE_DATE     = "task_done_date";
    private static final String TASK_REMINDER_DATE = "task_reminder_date";
    private static final String TASK_DIRECTORY_ID  = "task_directory_id";

    // - Goals


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        Log.d(TAG, "onPreExecute: downloading JSON...");
    }

    @Override
    protected Void doInBackground(Void... arg0) {

        // Side drawer data
        loadFoldersAndTasklists();
        // Home Fragment data
        loadTodaysTasksAndGoals();

        return null;
    }

    /**
     * Loads the folders and tasklists of the user
     */
    private void loadFoldersAndTasklists() {
        HttpHandler sh = new HttpHandler();
        // Making a request to url and getting response
        String jsonStr = sh.makeServiceCall(URL_FOLDERS_AND_TASKLISTS);

        Log.e(TAG, "Response from url: " + jsonStr);
        if (jsonStr != null) {
            try {
                JSONObject jsonObj = new JSONObject(jsonStr);

                // Getting JSON Array node
                JSONArray tasklists = jsonObj.getJSONArray(TASKLISTS_KEY);
                JSONArray folders   = jsonObj.getJSONArray(FOLDERS_KEY);

                // looping through all taskLists
                for (int i = 0; i < tasklists.length(); i++) {
                    JSONObject c = tasklists.getJSONObject(i);

                    // Tasklist data
                    String id    = c.getString(TASKLISTS_ID);
                    String title = c.getString(TASKLISTS_TITLE);

                    // Creating the tasklist and adding it to the current user
                    TaskList tl = new TaskList(Long.parseLong(id), title);
                    // TODO : user.addTaskList(tl)
                }

                // looping through all folders
                for (int i = 0; i < folders.length(); i++) {
                    JSONObject c = folders.getJSONObject(i);

                    // Folder data
                    String id    = c.getString(FOLDERS_ID);
                    String title = c.getString(FOLDERS_TITLE);
                    ArrayList<TaskList> folder_tasklists = new ArrayList<>();
                    JSONArray folder_tasklists_json = c.getJSONArray(TASKLISTS_KEY);
                    for (int j = 0; j < folder_tasklists_json.length(); i++) {
                        JSONObject tasklist = folder_tasklists_json.getJSONObject(i);

                        // Tasklist data
                        String folder_tasklist_id    = tasklist.getString(TASKLISTS_ID);
                        String folder_tasklist_title = tasklist.getString(TASKLISTS_TITLE);

                        // Creating the tasklist and adding it to the list
                        folder_tasklists.add(new TaskList(Long.parseLong(folder_tasklist_id),
                                folder_tasklist_title));
                    }

                    // Creating the folder and adding it to the user
                    Folder folder = new Folder(Long.parseLong(id), title, folder_tasklists);
                    // TODO : user.add(folder)
                }

            } catch (final JSONException e) {
                Log.e(TAG, "Json parsing error: " + e.getMessage());
            }
        } else {
            Log.e(TAG, "Couldn't get json from server.");
        }
    }

    /**
     * Loads the tasks and goals of the user for today
     */
    private void loadTodaysTasksAndGoals() {
        HttpHandler sh = new HttpHandler();
        // Making a request to url and getting response
        String jsonStr = sh.makeServiceCall(URL_TODAY_TASKS_GOALS);

        Log.e(TAG, "Response from url: " + jsonStr);
        if (jsonStr != null) {
            try {
                JSONObject jsonObj = new JSONObject(jsonStr);

                // Getting JSON Array node
                JSONArray tasks = jsonObj.getJSONArray(TASK_KEY);
                // TODO : JSONArray goals = jsonObj.getJSONArray(GOAL_KEY);

                // looping through all tasks
                for (int i = 0; i < tasks.length(); i++) {
                    JSONObject c = tasks.getJSONObject(i);

                    // Task data
                    long id                = c.getLong(TASK_ID);
                    String title           = c.getString(TASK_TITLE);
                    String description     = c.getString(TASK_DESCRIPTION);
                    boolean done           = c.getBoolean(TASK_DONE);
                    boolean favorite       = c.getBoolean(TASK_FAVORITE);
                    String dueDateStr      = c.getString(TASK_DUE_DATE);
                    String doneDateStr     = c.getString(TASK_DONE_DATE);
                    String reminderDateStr = c.getString(TASK_REMINDER_DATE);

                    // Date parser
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date dueDate         = sdf.parse(dueDateStr);
                    Date doneDate        = sdf.parse(doneDateStr);
                    Date reminderDate    = sdf.parse(reminderDateStr);

                    // Creating the task and adding it to the current user
                    Task task = new Task(id, title, description, done, favorite, dueDate, doneDate, reminderDate);
                    // TODO : user.addTask(task)
                }

                // TODO : looping through all goals
                //for (int i = 0; i < goals.length(); i++) {

                //}

            } catch (final JSONException e) {
                Log.e(TAG, "Json parsing error: " + e.getMessage());
            } catch (ParseException e) {
                Log.e(TAG, "Parsing error : " + e);
            }
        } else {
            Log.e(TAG, "Couldn't get json from server.");
        }
    }
}
