package com.heig.atmanager;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.heig.atmanager.folders.Folder;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Author : Chau Ying Kot
 * Date   : 29.04.2020
 **/
public class AddFolderDiag extends DialogFragment {

    public AddFolderDiag() {
        // Required empty public constructor
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        final LayoutInflater inflater = requireActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.fragment_add_folder_diag, null);
        builder.setView(view).setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                final EditText folderName = view.findViewById(R.id.newFolderName);
                Folder newFolder = new Folder(folderName.getText().toString());

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
                    Volley.newRequestQueue(getContext()).add(jsonObject);


                } catch (JSONException e) {
                    e.printStackTrace();
                }

                ((MainActivity) AddFolderDiag.this.getActivity()).user.addFolder(new Folder(folderName.getText().toString()));
            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                AddFolderDiag.this.getDialog().cancel();
            }
        });


        return builder.create();

    }
}