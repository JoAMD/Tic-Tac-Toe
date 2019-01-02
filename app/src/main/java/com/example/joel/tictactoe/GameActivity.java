package com.example.joel.tictactoe;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Random;

public class GameActivity extends AppCompatActivity {

    private ArrayList<ImageView> boxes;
    private int ctr = 0, pos = -1;
    private Button newGameBtn;
    private TextView player1, player2;
    private String gameMode, sideChosen, player1Name, player2Name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        //Getting Intent
        Intent data = getIntent();
        gameMode = data.getStringExtra("gameMode");
        sideChosen = data.getStringExtra("sideChosen");

        //Initialising Variables
        boxes = new ArrayList<>();

        boxes.add((ImageView)findViewById(R.id.box1));
        boxes.add((ImageView)findViewById(R.id.box2));
        boxes.add((ImageView)findViewById(R.id.box3));
        boxes.add((ImageView)findViewById(R.id.box4));
        boxes.add((ImageView)findViewById(R.id.box5));
        boxes.add((ImageView)findViewById(R.id.box6));
        boxes.add((ImageView)findViewById(R.id.box7));
        boxes.add((ImageView)findViewById(R.id.box8));
        boxes.add((ImageView)findViewById(R.id.box9));

        newGameBtn = (Button)findViewById(R.id.newGameBtn);

        player1 = (TextView)findViewById(R.id.player1);
        player2 = (TextView)findViewById(R.id.player2);


        //Setting player Names
        player1Name = "Player1";
        player2Name = "Player2";
        if(gameMode.equals("AI")){
            player2Name = "AI";
        }
        player1.setText(player1Name);
        player2.setText(player2Name);



//-----------------------SETTING ONCLICKLISTENERS-----------------------

        for(int i = 0; i < 9; ++i) {
            final int tempI = i;
            boxes.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (ctr % 2 == 0) {
                        boxes.get(tempI).setImageResource(R.drawable.x_blue);
                        boxes.get(tempI).setTag("X");
                        player1.setBackground(null);
                        player2.setBackgroundResource(R.drawable.grey_oval);
                    }
                    else {
                        boxes.get(tempI).setImageResource(R.drawable.o_orange);
                        boxes.get(tempI).setTag("O");
                        player2.setBackground(null);
                        player1.setBackgroundResource(R.drawable.grey_oval);
                    }
                    boxes.get(tempI).setClickable(false);
                    ctr++;


                    //-----------------------DISABLING ONCLICKLISTENERS / GAME OVER CONDITIONS-----------------------

                    //-------------DIAGONAL WISE-------------
                    if(boxes.get(2).getTag().equals(boxes.get(4).getTag()) && boxes.get(2).getTag().equals(boxes.get(6).getTag()) && boxes.get(4).getTag().equals(boxes.get(6).getTag())){
                        if(!boxes.get(2).getTag().equals("N")){
                            Toast.makeText(GameActivity.this, String.format("%s wins in alt diagonal", playerNamefinder(2)), Toast.LENGTH_SHORT).show();
                            for(int j = 0; j < 9; ++j) {
                                boxes.get(j).setClickable(false);
                            }
                            return;
                        }
                    }
                    if(boxes.get(0).getTag().equals(boxes.get(0 + 4).getTag()) && boxes.get(0).getTag().equals(boxes.get(0 + 8).getTag()) && boxes.get(0 + 4).getTag().equals(boxes.get(0 + 8).getTag())) {
                        if(!boxes.get(0).getTag().equals("N")) {
                            Toast.makeText(GameActivity.this, String.format("%s wins in main diagonal", playerNamefinder(0)), Toast.LENGTH_SHORT).show();
                            for(int j = 0; j < 9; ++j) {
                                boxes.get(j).setClickable(false);
                            }
                            return;
                        }
                    }

                    //--------------COLUMN WISE--------------
                    for(int i = 0; i < 3; ++i){
                        final int tempI = i;
                        if(boxes.get(tempI).getTag().equals(boxes.get(tempI + 3).getTag()) && boxes.get(tempI).getTag().equals(boxes.get(tempI + 6).getTag()) && boxes.get(tempI + 3).getTag().equals(boxes.get(tempI + 6).getTag())){
                            if(!boxes.get(tempI).getTag().equals("N")) {
                                Toast.makeText(GameActivity.this, String.format("%s wins in column %d", playerNamefinder(tempI), tempI + 1), Toast.LENGTH_SHORT).show();
                                for(int j = 0; j < 9; ++j) {
                                    boxes.get(j).setClickable(false);
                                }
                                return;
                            }
                        }
                    }
                    //----------------ROW WISE----------------
                    for(int i = 0; i < 9; i+=3){
                        final int temp2I = i;
                        if(boxes.get(temp2I).getTag().equals(boxes.get(temp2I + 1).getTag()) && boxes.get(temp2I).getTag().equals(boxes.get(temp2I + 2).getTag()) && boxes.get(temp2I + 1).getTag().equals(boxes.get(temp2I + 2).getTag())){
                            if(!boxes.get(temp2I).getTag().equals("N")) {
                                Toast.makeText(GameActivity.this, String.format("%s wins in row %d", playerNamefinder(temp2I), (temp2I / 3) + 1), Toast.LENGTH_SHORT).show();
                                for(int j = 0; j < 9; ++j) {
                                    boxes.get(j).setClickable(false);
                                }
                                return;
                            }
                        }
                    }


                    //-----------------------------DRAW CONDITION-----------------------------
                    /*
                    if(ctr == 9){
                        Toast.makeText(GameActivity.this, "The game is a draw!", Toast.LENGTH_SHORT).show();
                        player2.setBackground(null);
                        for(int i = 0; i < 9; ++i)
                            boxes.get(i).setClickable(false);
                        return;
                    }
                    */

                    if(gameMode.equals("AI") && sideChosen.equals("O")){
                        if(ctr == 10){
                            Toast.makeText(GameActivity.this, "The game is a draw!", Toast.LENGTH_SHORT).show();
                            player2.setBackground(null);
                            for(int i = 0; i < 9; ++i)
                                boxes.get(i).setClickable(false);
                            return;
                        }
                    }
                    else if(ctr == 9){
                        Toast.makeText(GameActivity.this, "The game is a draw!", Toast.LENGTH_SHORT).show();
                        player2.setBackground(null);
                        for(int i = 0; i < 9; ++i)
                            boxes.get(i).setClickable(false);
                        return;
                    }


                    //------------------------------------------------AI IF CONDITION------------------------------------------------
                    if(gameMode.equals("AI") && sideChosen.equals("X") && ctr % 2 == 1){
                        nextMove();
                    }
                    else if(gameMode.equals("AI") && sideChosen.equals("O") && ctr % 2 == 0){
                        nextMove();
                    }


                }

            });
        }

        newGameBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ctr = 0;
                for(int i = 0; i < 9; ++i) {
                    boxes.get(i).setImageDrawable(null);
                    boxes.get(i).setClickable(true);
                    boxes.get(i).setTag("N");
                }
                if(gameMode.equals("AI") && sideChosen.equals("O")){
                    boxes.get(4).performClick();
                }
            }
        });


        //Assigning sides in case its "with AI" (if its "with a friend" choose side activity is skipped
        if(gameMode.equals("AI") && sideChosen.equals("O")){
            boxes.get(4).performClick();
        }

    }


    //-------------------------------------------Core code of the AI-------------------------------------------
    public void nextMove() {
        Random rand = new Random();
        int k = 0;
        while (true) {
            pos = rand.nextInt(9);
            if (boxes.get(pos).getTag().equals("N")) {
                boxes.get(pos).performClick();
                break;
            }
            ++k;
        }
    }

    public String playerNamefinder(int i){
        if(gameMode.equals("AI") && sideChosen.equals("O"))
            return boxes.get(i).getTag()=="X" ? player2Name : player1Name;
        else
            return boxes.get(i).getTag()=="X" ? player1Name : player2Name;
    }

}
