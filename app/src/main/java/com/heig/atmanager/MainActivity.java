package com.heig.atmanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.heig.atmanager.addTaskGoal.AddTaskGoalActivity;
import com.heig.atmanager.calendar.CalendarFragment;
import com.heig.atmanager.goals.GoalsFragment;

public class MainActivity extends AppCompatActivity {
    public User dummyUser;

    private View fragmentContainer;

    private BottomNavigationView dock;

    private FloatingActionButton fab;
    private FloatingActionButton fabAddTask;
    private FloatingActionButton fabAddGoal;

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Drawer layout
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.drawer_open, R.string.drawer_close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // To get this variable from the fragments ((MainActivity)getActivity()).dummyUser
        dummyUser = new DummyData().initData();

        // First fragment to load : Home
        loadFragment(new HomeFragment());

    }

    // Menu icons are inflated just as they were with actionbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.top_navigation_menu, menu);
        View dockView = findViewById(R.id.dock_container);
        dock = dockView.findViewById(R.id.dock);

        fab = findViewById(R.id.fab);
        fabAddGoal = findViewById(R.id.fab_add_goal);
        fabAddTask = findViewById(R.id.fab_add_task);

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
                    case R.id.stats:
                        selectedFragment = new StatsFragment();
                        break;
                    default:
                        return false;
                }
                loadFragment(selectedFragment);
                return true;
            }
        });

        // Load fragment
        loadFragment(new HomeFragment());

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fab.setExpanded(!fab.isExpanded());
            }
        });

        fabAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), AddTaskGoalActivity.class);
                intent.putExtra("fragToLoad", R.id.frag_add_task);
                startActivity(intent);
            }
        });

        fabAddGoal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), AddTaskGoalActivity.class);
                intent.putExtra("fragToLoad", R.id.frag_add_goal);
                startActivity(intent);
            }
        });

        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // Drawer button
        if(drawerToggle.onOptionsItemSelected(item))
            return true;

        return super.onOptionsItemSelected(item);
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
