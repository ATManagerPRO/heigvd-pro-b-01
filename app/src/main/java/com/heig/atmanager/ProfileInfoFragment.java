package com.heig.atmanager;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileInfoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileInfoFragment extends Fragment {

    String last;
    String first;
    String nick;

    public ProfileInfoFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static ProfileInfoFragment newInstance() {
        ProfileInfoFragment fragment = new ProfileInfoFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =inflater.inflate(R.layout.fragment_profile_info, container, false);

        //temp
        last = "Doe";
        first = "John";
        nick = "@J0hnD0e";


        TextView lastName = (TextView) v.findViewById(R.id.last_name);
        lastName.setText(last);

        TextView firstName = (TextView) v.findViewById(R.id.first_name);
        firstName.setText(first);

        TextView nickName = (TextView) v.findViewById(R.id.nickname);
        nickName.setText(nick);

        return v;
    }
}
