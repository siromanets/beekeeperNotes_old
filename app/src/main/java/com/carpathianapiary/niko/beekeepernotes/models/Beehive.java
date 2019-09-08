package com.carpathianapiary.niko.beekeepernotes.models;

import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Key on 28.06.2017.
 *
 */
@IgnoreExtraProperties
public class Beehive implements Serializable {

    public int numberBeehive;
    public long founded;
    public String noteBeehive;
    public int typeBeehive;
    public List<BeeColony>beeColonies;




    public Beehive() {
    }
    public Beehive(Beehive beehive){
        numberBeehive = beehive.getNumberBeehive();
        founded = beehive.getFounded();
        noteBeehive = beehive.getNoteBeehive();
        typeBeehive = beehive.getTypeBeehive();
        beeColonies = beehive.getBeeColonies();
    }
    public int getNumberBeehive() {
        return numberBeehive;
    }

    public void setNumberBeehive(int numberBeehive) {
        this.numberBeehive = numberBeehive;
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
