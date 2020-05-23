package com.heig.atmanager.addContent;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;
import com.heig.atmanager.MainActivity;
import com.heig.atmanager.userData.PostRequests;
import com.heig.atmanager.R;


public class AddTagsDiag extends DialogFragment {

    public AddTagsDiag() {
        // Required empty public constructor
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        final LayoutInflater inflater = requireActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.fragment_add_tags_diag, null);
        AlertDialog dialog = builder.setView(view)
                // Null, we override this later to change the close behaviour
                .setPositiveButton(getString(R.string.add), null)
                .setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        AddTagsDiag.this.getDialog().cancel();
                    }
                })
                .create();

        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                Button positiveBtn = ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE);

                positiveBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final EditText tagName = view.findViewById(R.id.newTagName);

                        TextInputLayout tagLayout = view.findViewById(R.id.newTagName_layout);

                        if (tagName.getText().toString().isEmpty()) {
                            tagLayout.setError(getString(R.string.input_missing));
                        } else {
                            PostRequests.postTag(tagName.getText().toString(), getContext());
                            MainActivity.getUser().addTag(tagName.getText().toString());
                            dismiss();
                        }
                    }
                });
            }
        });


        return dialog;

    }
}
