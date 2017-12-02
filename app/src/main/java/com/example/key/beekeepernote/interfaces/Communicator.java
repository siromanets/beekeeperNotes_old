package com.example.key.beekeepernote.interfaces;


import android.support.v7.view.ActionMode;

import com.example.key.beekeepernote.adapters.RecyclerAdapter;
import com.example.key.beekeepernote.models.Beehive;

import java.util.List;

/**
 * Created by Key on 09.07.2017.
 */

public interface Communicator{
    void saveBeehive(Beehive beehive, String nameApiary);
	ActionMode setDataForTools(RecyclerAdapter recyclerAdapter, List<Beehive> list, Beehive itemBeehive, String nameApiary, int mode);
    void selectAll();
    void moveTabTo(String nameApiary, int numberBeehive);
    void deleteBeehive(List<Beehive> deletedBeehives, String nameApiary, boolean refreshBeehivesListItem);
    void moveBeehive(List<Beehive> moveBeehives,Beehive itemBeehive , String fromWhichApiary, String inWhichApiary, boolean inFront);
    void replaceBeehive(List<Beehive> replaceBeehives, Beehive itemBeehive, String fromWhichApiary, String inWhichApiary);

    void dissmisActionMode();
}
