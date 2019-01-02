package com.example.joel.tictactoe;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button withAI, withAFriend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initialising variables
        withAI = findViewById(R.id.withAI);
        withAFriend = findViewById(R.id.withAFriend);

        final Intent GameActivity = new Intent(MainActivity.this, com.example.joel.tictactoe.GameActivity.class);

        //Setting OnClickListener
        withAI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GameActivity.putExtra("gameMode", "AI");
                startActivity(GameActivity);

            }
        });
        withAFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GameActivity.putExtra("gameMode", "Friend");
                startActivity(GameActivity);
            }
        });
    }
}
