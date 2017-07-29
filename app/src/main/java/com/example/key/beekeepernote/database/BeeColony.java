package com.example.key.beekeepernote.database;

import java.io.Serializable;
import java.util.List;

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
    public List<BeeFrame> beeFrames;

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

    public List<BeeFrame> getBeeFrames() {
        return beeFrames;
    }

    public void setBeeFrames(List<BeeFrame> beeFrames) {
        this.beeFrames = beeFrames;
    }
}
