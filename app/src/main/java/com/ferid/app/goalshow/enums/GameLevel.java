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

package com.ferid.app.goalshow.enums;

/**
 * Created by vito on 9/30/2015.
 */
public enum GameLevel {
    LEVEL_MIN(500),
    LEVEL_MID(750),
    LEVEL_MAX(875);

    private final int value;

    GameLevel(int value) {
        this.value = value;
    }
    public int getValue() {
        return value;
    }
}
