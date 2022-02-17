package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    // Represents the internal state of the game
    private TicTacToeGame mGame;

    // Buttons making up the board
    private Button mBoardButtons[];
    // Various text displayed
    private TextView mInfoTextView;
    // Restart Button
    private Button startButton;
    // Game Over
    Boolean mGameOver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mGame = new TicTacToeGame();
        mBoardButtons = new Button[mGame.BOARD_SIZE];
        mBoardButtons[0] = (Button) findViewById(R.id.button0);
        mBoardButtons[1] = (Button) findViewById(R.id.button1);
        mBoardButtons[2] = (Button) findViewById(R.id.button2);
        mBoardButtons[3] = (Button) findViewById(R.id.button3);
        mBoardButtons[4] = (Button) findViewById(R.id.button4);
        mBoardButtons[5] = (Button) findViewById(R.id.button5);
        mBoardButtons[6] = (Button) findViewById(R.id.button6);
        mBoardButtons[7] = (Button) findViewById(R.id.button7);
        mBoardButtons[8] = (Button) findViewById(R.id.button8);
        mInfoTextView = (TextView) findViewById(R.id.information);
        mGame = new TicTacToeGame();
        startNewGame();
    }

    private void startNewGame() {
        mGameOver = false;
        mGame.clearBoard();
//---Reset all buttons
        for (int i = 0; i < mBoardButtons.length; i++) {
            mBoardButtons[i].setText("");
            mBoardButtons[i].setEnabled(true);
            mBoardButtons[i].setOnClickListener(new ButtonClickListener(i));
        }
//---Human goes first
        mInfoTextView.setText(R.string.Yougofirst);
    }

    private class ButtonClickListener implements View.OnClickListener {
        int location;
        public ButtonClickListener(int location) {
            this.location = location;
        }
        @Override
        public void onClick(View v) {
            if (mGameOver == false) {
                if (mBoardButtons[location].isEnabled()) {
                    setMove(TicTacToeGame.HUMAN_PLAYER, location);
//--- If no winner yet, let the computer make a move
                    int winner = mGame.checkForWinner();
                    if (winner == 0) {
                        mInfoTextView.setText(R.string.Itisandroidsturn);
                        int move = mGame.getComputerMove();
                        setMove(TicTacToeGame.COMPUTER_PLAYER, move);
                        winner = mGame.checkForWinner();
                    }
                    if (winner == 0) {
                        mInfoTextView.setTextColor(Color.rgb(0, 0, 0));
                        mInfoTextView.setText(R.string.itsyourturn);
                    } else if (winner == 1) {
                        mInfoTextView.setTextColor(Color.rgb(0, 0, 200));
                        mInfoTextView.setText(R.string.Itisatie);
                        mGameOver = true;
                    } else if (winner == 2) {
                        mInfoTextView.setTextColor(Color.rgb(0, 200, 0));
                        mInfoTextView.setText(R.string.Youwon);
                        mGameOver = true;
                    } else {
                        mInfoTextView.setTextColor(Color.rgb(200, 0, 0));
                        mInfoTextView.setText(R.string.Androidwon);
                        mGameOver = true;
                    }
                }
            }
        }
    }

    public void newGame(View v) {
        startNewGame();
    }

    private void setMove(char player, int location) {
        mGame.setMove(player, location);
        mBoardButtons[location].setEnabled(false);
        mBoardButtons[location].setText(String.valueOf(player));
        if (player == TicTacToeGame.HUMAN_PLAYER)
            mBoardButtons[location].setTextColor(Color.rgb(0, 200, 0));
        else
            mBoardButtons[location].setTextColor(Color.rgb(200, 0, 0));
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_options, menu);
        SubMenu file = menu.addSubMenu("Difficulty");//add sub menu

        //Add menu items to submenu
        file.add(2, 100,1,"Easy(Level1)");
        file.add(2, 101, 2, "Harder(Level2)");
        file.add(2, 102, 3, "Expert(Level3)");
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.menu_about://about features
                Intent intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://en.wikipedia.org/wiki/Tic-tac-toe"));
                startActivity(intent);
                return true;
            case R.id.action_track://keep track of how many games the user has won, the computer has won, and ties.
                return true;
            case R.id.action_quit://quit function
                finish();
                return true;
        }
        return true;
    }}