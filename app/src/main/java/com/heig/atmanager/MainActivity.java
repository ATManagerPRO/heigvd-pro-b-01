package com.heig.atmanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    View fragmentContainer;
    View button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadFragment(new HomeFragment());

        //instantly switches to the Profile activity for testing purposes
        //Intent myIntent = new Intent(MainActivity.this, ProfileActivity.class);
        //MainActivity.this.startActivity(myIntent);
        fragmentContainer = findViewById(R.id.folder_fragment_container);
        button = findViewById(R.id.drawer_button);

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(fragmentContainer.getVisibility() == View.GONE){
                    fragmentContainer.setVisibility(View.VISIBLE);
                    fragmentContainer.bringToFront();
                } else{
                    fragmentContainer.setVisibility(View.GONE);
                }
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
