package com.example.alexl.puzzle_game;
//for displaying the welcome screen. only has one button to go the next activity
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class WelcomeScreen extends Activity
{
    private Button newGame;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_screen);

        newGame=(Button)findViewById(R.id.button2);

        newGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(WelcomeScreen.this,PuzzleActivity.class)); //go to the next screen
            }
        });


    }


}
