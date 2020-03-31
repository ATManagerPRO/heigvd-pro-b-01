package com.heig.atmanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import android.view.View;
import android.widget.Button;

public class Manager extends AppCompatActivity {
    View fragmentContainer;
    View button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager);

        loadFragment(new HomeFragment());
        fragmentContainer = findViewById(R.id.folder_fragment_container);
        button = findViewById(R.id.floatingActionButton);

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
