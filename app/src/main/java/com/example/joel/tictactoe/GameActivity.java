package com.example.joel.tictactoe;

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
    private String gameMode = "AI", player1Name, player2Name;
    private boolean isAImoved = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

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
        player1Name = player1.getText() + "";
        player2Name = player2.getText() + "";



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
                            Toast.makeText(GameActivity.this, String.format("%s wins in alt diagonal", boxes.get(2).getTag()=="X" ? player1Name : player2Name), Toast.LENGTH_SHORT).show();
                            for(int j = 0; j < 9; ++j) {
                                boxes.get(j).setClickable(false);
                            }
                            return;
                        }
                    }
                    if(boxes.get(0).getTag().equals(boxes.get(0 + 4).getTag()) && boxes.get(0).getTag().equals(boxes.get(0 + 8).getTag()) && boxes.get(0 + 4).getTag().equals(boxes.get(0 + 8).getTag())) {
                        if(!boxes.get(0).getTag().equals("N")) {
                            Toast.makeText(GameActivity.this, String.format("%s wins in main diagonal", boxes.get(0).getTag()=="X" ? player1Name : player2Name), Toast.LENGTH_SHORT).show();
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
                                Toast.makeText(GameActivity.this, String.format("%s wins in column %d", boxes.get(tempI).getTag()=="X" ? player1Name : player2Name, tempI + 1), Toast.LENGTH_SHORT).show();
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
                                Toast.makeText(GameActivity.this, String.format("%s wins in row %d", boxes.get(temp2I).getTag()=="X" ? player1Name : player2Name, (temp2I / 3) + 1), Toast.LENGTH_SHORT).show();
                                for(int j = 0; j < 9; ++j) {
                                    boxes.get(j).setClickable(false);
                                }
                                return;
                            }
                        }
                    }



                    //------------------------------------------------AI IF CONDITION------------------------------------------------
                    if(gameMode.equals("AI") && ctr % 2 == 1){
                        nextMove();
                    }


                    //-----------------------------DRAW CONDITION-----------------------------
                    if(ctr == 9){
                        Toast.makeText(GameActivity.this, "The game is a draw!", Toast.LENGTH_SHORT).show();
                        player2.setBackground(null);
                        for(int i = 0; i < 9; ++i)
                            boxes.get(i).setClickable(false);
                        return;
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
            }
        });

    }


    //-------------------------------------------Core code of the AI-------------------------------------------
    public void nextMove(){
        Random rand = new Random();
        int k = 0;
        while(k < 10) {
            pos = rand.nextInt(9);
            if (boxes.get(pos).getTag().equals("N")) {
                boxes.get(pos).performClick();
                break;
            }
            ++k;
        }

}
