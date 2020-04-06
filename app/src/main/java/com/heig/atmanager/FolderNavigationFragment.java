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
 * A simple {@link Fragment} subclass.
 * Use the {@link FolderNavigationFragment} factory method to
 * create an instance of this fragment.
 */
public class FolderNavigationFragment extends Fragment {

    ArrayList<Folder> folders;

    private RecyclerView folderRecyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_folder_navigation, container, false);

        // ----------- TEMP -----------
        folders = new ArrayList<>();
        folders.add(new Folder("Administration"));
        folders.get(0).addList("first list");
        folders.get(0).addList("second list");
        folders.get(0).addList("third list");
        folders.add(new Folder("gaming"));
        folders.get(1).addList("fourth list");
        folders.get(1).addList("fifth list");
        folders.add(new Folder("ANA"));
        folders.add(new Folder("PCO"));
        folders.add(new Folder("PCO"));
        folders.add(new Folder("PCO"));
        folders.add(new Folder("PCO"));
        folders.add(new Folder("PCO"));
        folders.add(new Folder("PCO"));
        folders.add(new Folder("PCO"));
        folders.add(new Folder("PCO"));
        folders.add(new Folder("PCO"));
        folders.add(new Folder("PCO"));
        folders.add(new Folder("PCO"));
        folders.add(new Folder("PCO"));
        folders.add(new Folder("PCO"));
        folders.add(new Folder("PCO"));
        folders.add(new Folder("PCO"));
        folders.add(new Folder("PCO"));
        folders.add(new Folder("PCO"));
        folders.add(new Folder("PCO"));
        folders.add(new Folder("PCO"));
        folders.add(new Folder("PCO"));
        folders.add(new Folder("PCO"));


        // --------- END TEMP ---------

        folderRecyclerView = (RecyclerView) v.findViewById(R.id.folder_recycler_view);
        Utils.setupFoldersFeed(v, folderRecyclerView, folders);

        return v;
    }
}