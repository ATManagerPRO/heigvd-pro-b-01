package com.heig.atmanager;

import android.view.View;

import java.util.ArrayList;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Author : Stephane
 * Date   : 16.03.2020
 * <p>
 * This class better be good.
 */
public class Utils {


    public static void setupGoalsRecyclerView(View v, RecyclerView rv, RecyclerView.Adapter adapter, RecyclerView.LayoutManager manager, ArrayList<Goal> goals) {

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        rv.setHasFixedSize(true);

        // use a (horizontal) linear layout manager
        manager = new LinearLayoutManager(v.getContext(),
                LinearLayoutManager.HORIZONTAL, false);
        rv.setLayoutManager(manager);

        // specify an adapter (see also next example)
        adapter = new GoalFeedAdapter(goals);
        rv.setAdapter(adapter);
    }
}
