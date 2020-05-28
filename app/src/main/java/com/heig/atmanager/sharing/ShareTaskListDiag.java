package com.heig.atmanager.sharing;

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

import com.heig.atmanager.R;
import com.heig.atmanager.userData.PostRequests;

/**
 * Share list Dialog
 */
public class ShareTaskListDiag extends DialogFragment {

    private long taskListId;

    public ShareTaskListDiag(long taskListId) {
        this.taskListId = taskListId;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder  builder = new AlertDialog.Builder(getActivity());

        final LayoutInflater inflater = requireActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.fragment_shared, null);

        builder.setView(view).setPositiveButton(getString(R.string.confirm), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                final EditText inviteeEmail = view.findViewById(R.id.invitee_email);

                PostRequests.postShared(taskListId, inviteeEmail.getText().toString().trim(), getContext());
            }
        }).setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ShareTaskListDiag.this.getDialog().cancel();
            }
        });

        return builder.create();
    }
}
