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

package com.ferid.app.goalshow;

import android.content.Context;

/**
 * Created by vito on 10/2/2015.
 */
public class Commentator {

    /**
     * Make speaker comment on the situation
     */
    public static String askSpeaker(Context context, long time) {
        String comment = "";

        if (time < context.getResources().getInteger(R.integer.possession)) {
            comment = context.getString(R.string.noPosition);
        } else if (time < context.getResources().getInteger(R.integer.shot)) {
            comment = context.getString(R.string.niceAttack);
        } else if (time < context.getResources().getInteger(R.integer.shotOnTarget)) {
            comment = context.getString(R.string.shot);
        } else if (time < context.getResources().getInteger(R.integer.goalLow)) {
            comment = context.getString(R.string.shotOnTarget);
        } else if (time <= context.getResources().getInteger(R.integer.goalHigh)) {
            comment = context.getString(R.string.speakerOnGoal);
        } else if (time > context.getResources().getInteger(R.integer.goalHigh)) {
            comment = context.getString(R.string.out);
        }

        return comment;
    }
}
