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

}
