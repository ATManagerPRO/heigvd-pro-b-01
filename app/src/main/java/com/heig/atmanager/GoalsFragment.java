package com.heig.atmanager;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Author : Stéphane Bottin
 * Date   : 15.03.2020
 */
public class GoalsFragment extends Fragment {

    // Today's goal feed
    ArrayList<Goal> goalsToday; // user data
    private RecyclerView goalsTodayRecyclerView;
    private RecyclerView.Adapter goalsTodayAdapter;
    private RecyclerView.LayoutManager goalsTodaylayoutManager;

    // This week's goal feed
    /*ArrayList<Goal> goals; // user data
    private RecyclerView goalsRecyclerView;
    private RecyclerView.Adapter goalsAdapter;
    private RecyclerView.LayoutManager goalslayoutManager;

    // This month's goal feed
    ArrayList<Goal> goals; // user data
    private RecyclerView goalsRecyclerView;
    private RecyclerView.Adapter goalsAdapter;
    private RecyclerView.LayoutManager goalslayoutManager;

    // This year's goal feed
    ArrayList<Goal> goals; // user data
    private RecyclerView goalsRecyclerView;
    private RecyclerView.Adapter goalsAdapter;
    private RecyclerView.LayoutManager goalslayoutManager;*/
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_goals, container, false);

        // ----------- TEMP -----------
        //goalsToday = new ArrayList<>();
        //goalsToday.add(new Goal("SQUATS", 100, 70));
        //goalsToday.add(new Goal("KMS", 8, 3));

        // Today's goals setup
        goalsTodayRecyclerView = (RecyclerView) v.findViewById(R.id.goals_today_rv);
        Utils.setupGoalsRecyclerView(v, goalsTodayRecyclerView, goalsTodayAdapter, goalsTodaylayoutManager, goalsToday);

        return v;
    }

}
