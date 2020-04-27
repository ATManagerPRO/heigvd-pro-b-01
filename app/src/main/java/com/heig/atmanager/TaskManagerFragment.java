package com.heig.atmanager;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * Author : Stéphane Bottin
 * Date   : 02.04.2020
 *
 * Task Manager to allow the user to see the tasks selected in the drawer menu
 */
public class TaskManagerFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_task_manager, container, false);

        return v;
    }
}