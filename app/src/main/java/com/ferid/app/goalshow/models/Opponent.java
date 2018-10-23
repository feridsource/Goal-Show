/*
 * Copyright (C) 2015 Ferid Cafer
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
