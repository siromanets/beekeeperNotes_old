package com.example.key.beekeepernote.interfaces;

import android.view.View;

import com.example.key.beekeepernote.models.BeeColony;
import com.example.key.beekeepernote.models.Beehive;

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
    void moveBeehive(Set<Beehive> moveBeehives,Beehive itemBeehive , String fromWhichApiary, String inWhichApiary, boolean inFront);
    void replaceBeehive(Set<Beehive> replaceBeehives, Beehive itemBeehive, String fromWhichApiary, String inWhichApiary);
}
