package com.ferid.app.goalshow.prefs;

import android.content.Context;
import android.content.SharedPreferences;

import com.ferid.app.goalshow.R;
import com.ferid.app.goalshow.enums.GameLevel;
import com.ferid.app.goalshow.models.Opponent;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Created by vito on 9/10/2015.
 */
public class PrefsUtil {

    private static SharedPreferences sPrefs;

    /**
     * Initialise shared preferences
     * @param context
     */
    private static void initialisePrefs(Context context) {
        sPrefs = context.getSharedPreferences(context.getString(R.string.sharedPreferences), Context.MODE_PRIVATE);
    }

    /**
     * Get who's turn is it
     * @param context
     * @return
     */
    public static boolean isOpponentLeftTwo(Context context) {
        if (sPrefs == null) {
            initialisePrefs(context);
        }

        return sPrefs.getBoolean(context
                .getString(R.string.two_is_opponent_left), true);
    }

    /**
     * Set who's turn it is
     * @param context
     * @param value
     */
    public static void setOpponentLeftTwo(Context context, boolean value) {
        if (sPrefs != null) {
            SharedPreferences.Editor editor = sPrefs.edit();
            editor.putBoolean(context.getString(R.string.two_is_opponent_left), value);
            editor.apply();
        }
    }

    /**
     * Get who's turn is it
     * @param context
     * @return
     */
    public static boolean isOpponentLeftOne(Context context) {
        if (sPrefs == null) {
            initialisePrefs(context);
        }

        return sPrefs.getBoolean(context
                .getString(R.string.one_is_opponent_left), true);
    }

    /**
     * Set who's turn it is
     * @param context
     * @param value
     */
    public static void setOpponentLeftOne(Context context, boolean value) {
        if (sPrefs != null) {
            SharedPreferences.Editor editor = sPrefs.edit();
            editor.putBoolean(context.getString(R.string.one_is_opponent_left), value);
            editor.apply();
        }
    }

    /**
     * Get single-player game difficulty level
     * @param context
     * @return Game level
     */
    public static GameLevel getLevel(Context context) {
        if (sPrefs == null) {
            initialisePrefs(context);
        }

        int preference = sPrefs.getInt(context.getString(R.string.pref_level), 0);
        return GameLevel.values()[preference];
    }

    /**
     * Set single-player game difficulty level
     * @param context
     * @param gameLevel
     */
    public static void setLevel(Context context, int gameLevel) {
        if (sPrefs != null) {
            SharedPreferences.Editor editor = sPrefs.edit();
            editor.putInt(context.getString(R.string.pref_level), gameLevel);
            editor.apply();
        }
    }

    /**
     * Write left opponent into a bin file
     * @param context
     * @param opponent Opponent
     */
    public synchronized static void writeLeftOpponentTwo(Context context, Opponent opponent) {
        try {
            String tempPath = context.getFilesDir()
                    + context.getString(R.string.two_left_opponent);
            File file = new File(tempPath);
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
            oos.writeObject(opponent);
            oos.flush();
            oos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Read left opponent from a bin file
     * @param context
     * @return Opponent
     */
    public synchronized static Opponent readLeftOpponentTwo(Context context) {
        Opponent opponent = new Opponent();
        try {
            String tempPath = context.getFilesDir()
                    + context.getString(R.string.two_left_opponent);
            File file = new File(tempPath);
            if (file.exists()) {
                ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
                opponent = (Opponent) ois.readObject();
                ois.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return opponent;
    }

    /**
     * Write right opponent into a bin file
     * @param context
     * @param opponent Opponent
     */
    public synchronized static void writeRightOpponentTwo(Context context, Opponent opponent) {
        try {
            String tempPath = context.getFilesDir()
                    + context.getString(R.string.two_right_opponent);
            File file = new File(tempPath);
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
            oos.writeObject(opponent);
            oos.flush();
            oos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Read right opponent from a bin file
     * @param context
     * @return Opponent
     */
    public synchronized static Opponent readRightOpponentTwo(Context context) {
        Opponent opponent = new Opponent();
        try {
            String tempPath = context.getFilesDir()
                    + context.getString(R.string.two_right_opponent);
            File file = new File(tempPath);
            if (file.exists()) {
                ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
                opponent = (Opponent) ois.readObject();
                ois.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return opponent;
    }

    /**
     * Write left opponent into a bin file
     * @param context
     * @param opponent Opponent
     */
    public synchronized static void writeLeftOpponentOne(Context context, Opponent opponent) {
        try {
            String tempPath = context.getFilesDir()
                    + context.getString(R.string.one_left_opponent);
            File file = new File(tempPath);
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
            oos.writeObject(opponent);
            oos.flush();
            oos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Read left opponent from a bin file
     * @param context
     * @return Opponent
     */
    public synchronized static Opponent readLeftOpponentOne(Context context) {
        Opponent opponent = new Opponent();
        try {
            String tempPath = context.getFilesDir()
                    + context.getString(R.string.one_left_opponent);
            File file = new File(tempPath);
            if (file.exists()) {
                ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
                opponent = (Opponent) ois.readObject();
                ois.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return opponent;
    }

    /**
     * Write right opponent into a bin file
     * @param context
     * @param opponent Opponent
     */
    public synchronized static void writeRightOpponentOne(Context context, Opponent opponent) {
        try {
            String tempPath = context.getFilesDir()
                    + context.getString(R.string.one_right_opponent);
            File file = new File(tempPath);
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
            oos.writeObject(opponent);
            oos.flush();
            oos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Read right opponent from a bin file
     * @param context
     * @return Opponent
     */
    public synchronized static Opponent readRightOpponentOne(Context context) {
        Opponent opponent = new Opponent();
        try {
            String tempPath = context.getFilesDir()
                    + context.getString(R.string.one_right_opponent);
            File file = new File(tempPath);
            if (file.exists()) {
                ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
                opponent = (Opponent) ois.readObject();
                ois.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return opponent;
    }

}