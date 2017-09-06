package com.example.key.beekeepernote.models;

import java.io.Serializable;

/**
 * Created by Key on 28.06.2017.
 */

public class BeeColony implements Serializable {
    public boolean queen;
    public boolean worm;
    public boolean riskOfSwaddling;
    public boolean haveFood;
    public String noteBeeColony;
    public String output;
    public int beeHoneyFrame;
    public int beeWormsFrame;
    public int beeEmptyFrame;


    public BeeColony() {
    }

    public boolean isQueen() {
        return queen;
    }

    public void setQueen(boolean queen) {
        this.queen = queen;
    }

    public boolean isWorm() {
        return worm;
    }

    public void setWorm(boolean worm) {
        this.worm = worm;
    }

    public boolean isRiskOfSwaddling() {
        return riskOfSwaddling;
    }

    public void setRiskOfSwaddling(boolean riskOfSwaddling) {
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
