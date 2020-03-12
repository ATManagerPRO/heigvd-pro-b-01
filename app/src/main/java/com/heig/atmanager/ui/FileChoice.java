package com.heig.atmanager.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.heig.atmanager.R;

import java.util.ArrayList;


public class FileChoice extends Fragment {


    ArrayList<String> tags = new ArrayList<>();

    public FileChoice() {
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
        View result = inflater.inflate(R.layout.fragment_file_choice, container, false);

        //TEMP
        tags.add("Bla");
        tags.add("Blop");

        final Spinner tagSpinner = result.findViewById(R.id.frag_file_choice_tag_spinner);

        ArrayAdapter tagAdapter = new ArrayAdapter(getActivity(), R.layout.support_simple_spinner_dropdown_item, tags);
        tagAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        tagSpinner.setAdapter(tagAdapter);

        return result;
    }
}
