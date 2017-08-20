package com.example.key.beekeepernote;

import com.example.key.beekeepernote.database.BeeColony;
import com.example.key.beekeepernote.database.Beehive;

/**
 * Created by Key on 09.07.2017.
 */

public interface Communicator{
    void saveBeehive(Beehive beehive, String nameApiary);
    void saveColony(BeeColony beeColony, String nameApiary, int nameBeehive);
    void deleteBeehive(Beehive beehive, String nameApiary);
    void pasteBeehive(Beehive pasteBeehive, Beehive positionBeehive, String nameApiary);
}
