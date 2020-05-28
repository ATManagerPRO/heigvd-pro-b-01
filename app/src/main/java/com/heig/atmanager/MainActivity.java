package com.heig.atmanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.toolbox.Volley;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.android.volley.RequestQueue;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.heig.atmanager.addContent.AddFolderDiag;
import com.heig.atmanager.addContent.AddGoalFragment;
import com.heig.atmanager.addContent.AddTagsDiag;
import com.heig.atmanager.addContent.AddTaskFragment;
import com.heig.atmanager.addContent.AddTasklistDiag;
import com.heig.atmanager.calendar.CalendarFragment;
import com.heig.atmanager.calendar.local.LocalCalendarHandler;
import com.heig.atmanager.drawers.DrawerListAdapter;
import com.heig.atmanager.sharing.ShareTaskListDiag;
import com.heig.atmanager.goals.GoalsFragment;
import com.heig.atmanager.goals.GoalsTodoFragment;
import com.heig.atmanager.stats.StatsFragment;
import com.heig.atmanager.taskLists.TaskList;
import com.heig.atmanager.taskLists.TaskListFragment;
import com.heig.atmanager.tasks.TaskFeedAdapter;
import com.heig.atmanager.userData.User;
import com.heig.atmanager.userData.UserJsonParser;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity" ;
    private static User user;

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
    private ExpandableListAdapter drawerAdapter;

    public static String previousFragment = "";
    private GoogleSignInClient mGoogleSignInClient;
    private GoogleSignInAccount userAccount;

    // Current adapter for search feature
    private RecyclerView.Adapter contentAdapter;

    // JSON Parser
    private static UserJsonParser jsonParser;
    private static RequestQueue queue;


    public static String[] PERMISSIONS = {
            Manifest.permission.READ_CALENDAR,
            Manifest.permission.WRITE_CALENDAR
    };

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
        userAccount = GoogleSignIn.getLastSignedInAccount(this);

        // Creating the user with basic data
        Intent i = getIntent();
        Bundle bundle = i.getExtras();
        if (bundle != null) {
            for (String key : bundle.keySet()) {
                Log.e(TAG, key + " : " + (bundle.get(key) != null ? bundle.get(key) : "NULL"));
            }
        }
        user = new User(userAccount.getDisplayName(), userAccount.getIdToken(), userAccount.getEmail());
        user.setBackEndToken(i.getExtras().getString("userToken"));
        user.setUserId(i.getExtras().getLong("userId"));


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

        // Loading the data from the server into the user
        Log.d(TAG, "updateUI: testingSignIn loading user data...");
        jsonParser = new UserJsonParser(this);
        jsonParser.loadAllDataIntoUser(queue);

        // First fragment to load : Home
        displayFragment(HomeFragment.FRAG_HOME_ID);
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
                String selectedTag = "";
                switch (item.getItemId()) {
                    case R.id.home:
                        selectedFragment = new HomeFragment();
                        selectedTag = HomeFragment.FRAG_HOME_ID;
                        break;
                    case R.id.calendar:
                        selectedFragment = new CalendarFragment();
                        selectedTag = CalendarFragment.FRAG_CALENDAR_ID;
                        break;
                    case R.id.goals:
                        selectedFragment = new GoalsFragment();
                        selectedTag = GoalsFragment.FRAG_GOALS_ID;
                        break;
                    case R.id.stats:
                        selectedFragment = new StatsFragment();
                        selectedTag = StatsFragment.FRAG_STATS_ID;
                        break;
                    default:
                        return false;
                }
                enableBackButton(false);
                loadFragment(selectedFragment, selectedTag);
                return true;
            }
        });

        // Load fragment
        loadFragment(new HomeFragment(), HomeFragment.FRAG_HOME_ID);

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
                loadFragment(new AddTaskFragment(), AddTaskFragment.FRAG_ADD_TASK_ID);
            }
        });

        fabAddGoal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Hide Fab and dock
                findViewById(R.id.fab_container).setVisibility(View.GONE);
                findViewById(R.id.dock).setVisibility(View.GONE);
                fab.setExpanded(false);
                loadFragment(new AddGoalFragment(), AddGoalFragment.FRAG_ADD_GOAL_ID);
            }
        });

        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // TODO a way to made it cleaner
        // Drawer button
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        } else {
            switch (item.getItemId()) {
                case R.id.sign_out:
                    signOut();
                    return true;
                case R.id.add_tag:
                    DialogFragment addTagDiag = new AddTagsDiag();
                    addTagDiag.show(getSupportFragmentManager(), "addTag");
                    return true;
                case R.id.add_folder:
                    DialogFragment addFolderDiag = new AddFolderDiag();
                    addFolderDiag.show(getSupportFragmentManager(), "addFolder");
                    return true;
                case R.id.add_tasklist:
                    DialogFragment addTasklistDiag = new AddTasklistDiag();
                    addTasklistDiag.show(getSupportFragmentManager(), "addTasklist");
                    return true;
                case R.id.search:
                    SearchView searchView = (SearchView) item.getActionView();
                    searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                        @Override
                        public boolean onQueryTextSubmit(String s) {
                            return false;
                        }

                        @Override
                        public boolean onQueryTextChange(String s) {
                            if (contentAdapter != null)
                                ((TaskFeedAdapter) contentAdapter).getFilter().filter(s);
                            return false;
                        }
                    });
                    return true;
                case android.R.id.home:
                    onBackPressed();
                    return true;
                default:
                    return super.onOptionsItemSelected(item);
            }
        }
    }

    private void loadFragment(Fragment fragment, String tag) {

        // Create new fragment and transaction
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack
        transaction.replace(R.id.fragment_container, fragment, tag);
        transaction.addToBackStack(null);

        // Commit the transaction
        transaction.commitAllowingStateLoss();
    }

    /**
     * Updates the items of the drawer menu with the current user's data (tasklists then folders)
     */
    public void updateDrawerItems() {
        final ArrayList<TaskList> standaloneTaskLists = new ArrayList<>();

        for (TaskList taskList : user.getTaskLists())
            if (taskList.getFolderId() == -1)
                standaloneTaskLists.add(taskList);

        drawerAdapter = new DrawerListAdapter(this, standaloneTaskLists, user.getFolders());
        expandableListView.setAdapter(drawerAdapter);

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
                Log.d(TAG, "onChildClick: ");
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
    private void signOut() {
        mGoogleSignInClient.signOut();
        Intent intent = new Intent(MainActivity.this, SignInActivity.class);
        startActivity(intent);
    }

    /**
     * Enables the back button instead of the drawer menu
     *
     * @param enable : back button status
     */
    public void enableBackButton(boolean enable) {

        if (enable) {
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
            drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.drawer_open, R.string.drawer_close);
            drawerLayout.addDrawerListener(drawerToggle);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            drawerToggle.syncState();
        }
    }

    @Override
    public void onBackPressed() {
        enableBackButton(false);
        displayFragment(previousFragment);
        previousFragment = "";
    }

    public void displayFragment(String fragmentToDisplay) {
        Log.d(TAG, "displayPreviousFragment: displaying new fragment " + fragmentToDisplay);
        //creating fragment object
        Fragment fragment = null;
        String tag = "";

        //initializing the fragment object which is selected
        switch (fragmentToDisplay) {
            case HomeFragment.FRAG_HOME_ID:
                fragment = new HomeFragment();
                break;
            case GoalsFragment.FRAG_GOALS_ID:
                fragment = new GoalsFragment();
                break;
            case GoalsTodoFragment.FRAG_GOALS_TODO_ID:
                fragment = new GoalsTodoFragment();
                break;
            case CalendarFragment.FRAG_CALENDAR_ID:
                fragment = new CalendarFragment();
                break;
        }

        //replacing the fragment
        if (fragment != null) {
            loadFragment(fragment, previousFragment);
        }
    }

    // Share invite Dialog
    public void showShareTaskDialog(long taskListId){
        DialogFragment dialog = new ShareTaskListDiag(taskListId);
        dialog.show(getSupportFragmentManager(), "ShareTaskListDialog");
    }

    public void setContentAdapter(RecyclerView.Adapter adapter) {
        this.contentAdapter = adapter;
    }

    public static UserJsonParser getJsonParser() {
        return jsonParser;
    }

    public static RequestQueue getQueue(){
        return queue;
    }

    @Override
    public void onRequestPermissionsResult(final int requestCode, @NonNull final String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case LocalCalendarHandler.MY_CAL_CREATE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    LocalCalendarHandler.getInstance().createCalendar(this);
                }
                break;
            case LocalCalendarHandler.MY_CAL_CHECK:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    LocalCalendarHandler.getInstance().checkIfCalendarExist(this);
                }
                break;
            case LocalCalendarHandler.MY_CAL_ADD_TASK:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    LocalCalendarHandler.getInstance().addTask(this);
                } else {
                    // Explain why we need to have Calendar access
                    if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_CALENDAR)) {
                        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
                        alertDialog.setTitle("Calendar permission");
                        alertDialog.setMessage("We need to have access at your Calendar if you want to see it in the native calendar");
                        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                requestPermissions(permissions, requestCode);
                            }
                        });
                        alertDialog.show();
                    }
                }
                break;
        }
    }

}

