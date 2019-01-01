package com.example.joel.tictactoe;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent GameActivity = new Intent(MainActivity.this, com.example.joel.tictactoe.GameActivity.class);
        startActivity(GameActivity);
    }
}
