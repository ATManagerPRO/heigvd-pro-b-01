package com.heig.atmanager;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.heig.atmanager.folders.Folder;

import java.util.ArrayList;


/**
 * Fragment to navigate the folders and tasklists
 */
public class FolderNavigationFragment extends Fragment {



    private RecyclerView folderRecyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_folder_navigation, container, false);

        folderRecyclerView = (RecyclerView) v.findViewById(R.id.folder_recycler_view);
        Utils.setupFoldersFeed(v, folderRecyclerView, DummyData.getUser().getFolders().getValue());

        return v;
    }
}