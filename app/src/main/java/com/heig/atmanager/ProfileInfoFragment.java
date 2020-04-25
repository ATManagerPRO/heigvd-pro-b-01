package com.heig.atmanager;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * Author : Ferrari Teo
 * Date   : 20.03.2020
 *
 * Fragment for the profile view that will show the user infos
 */
public class ProfileInfoFragment extends Fragment {

    String last;
    String first;
    String nick;

    public ProfileInfoFragment() {
        // Required empty public constructor
    }

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

        //temporary for testing
        last = "Doe";
        first = "John";
        nick = "@J0hnD0e";

        //set user info texts
        TextView lastName = (TextView) v.findViewById(R.id.last_name);
        lastName.setText(last);

        TextView firstName = (TextView) v.findViewById(R.id.first_name);
        firstName.setText(first);

        TextView nickName = (TextView) v.findViewById(R.id.nickname);
        nickName.setText(nick);

        return v;
    }
}
