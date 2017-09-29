package com.example.key.beekeepernote.models;

import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by Key on 28.06.2017.
 *
 */
@IgnoreExtraProperties
public class Beehive implements Serializable {
    public int getNumberBeehive() {
        return numberBeehive;
    }

    public void setNumberBeehive(int numberBeehive) {
        this.numberBeehive = numberBeehive;
    }

    public int numberBeehive;
    public Date founded;
    public String noteBeehive;
    public int typeBeehive;
    public List<BeeColony>beeColonies;
    public Date checkedTime;



    public Beehive() {
    }
    public Beehive(Beehive beehive){
        numberBeehive = beehive.getNumberBeehive();
        founded = beehive.getFounded();
        noteBeehive = beehive.getNoteBeehive();
        typeBeehive = beehive.getTypeBeehive();
        beeColonies = beehive.getBeeColonies();
        checkedTime = beehive.getCheckedTime();
    }

    public Date getFounded() {
        return founded;
    }

    public void setFounded(Date founded) {
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
    public Date getCheckedTime() {
        return checkedTime;
    }

    public void setCheckedTime(Date checkedTime) {
        this.checkedTime = checkedTime;
    }
}
