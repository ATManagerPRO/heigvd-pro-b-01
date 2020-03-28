package com.heig.atmanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;

/**
 * Author : Ferrari Teo
 * Date   : 18.03.2020
 *
 * Activity for the profile view that will take care of that user infos and statistics
 */
public class ProfileActivity extends AppCompatActivity {

    BottomNavigationView dock;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        View dockView = findViewById(R.id.dock_container);
        dock = dockView.findViewById(R.id.dock);
        dock.getMenu().findItem(R.id.profile).setChecked(true);
        dock.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int fragmentId = -1;
                switch (item.getItemId()) {
                    case R.id.home:
                        fragmentId = R.id.home;
                    case R.id.calendar:
                        if(fragmentId == -1) // Only update the id if it is not already set
                            fragmentId = R.id.calendar;
                    case R.id.goals:
                        if(fragmentId == -1) // Only update the id if it is not already set
                            fragmentId = R.id.goals;

                        Intent intent = new Intent(getBaseContext(), MainActivity.class);
                        intent.putExtra("FRAGMENT_ID", fragmentId);
                        startActivity(intent);
                        finish();
                        overridePendingTransition(0, 0);
                        break;
                    case R.id.profile:
                        // Do nothing
                        break;
                }

                return true;
            }
        });


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
