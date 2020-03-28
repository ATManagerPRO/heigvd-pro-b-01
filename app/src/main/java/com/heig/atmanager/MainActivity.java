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

public class MainActivity extends AppCompatActivity {

    BottomNavigationView dock;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        View dockView = findViewById(R.id.dock_container);
        dock = dockView.findViewById(R.id.dock);
        dock.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;
                switch (item.getItemId()) {
                    case R.id.home:
                        selectedFragment = new HomeFragment();
                        break;
                    case R.id.calendar:
                        selectedFragment = new CalendarFragment();
                        break;
                    case R.id.goals:
                        selectedFragment = new GoalsFragment();
                        break;
                    case R.id.profile:
                        Intent intent = new Intent(getBaseContext(), ProfileActivity.class);
                        // Might need this to know which user is logged in
                        //String userID = "U12332";
                        //intent.putExtra(USER_ID, userID);
                        startActivity(intent);
                        finish();
                        overridePendingTransition(0, 0);
                        break;
                }

                if(selectedFragment != null)
                    loadFragment(selectedFragment);

                return true;
            }
        });

        // Load fragment
        Intent intent = getIntent();
        int fragmentId = intent.getIntExtra("FRAGMENT_ID", R.id.home);
        switch(fragmentId) {
            case R.id.home:
                dock.getMenu().findItem(R.id.home).setChecked(true);
                loadFragment(new HomeFragment());
                break;
            case R.id.calendar:
                dock.getMenu().findItem(R.id.calendar).setChecked(true);
                loadFragment(new CalendarFragment());
                break;
            case R.id.goals:
                dock.getMenu().findItem(R.id.goals).setChecked(true);
                loadFragment(new GoalsFragment());
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
