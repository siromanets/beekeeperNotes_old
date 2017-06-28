package com.example.key.beekeepernote.database;

import java.util.List;

/**
 * Created by Key on 28.06.2017.
 */

public class Bike {
    public boolean queen;
    public boolean worm;
    public boolean riskOfSwaddling;
    public boolean haveFood;
    public String noteBike;
    public String output;
    public List<BeeBox> beeBoxes;

    public Bike() {
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

    public String getNoteBike() {
        return noteBike;
    }

    public void setNoteBike(String noteBike) {
        this.noteBike = noteBike;
    }

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }

    public List<BeeBox> getBeeBoxes() {
        return beeBoxes;
    }

    public void setBeeBoxes(List<BeeBox> beeBoxes) {
        this.beeBoxes = beeBoxes;
    }
}
