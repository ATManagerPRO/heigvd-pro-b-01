package com.heig.atmanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;


import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.heig.atmanager.addTaskGoal.AddGoalFragment;
import com.heig.atmanager.addTaskGoal.AddTaskFragment;
import com.heig.atmanager.calendar.CalendarFragment;
import com.heig.atmanager.goals.GoalsFragment;
import com.heig.atmanager.stats.StatsFragment;
import com.heig.atmanager.taskLists.TaskList;
import com.heig.atmanager.taskLists.TaskListFragment;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static User user;

    private static final String TAG = "MainActivity";

    private BottomNavigationView dock;

    private FloatingActionButton fab;
    private FloatingActionButton fabAddTask;
    private FloatingActionButton fabAddGoal;

    // Drawer
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    // Navigation view (drawer)
    private NavigationView navView;
    private ExpandableListView expandableListView;
    private ExpandableListAdapter adapter;


    private GoogleSignInClient mGoogleSignInClient;

    // JSON Parser
    private UserJsonParser jsonParser;
    private RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        queue = Volley.newRequestQueue(this);

        GoogleSignInOptions gso =
                new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestIdToken(getString(R.string.server_client_id))
                        .requestEmail()
                        .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);


        // To get this variable from the fragments ((MainActivity)getActivity()).dummyUser
        user = new User("Joe", "GoogleToken");

        fab = findViewById(R.id.fab);
        fabAddGoal = findViewById(R.id.fab_add_goal);
        fabAddTask = findViewById(R.id.fab_add_task);

        // Drawer layout
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.drawer_open, R.string.drawer_close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        expandableListView = (ExpandableListView) findViewById(R.id.navList);
        updateDrawerItems();

        // Loading the data from the server into the user
        jsonParser = new UserJsonParser();
        jsonParser.loadAllDataIntoUser(queue);

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
                // Hide Fab and dock
                findViewById(R.id.fab_container).setVisibility(View.GONE);
                findViewById(R.id.dock).setVisibility(View.GONE);
                fab.setExpanded(false);
                loadFragment(new AddTaskFragment());
            }
        });

        fabAddGoal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Hide Fab and dock
                findViewById(R.id.fab_container).setVisibility(View.GONE);
                findViewById(R.id.dock).setVisibility(View.GONE);
                fab.setExpanded(false);
                loadFragment(new AddGoalFragment());
            }
        });

        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // TODO a way to made it cleaner
        // Drawer button
        if (drawerToggle.onOptionsItemSelected(item))
            return true;
        else {
            switch (item.getItemId()) {
                case R.id.sign_out:
                    signOut();
                    return true;
                default:
                    return super.onOptionsItemSelected(item);
            }
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
        transaction.commitAllowingStateLoss();
    }

    /**
     * Updates the items of the drawer menu with the current user's data (tasklists then folders)
     */
    private void updateDrawerItems() {
        final ArrayList<TaskList> standaloneTaskLists = new ArrayList<>();
        for(TaskList taskList : user.getTaskLists())
            if(taskList.isStandalone())
                standaloneTaskLists.add(taskList);

        adapter = new DrawerListAdapter(this, standaloneTaskLists, user.getFolders());
        expandableListView.setAdapter(adapter);

        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int i, long l) {
                if (i >= standaloneTaskLists.size())
                    return false;

                drawerLayout.closeDrawer(GravityCompat.START);
                loadTaskListFragment(user.getTaskLists().get(i));
                return true;
            }
        });

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {
                drawerLayout.closeDrawer(GravityCompat.START);

                loadTaskListFragment(
                        user.getFolders().get(i - standaloneTaskLists.size()).getTaskLists().get(i1)
                );
                return true;
            }
        });
    }

    private void loadTaskListFragment(TaskList taskList) {

        // Data to pass in the fragment
        Bundle bundle = new Bundle();
        bundle.putSerializable(TaskList.SERIAL_TASK_LIST_KEY, taskList);
        TaskListFragment taskListFragment = new TaskListFragment();
        taskListFragment.setArguments(bundle);

        // Load goalsTodos fragment
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, taskListFragment)
                .commit();
    }

    public static User getUser() {
        return user;
    }
    /**
     * Sign out google account
     */
    private void signOut(){
        mGoogleSignInClient.signOut();
        Intent intent = new Intent(MainActivity.this,SignInActivity.class);
        startActivity(intent);
    }
}
