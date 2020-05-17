package com.heig.atmanager;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.heig.atmanager.folders.Folder;
import com.heig.atmanager.taskLists.TaskList;


public class AddTasklistDiag extends DialogFragment {

    public AddTasklistDiag() {
        // Required empty public constructor
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        final LayoutInflater inflater = requireActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.fragment_add_tasklist_diag, null);

        final Spinner folderSpinner = view.findViewById(R.id.dropdwon_folder);

        final ArrayAdapter<Folder> spinnerAdapter = new FolderSpinnerAdapter(getActivity(),
                R.layout.support_simple_spinner_dropdown_item, ((MainActivity) getContext()).getUser().getFolders());
        spinnerAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        folderSpinner.setAdapter(spinnerAdapter);


        builder.setView(view).setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                final EditText taskListName = view.findViewById(R.id.newTaskListName);
                TaskList newTaskList = new TaskList(taskListName.getText().toString(), ((Folder) folderSpinner.getSelectedItem()).getId());
                PostRequests.postTaskList(newTaskList,getContext());
                ((MainActivity) AddTasklistDiag.this.getActivity()).user.addTaskList(newTaskList);
            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                AddTasklistDiag.this.getDialog().cancel();
            }
        });


        return builder.create();

    }

}
