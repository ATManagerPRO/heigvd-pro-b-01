package com.heig.atmanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.heig.atmanager.addTaskGoal.AddTaskGoalActivity;
import com.heig.atmanager.calendar.CalendarFragment;
import com.heig.atmanager.folders.Folder;
import com.heig.atmanager.goals.GoalsFragment;
import com.heig.atmanager.goals.GoalsTodoFragment;
import com.heig.atmanager.taskLists.TaskList;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity1";
    
    public UserViewModel dummyUser;

    // For back button
    public static String previousFragment = null;

    private BottomNavigationView dock;

    private FloatingActionButton fab;
    private FloatingActionButton fabAddTask;
    private FloatingActionButton fabAddGoal;

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private NavigationView navView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // To get this variable from the fragments ((MainActivity)getActivity()).dummyUser
        dummyUser = DummyData.getUser();

        // Drawer layout
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        enableBackButton(false);
        navView = (NavigationView) findViewById(R.id.navView);
        updateDrawerItems(navView);

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

        switch(item.getItemId()) {
            // Back button
            case R.id.home:
            case R.id.homeAsUp:
            case android.R.id.home:
                onBackPressed();
                break;
        }

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

    private void updateDrawerItems(NavigationView navigationView) {

        // get menu from navigationView
        Menu menu = navigationView.getMenu();

        for(Folder folder : dummyUser.getFolders().getValue()) {
            Menu submenu = menu.addSubMenu(folder.getName());

            for(TaskList taskList : folder.getTaskLists())
                submenu.add(taskList.getName());

        }

        navigationView.invalidate();
    }


    /**
     * Enables the back button instead of the drawer menu
     *
     * @param enable : back button status
     */
    public void enableBackButton(boolean enable) {

        if(enable) {
            // Disable slide-to-open drawer navigation
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
            // Hide drawer icon
            drawerToggle.setDrawerIndicatorEnabled(false);
            // Show back arrow icon
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        } else {
            // Enable slide-to-open drawer navigation
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);

            // Create and sync drawer toggle
            drawerToggle = new ActionBarDrawerToggle (this, drawerLayout, R.string.drawer_open, R.string.drawer_close);
            drawerLayout.addDrawerListener(drawerToggle);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            drawerToggle.syncState();
        }
    }

    @Override
    public void onBackPressed() {
        displayPreviousFragment(previousFragment);
    }

    public void displayPreviousFragment(String previousFragment)
    {
        //creating fragment object
        Fragment fragment = null;

        //initializing the fragment object which is selected
        switch (previousFragment)
        {
            case HomeFragment.FRAG_HOME_ID :
                fragment = new HomeFragment();
                break;
            case GoalsFragment.FRAG_GOALS_ID :
                fragment = new GoalsFragment();
                break;
            case GoalsTodoFragment.FRAG_GOALS_TODO_ID :
                fragment = new GoalsTodoFragment();
                break;
            case CalendarFragment.FRAG_CALENDAR_ID :
                fragment = new CalendarFragment();
                break;
        }

        //replacing the fragment
        if (fragment != null) {
            loadFragment(fragment);
        }
    }
}
