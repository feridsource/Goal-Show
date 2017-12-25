package com.ferid.app.goalshow.models;

import java.io.Serializable;

/**
 * Created by vito on 9/11/2015.
 */
public class Opponent implements Serializable {
    private int opportunity;
    private int goal;
    private int shotOnTarget;
    private int shot;
    private int possession;

    public Opponent() {
        opportunity = 0;
        goal = 0;
        shotOnTarget = 0;
        shot = 0;
        possession = 0;
    }

    public int getOpportunity() {
        return opportunity;
    }

    public void setOpportunity(int opportunity) {
        this.opportunity = opportunity;
    }

    public int getGoal() {
        return goal;
    }

    public void setGoal(int goal) {
        this.goal = goal;
    }

    public int getShotOnTarget() {
        return shotOnTarget;
    }

    public void setShotOnTarget(int shotOnTarget) {
        this.shotOnTarget = shotOnTarget;
    }

    public int getShot() {
        return shot;
    }

    public void setShot(int shot) {
        this.shot = shot;
    }

    public int getPossession() {
        return possession;
    }

    public void setPossession(int possession) {
        this.possession = possession;
    }
}
