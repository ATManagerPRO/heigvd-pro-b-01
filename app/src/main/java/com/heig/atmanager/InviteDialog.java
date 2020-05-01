package com.heig.atmanager;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

/**
 * Author : Chau Ying Kot
 * Date   : 01.05.2020
 **/
public class InviteDialog extends DialogFragment {

    public static InviteDialog newInsance(String userName, int taskId){
        InviteDialog inviteDialog = new InviteDialog();

        Bundle args = new Bundle();
        args.putString("userName", userName);
        args.putInt("taskId", taskId);

        inviteDialog.setArguments(args);

        return inviteDialog;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        String userName = getArguments().getString("userName");

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setMessage("You receive a task from " + userName)
        .setPositiveButton("Accept", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // Todo add task
                //((MainActivity)getActivity()).user.addTask();
            }
        })
        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                InviteDialog.this.getDialog().cancel();
            }
        });

        return builder.create();


    }
}
