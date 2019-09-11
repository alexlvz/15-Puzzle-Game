package com.example.alexl.puzzle_game;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Timer;
import java.util.TimerTask;

public class PuzzleActivity extends AppCompatActivity {

    private Button newGame;
    private GameBoard game;
    private TextView moves;
    private int moveCounter; //count the moves...
    private int gameTimeVal; //to display the time taken to win
    private Timer time; //for time counter
    private  TextView labels[][] = new TextView[4][4];
    private int []labelColors={Color.parseColor("#b4fbff00"),Color.parseColor("#b4b40039"),Color.parseColor("#b400ffe1"),Color.parseColor("#b41aff00"), Color.parseColor("#b4ff8400"),Color.parseColor("#b49d41ff"),Color.parseColor("#b400ffa1"),Color.parseColor("#b4ff0000")
            ,Color.parseColor("#b46b00b7"),Color.parseColor("#b4007c08"),Color.parseColor("#b4ffa0d4"),Color.parseColor("#b4f8ffab"),Color.parseColor("#b43974dc"),Color.parseColor("#b48e4e00"),Color.parseColor("#b43f3939"),Color.parseColor("#00000000")};
    // the colors array is for changing the numbers with their color

    public void newGame() //make a new game
    {
        game = new GameBoard();
        printBoard(); //function for printing the board

        for (int i = 0; i < labels.length; i++) { //set clickable so that in the end of the game the will become unclickable
            for (int j = 0; j < labels.length; j++)
            {
                labels[i][j].setClickable(true);
            }
        }
        moveCounter=0;
        gameTimeVal=0;
        moves.setText("Moves Made: " + moveCounter);
    }
    public void printBoard() //print the board by copying the values from game class to this class and changing their color
    {
        int label;
        for (int i = 0; i < labels.length; i++) {
            for (int j = 0; j < labels.length; j++)
            {
                if (game.gameMatrix[i][j] == 16) //empty spot
                {
                    label = game.gameMatrix[i][j];
                    labels[i][j].setBackgroundColor(labelColors[label - 1]);
                    labels[i][j].setText("");
                }
                else
                {
                    label = game.gameMatrix[i][j];
                    labels[i][j].setText(String.valueOf(game.gameMatrix[i][j]));
                    labels[i][j].setBackgroundColor(labelColors[label - 1]);
                }

            }
        }
    }
    public void GameTime() // function to count the time till victory
    {
        time=new Timer();
        time.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                runOnUiThread(new Runnable() {
                    public void run() {
                        gameTimeVal++;
                    }
                });
            }
        }, 1000, 1000);
    }
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puzzle);

        Typeface font=Typeface.createFromAsset(getAssets(), "fonts/Torture.ttf");
        newGame = (Button) findViewById(R.id.button);
        moves=(TextView) findViewById(R.id.moves);

        //=========== initialize the labels array ============

        labels[0][0]=(TextView) findViewById(R.id.textView2);
        labels[0][1]=(TextView) findViewById(R.id.textView3);
        labels[0][2]=(TextView) findViewById(R.id.textView4);
        labels[0][3]=(TextView) findViewById(R.id.textView5);
        labels[1][0]=(TextView) findViewById(R.id.textView6);
        labels[1][1]=(TextView) findViewById(R.id.textView7);
        labels[1][2]=(TextView) findViewById(R.id.textView8);
        labels[1][3]=(TextView) findViewById(R.id.textView9);
        labels[2][0]=(TextView) findViewById(R.id.textView10);
        labels[2][1]=(TextView) findViewById(R.id.textView11);
        labels[2][2]=(TextView) findViewById(R.id.textView12);
        labels[2][3]=(TextView) findViewById(R.id.textView13);
        labels[3][0]=(TextView) findViewById(R.id.textView14);
        labels[3][1]=(TextView) findViewById(R.id.textView15);
        labels[3][2]=(TextView) findViewById(R.id.textView16);
        labels[3][3]=(TextView) findViewById(R.id.textView17);

        //====================================================

        newGame(); //start a new game
        GameTime(); //time counter starts

        newGame.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) { //when clicking on new game will jump a pop-up box to choose what to do

                AlertDialog.Builder builder = new AlertDialog.Builder(PuzzleActivity.this);
                builder.setTitle("RESET GAME");
                builder.setMessage("Are you sure?");

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        newGame();
                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });

        for (int i = 0; i < labels.length; i++) //same listener for all number buttons. after each move it will print the board again
                {
                    for (int j = 0; j < labels.length; j++) {
                        labels[i][j].setOnClickListener(new View.OnClickListener() {
                            public void onClick(View v)
                            {
                                    for (int k = 0; k < labels.length; k++) {
                                        for (int m = 0; m < labels.length; m++) {
                                            if (labels[k][m].getId() == v.getId())//find the spot to move
                                            {
                                                if(game.isMoveable(k,m))//for the counter of moves
                                                {
                                                    moveCounter++;
                                                    moves.setText("Moves Made: " + moveCounter);
                                                }
                                                game.move(k,m);
                                            }
                                        }
                                    printBoard();

                                    if (game.isWon())
                                    {
                                        time.cancel(); //stop the timer
                                        for (int i = 0; i < labels.length; i++) {
                                            for (int j = 0; j < labels.length; j++)
                                            {
                                                labels[i][j].setClickable(false); //disable clicking on the numbers till new game starts
                                            }
                                        }
                                        Toast.makeText(PuzzleActivity.this,"You won the the game! It took you " + gameTimeVal +" seconds!", Toast.LENGTH_LONG).show(); //victory message

                                    }
                                }
                            }
                        });
                    }
                }
        }
}
