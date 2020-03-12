package com.heig.atmanager.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.textfield.TextInputEditText;
import com.heig.atmanager.R;


/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class AddTaskDescription extends Fragment {

    private String title;
    private String description;

    public AddTaskDescription() {
        // Required empty public constructor

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View result =  inflater.inflate(R.layout.fragment_add_task_description, container, false);

        TextInputEditText titleInput = result.findViewById(R.id.frag_add_taks_task_title);
        TextInputEditText descriptionInput = result.findViewById(R.id.frag_add_task_description);
        TextInputEditText tagsInput = result.findViewById(R.id.frag_add_task_tags);

        title = titleInput.getText().toString();
        description = descriptionInput.getText().toString();

        return result;
    }
}
