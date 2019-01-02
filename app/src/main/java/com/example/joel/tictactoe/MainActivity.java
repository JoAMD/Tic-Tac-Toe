package com.example.joel.tictactoe;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button withAI, withAFriend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initialising variables
        withAI = findViewById(R.id.withAI);
        withAFriend = findViewById(R.id.withAFriend);


        //Setting OnClickListener
        withAI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Initialising Intent
                Intent ChooseSide = new Intent(MainActivity.this, ChooseSideActivity.class);
                ChooseSide.putExtra("gameMode", "AI");
                startActivity(ChooseSide);

            }
        });
        withAFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Initialising Intent
                Intent gameActivity = new Intent(MainActivity.this, GameActivity.class);
                gameActivity.putExtra("gameMode", "Friend");
                startActivity(gameActivity);
            }
        });
    }
}
