package com.heig.atmanager.addTaskGoal;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.heig.atmanager.userData.DummyData;
import com.heig.atmanager.R;
import com.heig.atmanager.userData.UserViewModel;

public class AddTaskGoalActivity extends AppCompatActivity  {

    UserViewModel dummyUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dummyUser = DummyData.getUser();
        setContentView(R.layout.activity_add_task_goal);

        int fragmentToShow = getIntent().getExtras().getInt("fragToLoad");

        switch (fragmentToShow){
            case R.id.frag_add_goal:
                loadFragment(new AddGoalFragment());
                break;
            case R.id.frag_add_task:
                loadFragment(new AddTaskFragment());
                break;
        }
    }

    private void loadFragment(Fragment fragment) {

        // Create new fragment and transaction
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);

        // Commit the transaction
        transaction.commit();
    }
}
