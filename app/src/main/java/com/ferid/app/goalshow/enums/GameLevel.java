package com.ferid.app.goalshow.enums;

/**
 * Created by vito on 9/30/2015.
 */
public enum GameLevel {
    LEVEL_MIN(500),
    LEVEL_2(750),
    LEVEL_MAX(875);

    private final int value;

    private GameLevel(int value) {
        this.value = value;
    }
    public int getValue() {
        return value;
    }
}