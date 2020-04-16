package com.heig.atmanager;

import android.os.AsyncTask;
import android.util.Log;

import com.heig.atmanager.folders.Folder;
import com.heig.atmanager.taskLists.TaskList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

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

    // Data keywords
    // - Folders
    private static final String FOLDERS_KEY   = "folders";
    private static final String FOLDERS_ID    = "folders_id";
    private static final String FOLDERS_TITLE = "folders_title";
    // - TaskLists
    private static final String TASKLISTS_KEY   = "tasklists";
    private static final String TASKLISTS_ID    = "tasklists_id";
    private static final String TASKLISTS_TITLE = "tasklists_title";


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        Log.d(TAG, "onPreExecute: downloading JSON...");
    }

    @Override
    protected Void doInBackground(Void... arg0) {

        // Side drawer data
        loadFoldersAndTasklists();

        return null;
    }

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
                    //TaskList tl = new TaskList(Long.parseLong(id), title);
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
                        //folder_tasklists.add(new TaskList(Long.parseLong(folder_tasklist_id),
                        //       folder_tasklist_title));
                    }

                    // Creating the folder and adding it to the user
                    //Folder folder = new Folder(Long.parseLong(id), title, folder_tasklists);
                    // TODO : user.add(folder)
                }

            } catch (final JSONException e) {
                Log.e(TAG, "Json parsing error: " + e.getMessage());
            }
        } else {
            Log.e(TAG, "Couldn't get json from server.");
        }
    }
}
