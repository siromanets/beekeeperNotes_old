package com.example.key.beekeepernote.database;

import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;

/**
 * Created by Key on 28.06.2017.
 */
@IgnoreExtraProperties
public class BeeFrame implements Serializable {
    public int typeBox;
    public int shapeBox;
    public String noteFieldBox;

    public BeeFrame() {
    }

    public int getTypeBox() {
        return typeBox;
    }

    public void setTypeBox(int typeBox) {
        this.typeBox = typeBox;
    }

    public int getShapeBox() {
        return shapeBox;
    }

    public void setShapeBox(int shapeBox) {
        this.shapeBox = shapeBox;
    }

    public String getNoteFieldBox() {
        return noteFieldBox;
    }

    public void setNoteFieldBox(String noteFieldBox) {
        this.noteFieldBox = noteFieldBox;
    }
}
