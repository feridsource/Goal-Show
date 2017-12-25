package com.ferid.app.goalshow.games;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.ferid.app.goalshow.R;
import com.ferid.app.goalshow.models.Opponent;

/**
 * Created by vito on 9/10/2015.
 */
public class GameOverActivity extends AppCompatActivity {

    private TextView opponentLeftScore;
    private TextView opponentRightScore;
    private TextView opponentLeftShotOnTarget;
    private TextView opponentRightShotOnTarget;
    private TextView opponentLeftShot;
    private TextView opponentRightShot;
    private TextView opponentLeftPossession;
    private TextView opponentRightPossession;

    private Opponent leftOpponent = new Opponent();
    private Opponent rightOpponent = new Opponent();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_over);

        Bundle args = getIntent().getExtras();
        if (args != null) {
            leftOpponent = (Opponent) args.getSerializable("leftOpponent");
            rightOpponent = (Opponent) args.getSerializable("rightOpponent");
        }

        setToolbar();

        opponentLeftScore = (TextView) findViewById(R.id.opponentLeftScore);
        opponentRightScore = (TextView) findViewById(R.id.opponentRightScore);
        opponentLeftShotOnTarget = (TextView) findViewById(R.id.opponentLeftShotOnTarget);
        opponentRightShotOnTarget = (TextView) findViewById(R.id.opponentRightShotOnTarget);
        opponentLeftShot = (TextView) findViewById(R.id.opponentLeftShot);
        opponentRightShot = (TextView) findViewById(R.id.opponentRightShot);
        opponentLeftPossession = (TextView) findViewById(R.id.opponentLeftPossession);
        opponentRightPossession = (TextView) findViewById(R.id.opponentRightPossession);

        setMatchStats();
    }

    /**
     * Set toolbar
     */
    private void setToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }
    }

    /**
     * Show match stats
     */
    private void setMatchStats() {
        opponentLeftScore.setText("" + leftOpponent.getGoal());
        opponentRightScore.setText("" + rightOpponent.getGoal());
        opponentLeftShotOnTarget.setText("" + leftOpponent.getShotOnTarget());
        opponentRightShotOnTarget.setText("" + rightOpponent.getShotOnTarget());
        opponentLeftShot.setText("" + leftOpponent.getShot());
        opponentRightShot.setText("" + rightOpponent.getShot());

        //calculate possession
        int leftPossessionNumber = leftOpponent.getPossession();
        int rightPossessionNumber = rightOpponent.getPossession();
        int totalPossessionNumber = leftPossessionNumber + rightPossessionNumber;
        int leftPossessionRatio
                = (int) ((((double) leftPossessionNumber) / totalPossessionNumber) * 100);
        int rightPossessionRatio = 100 - leftPossessionRatio;

        opponentLeftPossession.setText("" + leftPossessionRatio);
        opponentRightPossession.setText("" + rightPossessionRatio);
    }

    private void closeWindow() {
        finish();
        overridePendingTransition(R.anim.stand_still, R.anim.move_out_to_bottom);
    }

    @Override
    public void onBackPressed() {
        closeWindow();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar actions click
        switch (item.getItemId()) {
            case android.R.id.home:
                closeWindow();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}