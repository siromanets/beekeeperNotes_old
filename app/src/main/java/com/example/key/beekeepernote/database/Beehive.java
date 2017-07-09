package com.example.key.beekeepernote.database;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.List;

/**
 * Created by Key on 28.06.2017.
 *
 */
@IgnoreExtraProperties
public class Beehive {
    public String nameBeehive;
    public long founded;
    public String noteBeehive;
    public int typeBeehive;
    public List<BeeColony>beeColonies;

    public Beehive() {
    }

    public String getNameBeehive() {
        return nameBeehive;
    }

    public void setNameBeehive(String nameBeehive) {
        this.nameBeehive = nameBeehive;
    }

    public long getFounded() {
        return founded;
    }

    public void setFounded(long founded) {
        this.founded = founded;
    }

    public String getNoteBeehive() {
        return noteBeehive;
    }

    public void setNoteBeehive(String noteBeehive) {
        this.noteBeehive = noteBeehive;
    }

    public int getTypeBeehive() {
        return typeBeehive;
    }

    public void setTypeBeehive(int typeBeehive) {
        this.typeBeehive = typeBeehive;
    }

    public List<BeeColony> getBeeColonies() {
        return beeColonies;
    }

    public void setBeeColonies(List<BeeColony> iBeeColonies) {
        this.beeColonies = iBeeColonies;
    }
}
