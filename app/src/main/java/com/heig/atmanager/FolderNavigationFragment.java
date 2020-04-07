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
        folders.get(2).addList("sixth list");
        folders.get(2).addList("seventh list");
        folders.add(new Folder("PCO"));
        folders.get(3).addList("list");
        folders.get(3).addList("list1");
        folders.get(3).addList("list2");
        folders.get(3).addList("list3");
        folders.get(3).addList("list4");
        folders.get(3).addList("list5");
        folders.get(3).addList("list6");
        folders.get(3).addList("list7");
        folders.get(3).addList("list8");
        folders.get(3).addList("list9");
        folders.get(3).addList("list0");
        folders.get(3).addList("list10");
        folders.get(3).addList("list11");
        folders.get(3).addList("list22");
        folders.get(3).addList("list24");
        folders.get(3).addList("list33");
        folders.get(3).addList("list55");
        folders.get(3).addList("list66");
        folders.get(3).addList("list77");
        folders.get(3).addList("list88");
        folders.get(3).addList("list99");


        // --------- END TEMP ---------

        folderRecyclerView = (RecyclerView) v.findViewById(R.id.folder_recycler_view);
        Utils.setupFoldersFeed(v, folderRecyclerView, folders);

        return v;
    }
}