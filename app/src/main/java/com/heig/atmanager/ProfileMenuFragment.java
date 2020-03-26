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
 * Fragment that contains the profile menu allowing to navigate to different other windows
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
        tasks_title.setText(R.string.profile_button_tasks);

        View goals = (View) v.findViewById(R.id.goals_button);
        TextView goals_title = (TextView) goals.findViewById(R.id.profile_button_title);
        goals_title.setText(R.string.profile_button_goals);

        View stats = (View) v.findViewById(R.id.stats_button);
        TextView stats_title = (TextView) stats.findViewById(R.id.profile_button_title);
        stats_title.setText(R.string.profile_button_stats);

        View settings = (View) v.findViewById(R.id.settings_button);
        TextView settings_title = (TextView) settings.findViewById(R.id.profile_button_title);
        settings_title.setText(R.string.profile_button_settings);

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

        settings.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // switch fragment
            }
        });

        return v;
    }
}
