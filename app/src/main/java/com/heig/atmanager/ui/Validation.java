package com.heig.atmanager.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.PopupWindow;

import com.heig.atmanager.R;

public class Validation extends Fragment implements View.OnClickListener {

    public Validation() {
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
        View result =  inflater.inflate(R.layout.fragment_validation, container, false);

        ImageButton imageValidationButton = result.findViewById(R.id.frag_validation_button);

        imageValidationButton.setOnClickListener(this);

        return result;
    }

    @Override
    public void onClick(View v) {

    }
}
