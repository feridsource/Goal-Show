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
