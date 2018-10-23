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

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;

import com.ferid.app.goalshow.games.SinglePlayerGameActivity;
import com.ferid.app.goalshow.games.TwoPlayerGameActivity;
import com.ferid.app.goalshow.tutorial.TutorialActivity;

/**
 * Created by vito on 9/30/2015.
 */
public class MainActivity extends AppCompatActivity {

    private RelativeLayout twoPlayerGame;
    private RelativeLayout singlePlayerGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        twoPlayerGame = findViewById(R.id.twoPlayerGame);
        singlePlayerGame = findViewById(R.id.singlePlayerGame);

        setListeners();
    }

    /**
     * Set layout listeners
     */
    private void setListeners() {
        twoPlayerGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startGame(true);
            }
        });

        singlePlayerGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startGame(false);
            }
        });
    }

    /**
     * Go to game area and start to play
     * @param isTwoPlayer
     */
    private void startGame(boolean isTwoPlayer) {
        Intent intent;
        if (isTwoPlayer) {
            intent = new Intent(this, TwoPlayerGameActivity.class);
        } else {
            intent = new Intent(this, SinglePlayerGameActivity.class);
        }
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.move_in_from_bottom, R.anim.stand_still);
    }

    /**
     * Show tutorial of the game
     */
    private void showTutorial() {
        Intent intent = new Intent(this, TutorialActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.move_in_from_bottom, R.anim.stand_still);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar actions click
        switch (item.getItemId()) {
            case R.id.showTutorial:
                showTutorial();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}