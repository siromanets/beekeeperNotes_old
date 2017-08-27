package com.example.key.beekeepernote;

import android.view.View;

import com.example.key.beekeepernote.database.BeeColony;
import com.example.key.beekeepernote.database.Beehive;

import java.util.Set;

/**
 * Created by Key on 09.07.2017.
 */

public interface Communicator{
    void saveBeehive(Beehive beehive, String nameApiary);
    void saveColony(BeeColony beeColony, String nameApiary, int nameBeehive);
	void setDataForTools(Beehive beehive, View view, String nameApiary);
    void selectAll();
    void multiSelectMod();
    void deleteBeehive(Set<Beehive> deletedBeehives, String nameApiary, boolean refreshBeehivesListItem);
    void moveBeehive(Set<Beehive> copiedBeehivesSet, Beehive itemBeehive, String fromWhichApiary, String inWhichApiary);
}
