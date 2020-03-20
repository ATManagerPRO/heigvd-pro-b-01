package com.heig.atmanager;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileMenuFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileMenuFragment extends Fragment {

    public ProfileMenuFragment() {
        // Required empty public constructor
    }

    public static ProfileMenuFragment newInstance() {
        ProfileMenuFragment fragment = new ProfileMenuFragment();
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
        View v = inflater.inflate(R.layout.fragment_profile_menu, container, false);

        //Modifiy all buttons templates titles
        View tasks = (View) v.findViewById(R.id.tasks_button);
        TextView tasks_title = (TextView) tasks.findViewById(R.id.profile_button_title);
        tasks_title.setText("Tasks");

        View goals = (View) v.findViewById(R.id.goals_button);
        TextView goals_title = (TextView) goals.findViewById(R.id.profile_button_title);
        goals_title.setText("Goals");

        View stats = (View) v.findViewById(R.id.stats_button);
        TextView stats_title = (TextView) stats.findViewById(R.id.profile_button_title);
        stats_title.setText("Stats");

        //set the onclicklisteners to switch to the correct activity/fragment
        tasks.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // switch activity
            }
        });

        goals.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // switch activity
            }
        });

        stats.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // switch fragment
            }
        });

        return v;
    }
}
