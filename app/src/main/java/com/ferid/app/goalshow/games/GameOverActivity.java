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

        opponentLeftScore = findViewById(R.id.opponentLeftScore);
        opponentRightScore = findViewById(R.id.opponentRightScore);
        opponentLeftShotOnTarget = findViewById(R.id.opponentLeftShotOnTarget);
        opponentRightShotOnTarget = findViewById(R.id.opponentRightShotOnTarget);
        opponentLeftShot = findViewById(R.id.opponentLeftShot);
        opponentRightShot = findViewById(R.id.opponentRightShot);
        opponentLeftPossession = findViewById(R.id.opponentLeftPossession);
        opponentRightPossession = findViewById(R.id.opponentRightPossession);

        setMatchStats();
    }

    /**
     * Set toolbar
     */
    private void setToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
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
        opponentLeftScore.setText(String.valueOf(leftOpponent.getGoal()));
        opponentRightScore.setText(String.valueOf(rightOpponent.getGoal()));
        opponentLeftShotOnTarget.setText(String.valueOf(leftOpponent.getShotOnTarget()));
        opponentRightShotOnTarget.setText(String.valueOf(rightOpponent.getShotOnTarget()));
        opponentLeftShot.setText(String.valueOf(leftOpponent.getShot()));
        opponentRightShot.setText(String.valueOf(rightOpponent.getShot()));

        //calculate possession
        int leftPossessionNumber = leftOpponent.getPossession();
        int rightPossessionNumber = rightOpponent.getPossession();
        int totalPossessionNumber = leftPossessionNumber + rightPossessionNumber;
        int leftPossessionRatio
                = (int) ((((double) leftPossessionNumber) / totalPossessionNumber) * 100);
        int rightPossessionRatio = 100 - leftPossessionRatio;

        opponentLeftPossession.setText(String.valueOf(leftPossessionRatio));
        opponentRightPossession.setText(String.valueOf(rightPossessionRatio));
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