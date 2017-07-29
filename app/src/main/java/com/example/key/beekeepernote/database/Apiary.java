package com.example.key.beekeepernote.database;

import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Key on 28.06.2017.
 */

@IgnoreExtraProperties
public class Apiary implements Serializable {
    public String nameApiary;
    public String noteApiary;
    public String locationApiary;
    public List<Beehive> beehives;

    public Apiary() {
    }

    public String getNameApiary() {
        return nameApiary;
    }

    public void setNameApiary(String nameApiary) {
        this.nameApiary = nameApiary;
    }

    public String getNoteApiary() {
        return noteApiary;
    }

    public void setNoteApiary(String noteApiary) {
        this.noteApiary = noteApiary;
    }

    public String getLocationApiary() {
        return locationApiary;
    }

    public void setLocationApiary(String locationApiary) {
        this.locationApiary = locationApiary;
    }

    public List<Beehive> getBeehives() {
        return beehives;
    }

    public void setBeehives(List<Beehive> beehives) {
        this.beehives = beehives;
    }
}
