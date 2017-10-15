package com.example.key.beekeepernote.models;

import java.io.Serializable;

/**
 * Created by Key on 28.06.2017.
 */

public class BeeColony implements Serializable {
    public long queen;
    public long worm;
    public long riskOfSwaddling;
    public boolean haveFood;
    public String noteBeeColony;
    public String output;
    public int beeHoneyFrame;
    public int beeWormsFrame;
    public int beeEmptyFrame;
    public long checkedTime;
    public long founded;

    public BeeColony() {
    }

    public long getCheckedTime() {
        return checkedTime;
    }

    public void setCheckedTime(long checkedTime) {
        this.checkedTime = checkedTime;
    }

    public long getFounded() {
        return founded;
    }

    public void setFounded(long founded) {
        this.founded = founded;
    }

    public long isQueen() {
        return queen;
    }

    public void setQueen(long queen) {
        this.queen = queen;
    }

    public long isWorm() {
        return worm;
    }

    public void setWorm(long worm) {
        this.worm = worm;
    }

    public long isRiskOfSwaddling() {
        return riskOfSwaddling;
    }

    public void setRiskOfSwaddling(long riskOfSwaddling) {
        this.riskOfSwaddling = riskOfSwaddling;
    }

    public boolean isHaveFood() {
        return haveFood;
    }

    public void setHaveFood(boolean haveFood) {
        this.haveFood = haveFood;
    }

    public String getNoteBeeColony() {
        return noteBeeColony;
    }

    public void setNoteBeeColony(String noteBeeColony) {
        this.noteBeeColony = noteBeeColony;
    }

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }

    public int getBeeHoneyFrame() {
        return beeHoneyFrame;
    }

    public void setBeeHoneyFrame(int beeHoneyFrame) {
        this.beeHoneyFrame = beeHoneyFrame;
    }

    public int getBeeWormsFrame() {
        return beeWormsFrame;
    }

    public void setBeeWormsFrame(int beeWormsFrame) {
        this.beeWormsFrame = beeWormsFrame;
    }

    public int getBeeEmptyFrame() {
        return beeEmptyFrame;
    }

    public void setBeeEmptyFrame(int beeEmptyFrame) {
        this.beeEmptyFrame = beeEmptyFrame;
    }
}
