package com.heig.atmanager.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.heig.atmanager.userData.PostRequests;

/**
 * Author : Chau Ying Kot
 * Date   : 01.05.2020
 **/
public class InviteDialog extends DialogFragment {

    public final static String TASKLIST_ID_KEY = "taskListId";
    public final static String USERNAME_KEY ="username";
    public final static String IS_EDITABLE_KEY  = "isEditable";

    public final static String SENDER_EMAIL_KEY = "senderEmail";

    public static InviteDialog newInsance(String userName, int taskListId, boolean isEditable, String email){
        InviteDialog inviteDialog = new InviteDialog();

        Bundle args = new Bundle();
        args.putString(USERNAME_KEY, userName);
        args.putInt(TASKLIST_ID_KEY, taskListId);
        args.putBoolean(IS_EDITABLE_KEY, isEditable);
        args.putString(SENDER_EMAIL_KEY, email);

        inviteDialog.setArguments(args);

        return inviteDialog;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        String userName = getArguments().getString(USERNAME_KEY);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setMessage("You receive a task from " + userName)
        .setPositiveButton("Accept", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // Todo add task
                //TaskList sharedList = fetch list with taskList id
                //((MainActivity)getActivity()).user.addASharedList(sharedList);
                PostRequests.postShared(getArguments().getInt(TASKLIST_ID_KEY), getArguments().getString(SENDER_EMAIL_KEY), getContext());
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
