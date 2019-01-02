package com.example.joel.tictactoe;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class ChooseSideActivity extends AppCompatActivity {

    private Button continueBtn;
    private RadioButton chooseX, chooseO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_side);

        //Getting Intent
        final Intent dataGameMode = getIntent();

        //Initialising variables
        continueBtn = (Button)findViewById(R.id.choose_side_continueBtn);
        chooseX = (RadioButton)findViewById(R.id.chooseX);
        chooseO = (RadioButton)findViewById(R.id.chooseO);

        //Setting OnClickListener

        chooseX.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseO.setChecked(false);
            }
        });
        chooseO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseX.setChecked(false);
            }
        });


        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Creating Intent
                Intent GameActivity = new Intent(ChooseSideActivity.this, GameActivity.class);
                GameActivity.putExtra("gameMode", dataGameMode.getStringExtra("gameMode"));
                if(chooseX.isChecked())
                    GameActivity.putExtra("sideChosen", "X");
                else if(chooseO.isChecked())
                    GameActivity.putExtra("sideChosen", "O");
                else{
                    Toast.makeText(ChooseSideActivity.this, "Choose a side!", Toast.LENGTH_SHORT).show();
                    return;
                }
                startActivity(GameActivity);
            }
        });

    }
}
