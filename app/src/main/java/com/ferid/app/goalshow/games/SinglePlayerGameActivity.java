package com.ferid.app.goalshow.games;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ferid.app.goalshow.Commentator;
import com.ferid.app.goalshow.R;
import com.ferid.app.goalshow.enums.GameLevel;
import com.ferid.app.goalshow.enums.TimerState;
import com.ferid.app.goalshow.models.Opponent;
import com.ferid.app.goalshow.prefs.PrefsUtil;

import org.apache.commons.lang3.time.StopWatch;

import java.util.Random;

/**
 * Created by vito on 9/30/2015.
 */
public class SinglePlayerGameActivity extends AppCompatActivity {

    //score board
    private TextView scoreLeft;
    private TextView scoreRight;
    //opportunities
    private TextView scoringOpportunityLeft;
    private TextView scoringOpportunityRight;

    //speaker
    private TextView speaker;

    //play area
    private LinearLayout playArea;
    private TextView timer;

    //muscles
    private Handler handler = new Handler();
    private StopWatch stopWatch = new StopWatch();

    //limbs
    private boolean isOpponentLeft = true;
    private TimerState timerState = TimerState.STOP;
    private boolean wait = false;

    //opponents
    private Opponent leftOpponent = new Opponent();
    private Opponent rightOpponent = new Opponent();

    //machine related
    private int machineScore = 0;
    private int level = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        scoreLeft = (TextView) findViewById(R.id.scoreLeft);
        scoreRight = (TextView) findViewById(R.id.scoreRight);
        scoringOpportunityLeft = (TextView) findViewById(R.id.scoringOpportunityLeft);
        scoringOpportunityRight = (TextView) findViewById(R.id.scoringOpportunityRight);
        speaker = (TextView) findViewById(R.id.speaker);

        playArea = (LinearLayout) findViewById(R.id.playArea);
        timer = (TextView) findViewById(R.id.timer);

        setCurrentLevelValue();

        playArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!wait && isOpponentLeft) {
                    setPlayAreaClickListener();
                }
            }
        });
    }

    /**
     * Click event of play area
     */
    private void setPlayAreaClickListener() {
        //if opportunities have reached to the end, stop letting play anymore.
        if (isGameOver()) {
            return;
        }

        if (timerState == TimerState.STOP && !stopWatch.isStarted()) {
            timerState = TimerState.START;

            stopWatch.start();

            runTimer();
        } else if (timerState == TimerState.START) {
            stop();
        }
    }

    /**
     * Change opponent
     */
    private void changeOpponent() {
        isOpponentLeft = !isOpponentLeft;

        setPlayAreaColour();
    }

    /**
     * Setting play area colour, indicates the order of the opponents
     */
    private void setPlayAreaColour() {
        if (isOpponentLeft) {
            playArea.setBackgroundColor(getResources().getColor(R.color.leftOpponent));
        } else {
            playArea.setBackgroundColor(getResources().getColor(R.color.rightOpponent));
        }
    }

    /**
     * Start timer
     */
    private void runTimer() {
        if (timerState != TimerState.STOP) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    if (stopWatch.getTime() <= getResources().getInteger(R.integer.goalHigh)) {

                        if (timerState != TimerState.STOP) {
                            showLatestTimer();

                            runTimer();
                        }
                    } else {
                        stop();
                    }
                }
            });
        }
    }

    /**
     * Make the machine/device play
     */
    private void startMachinePlayer() {
        wait = true;

        try {
            Thread.sleep(getResources().getInteger(R.integer.waitAfterHuman));
        } catch (Exception ignored) {}

        Random random = new Random();

        boolean isEnhance;  //this is the value to enhance the machineScore
                            //in case of being too small

        //enhance with 3:4 probability
        isEnhance = random.nextDouble() <= 0.75;

        machineScore = random.nextInt(getResources()
                .getInteger(R.integer.machineOutHigh) - level) + level;

        if (isEnhance) {
            int midPoint = (getResources()
                    .getInteger(R.integer.machineOutHigh) - level) / 2;

            //if it is not goal definitely, make it possible to be goal
            if (machineScore <= (getResources()
                    .getInteger(R.integer.goalHigh) - midPoint)) {
                machineScore += midPoint;
            }
        }

        new PlayMachine().execute();
    }

    /**
     * Machine player process
     */
    private class PlayMachine extends AsyncTask<Void, Integer, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            for (int i = 0; i <= machineScore; i += 25) {
                try {
                    Thread.sleep(5);
                } catch (Exception ignored) {}

                publishProgress(i);
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            stop();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            timer.setText(String.valueOf(values[0]));
        }
    }

    /**
     * Show the latest time
     */
    private void showLatestTimer() {
        timer.setText(String.valueOf(getTime()));
    }

    /**
     * Get the timer's latest time after stopping
     * @return Integer
     */
    private int getTime() {
        int time;
        if (isOpponentLeft) {
            time = (int) stopWatch.getTime();
        } else {
            time = machineScore;
        }

        return time;
    }

    /**
     * Reset timer and contents
     */
    private void reset() {
        timer.setText("0");
        speaker.setText(getString(R.string.noComment));

        stopWatch.reset();

        wait = false;
    }

    /**
     * Stop timer and re-new contents
     */
    private void stop() {
        timerState = TimerState.STOP;
        wait = true;

        if (!stopWatch.isStopped()) {
            stopWatch.stop();
        }

        showLatestTimer();

        decideOnOpportunity();
        decideOnScore();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (isGameOver()) {
                    gameOver();
                } else {
                    changeOpponent();
                    reset();

                    if (!isOpponentLeft) {
                        startMachinePlayer();
                    }
                }
            }
        }, getResources().getInteger(R.integer.waitAfterShot));
    }

    /**
     * Decide on opportunity
     */
    private void decideOnOpportunity() {
        //increment by 1
        if (isOpponentLeft) {
            leftOpponent.setOpportunity(leftOpponent.getOpportunity() + 1);
        } else {
            rightOpponent.setOpportunity(rightOpponent.getOpportunity() + 1);
        }
        setOpportunity();
    }

    /**
     * Decide on score
     */
    private void decideOnScore() {
        int time = getTime();

        if (time >= getResources().getInteger(R.integer.goalLow)
                && time <= getResources().getInteger(R.integer.goalHigh)) {
            //Goal
            timer.setText(getString(R.string.goal));

            if (isOpponentLeft) {
                leftOpponent.setGoal(leftOpponent.getGoal() + 1);
                leftOpponent.setShotOnTarget(leftOpponent.getShotOnTarget() + 1);
                leftOpponent.setShot(leftOpponent.getShot() + 1);
                leftOpponent.setPossession(leftOpponent.getPossession() + 1);
            } else {
                rightOpponent.setGoal(rightOpponent.getGoal() + 1);
                rightOpponent.setShotOnTarget(rightOpponent.getShotOnTarget() + 1);
                rightOpponent.setShot(rightOpponent.getShot() + 1);
                rightOpponent.setPossession(rightOpponent.getPossession() + 1);
            }
            setScore();
        } else if (time < getResources().getInteger(R.integer.goalLow)
                && time >= getResources().getInteger(R.integer.shotOnTarget)) {
            //shot on target
            if (isOpponentLeft) {
                leftOpponent.setShotOnTarget(leftOpponent.getShotOnTarget() + 1);
                leftOpponent.setShot(leftOpponent.getShot() + 1);
                leftOpponent.setPossession(leftOpponent.getPossession() + 1);
            } else {
                rightOpponent.setShotOnTarget(rightOpponent.getShotOnTarget() + 1);
                rightOpponent.setShot(rightOpponent.getShot() + 1);
                rightOpponent.setPossession(rightOpponent.getPossession() + 1);
            }
        } else if (time < getResources().getInteger(R.integer.shotOnTarget)
                && time >= getResources().getInteger(R.integer.shot)) {
            //Shot
            if (isOpponentLeft) {
                leftOpponent.setShot(leftOpponent.getShot() + 1);
                leftOpponent.setPossession(leftOpponent.getPossession() + 1);
            } else {
                rightOpponent.setShot(rightOpponent.getShot() + 1);
                rightOpponent.setPossession(rightOpponent.getPossession() + 1);
            }
        } else if (time < getResources().getInteger(R.integer.shot)
                && time >= getResources().getInteger(R.integer.possession)) {
            //Goal attempt
            if (isOpponentLeft) {
                leftOpponent.setPossession(leftOpponent.getPossession() + 1);
            } else {
                rightOpponent.setPossession(rightOpponent.getPossession() + 1);
            }
        }

        askSpeaker(time);
    }

    /**
     * Make speaker comment on the situation
     */
    private void askSpeaker(long time) {
        speaker.setText(Commentator.askSpeaker(this, time));
    }

    /**
     * Write left and right scores
     */
    private void setScore() {
        scoreLeft.setText("" + leftOpponent.getGoal());
        scoreRight.setText("" + rightOpponent.getGoal());
    }

    /**
     * Write left and right opportunities
     */
    private void setOpportunity() {
        scoringOpportunityLeft.setText(leftOpponent.getOpportunity()
                + "/" + getResources().getInteger(R.integer.max_opportunity));
        scoringOpportunityRight.setText(rightOpponent.getOpportunity()
                + "/" + getResources().getInteger(R.integer.max_opportunity));
    }

    /**
     * Initialise attributes to start a new game
     */
    private void newGame() {
        leftOpponent = new Opponent();
        rightOpponent = new Opponent();
        isOpponentLeft = true;
        timerState = TimerState.STOP;

        setOpportunity();
        setScore();
        setPlayAreaColour();
        //now it is ready to play
        reset();
    }

    /**
     * Saves the game's latest state
     */
    private void saveGame() {
        PrefsUtil.writeLeftOpponentOne(this, leftOpponent);
        PrefsUtil.writeRightOpponentOne(this, rightOpponent);
        PrefsUtil.setOpponentLeftOne(this, isOpponentLeft);
    }

    /**
     * Load the game from the last saved attributes
     */
    private void loadGame() {
        isOpponentLeft = PrefsUtil.isOpponentLeftOne(this);
        leftOpponent = PrefsUtil.readLeftOpponentOne(this);
        rightOpponent = PrefsUtil.readRightOpponentOne(this);

        setOpportunity();
        setScore();
        setPlayAreaColour();

        //if it is the machine's turn let it play
        if (!isOpponentLeft) {
            startMachinePlayer();
        } else { //otherwise the human is to play
            //now it is ready to play
            wait = false;
        }
    }

    /**
     * Decides if it the game is over
     * @return boolean
     */
    private boolean isGameOver() {
        return (leftOpponent.getOpportunity() == getResources()
                .getInteger(R.integer.max_opportunity)
                && rightOpponent.getOpportunity() == getResources()
                .getInteger(R.integer.max_opportunity));
    }

    /**
     * If the game is over, show a pop up
     */
    private void gameOver() {
        Intent intent = new Intent(this, GameOverActivity.class);
        intent.putExtra("leftOpponent", leftOpponent);
        intent.putExtra("rightOpponent", rightOpponent);
        startActivity(intent);
        overridePendingTransition(R.anim.move_in_from_bottom, R.anim.stand_still);

        //make it ready for another game
        newGame();
    }

    /**
     * Increase level
     */
    private void levelUp() {
        GameLevel currentLevel = PrefsUtil.getLevel(this);

        if (currentLevel.ordinal() < GameLevel.LEVEL_MAX.ordinal()) {
            int tmpNewLevel = currentLevel.ordinal() + 1;

            PrefsUtil.setLevel(this, tmpNewLevel);
        }

        setCurrentLevelValue();
    }

    /**
     * Reduce level
     */
    private void levelDown() {
        GameLevel currentLevel = PrefsUtil.getLevel(this);

        if (currentLevel.ordinal() > GameLevel.LEVEL_MIN.ordinal()) {
            int tmpNewLevel = currentLevel.ordinal() - 1;

            PrefsUtil.setLevel(this, tmpNewLevel);
        }

        setCurrentLevelValue();
    }

    /**
     * Set current level's value.<br />
     * Also show the level to the user
     */
    private void setCurrentLevelValue() {
        GameLevel currentLevel = PrefsUtil.getLevel(this);
        level = currentLevel.getValue();

        if (playArea != null) {
            Snackbar.make(playArea,
                    getString(R.string.level) + (currentLevel.ordinal() + 1),
                    Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putSerializable("leftOpponent", leftOpponent);
        outState.putSerializable("rightOpponent", rightOpponent);
        outState.putBoolean("isOpponentLeft", isOpponentLeft);

        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        leftOpponent = (Opponent) savedInstanceState.getSerializable("leftOpponent");
        rightOpponent = (Opponent) savedInstanceState.getSerializable("rightOpponent");
        isOpponentLeft = savedInstanceState.getBoolean("isOpponentLeft");

        setOpportunity();
        setScore();
        setPlayAreaColour();
    }

    @Override
    public void onBackPressed() {
        saveGame();

        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_single_player_game, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar actions click
        if (wait) {
            return false;
        }

        switch (item.getItemId()) {
            case R.id.newGame:
                newGame();
                return true;
            case R.id.levelUp:
                levelUp();
                return true;
            case R.id.levelDown:
                levelDown();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        //load the latest played game if necessary
        loadGame();
    }

    @Override
    protected void onStop() {
        //if the game is not finished, save it.
        if (!isGameOver()) {
            saveGame();
        }

        super.onStop();
    }

}