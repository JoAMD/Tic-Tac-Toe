package com.example.joel.tictactoe;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class GameActivity extends AppCompatActivity {

    private ArrayList<ImageView> boxes;
    private int ctr = 0, pos = -1, clickCount = 0;
    private Button newGameBtn;
    private TextView player1, player2;
    private String gameMode, sidePlayer, sideAI = "O", player1Name, player2Name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        //Getting Intent
        Intent data = getIntent();
        gameMode = data.getStringExtra("gameMode");
        sidePlayer = data.getStringExtra("sideChosen");
        if(sidePlayer.equals("O"))
            sideAI = "X";

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
                            Toast.makeText(GameActivity.this, String.format("%s wins in alt diagonal", findPlayerName(2)), Toast.LENGTH_SHORT).show();
                            for(int j = 0; j < 9; ++j) {
                                boxes.get(j).setClickable(false);
                            }
                            return;
                        }
                    }
                    if(boxes.get(0).getTag().equals(boxes.get(0 + 4).getTag()) && boxes.get(0).getTag().equals(boxes.get(0 + 8).getTag()) && boxes.get(0 + 4).getTag().equals(boxes.get(0 + 8).getTag())) {
                        if(!boxes.get(0).getTag().equals("N")) {
                            Toast.makeText(GameActivity.this, String.format("%s wins in main diagonal", findPlayerName(0)), Toast.LENGTH_SHORT).show();
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
                                Toast.makeText(GameActivity.this, String.format("%s wins in column %d", findPlayerName(tempI), tempI + 1), Toast.LENGTH_SHORT).show();
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
                                Toast.makeText(GameActivity.this, String.format("%s wins in row %d", findPlayerName(temp2I), (temp2I / 3) + 1), Toast.LENGTH_SHORT).show();
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

                    if(gameMode.equals("AI") && sidePlayer.equals("O")){
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
                    if(gameMode.equals("AI") && sidePlayer.equals("X") && ctr % 2 == 1){
                        nextMove();
                    }
                    else if(gameMode.equals("AI") && sidePlayer.equals("O") && ctr % 2 == 0){
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
                if(gameMode.equals("AI") && sidePlayer.equals("O")){
                    boxes.get(4).performClick();
                }
            }
        });


        //Assigning sides in case its "with AI" (if its "with a friend" choose side activity is skipped
        if(gameMode.equals("AI") && sidePlayer.equals("O")){
            boxes.get(4).performClick();
        }

    }


    //---------------------Function to get Name of winner--------------------
    public String findPlayerName(int i){
        if(gameMode.equals("AI") && sidePlayer.equals("O"))
            return boxes.get(i).getTag()=="X" ? player2Name : player1Name;
        else
            return boxes.get(i).getTag()=="X" ? player1Name : player2Name;
    }



    //-------------------------------------------Core code of the AI-------------------------------------------
    public void nextMove() {
        clickCount = 0;


        //<--!> Start of MAIN OFFENSIVE LOGIC
        //diagonals
        chk(0, 8, -1);
        if(clickCount >= 1)
            return;
        chk(2, 6, -1);
        if(clickCount >= 1)
            return;

        //sides
        chk(0, 2, -1);
        if(clickCount >= 1)
            return;
        chk(2, 8, -1);
        if(clickCount >= 1)
            return;
        chk(6, 8, -1);
        if(clickCount >= 1)
            return;
        chk(0, 6, -1);
        if(clickCount > 1)
            return;

        //cross
        chk(1, 7, -1);
        if(clickCount >= 1)
            return;
        chk(3, 5, -1);
        if(clickCount >= 1)
            return;
        //<--!> End of MAIN OFFENSIVE LOGIC


        //<--!> Start of MAIN DEFENSIVE LOGIC
        //diagonals
        chk(0, 8, "Def");
        if(clickCount >= 1)
            return;
        chk(2, 6, "Def");
        if(clickCount >= 1)
            return;

        //sides
        chk(0, 2, "Def");
        if(clickCount >= 1)
            return;
        chk(2, 8, "Def");
        if(clickCount >= 1)
            return;
        chk(6, 8, "Def");
        if(clickCount >= 1)
            return;
        chk(0, 6, "Def");
        if(clickCount > 1)
            return;

        //cross
        chk(1, 7, "Def");
        if(clickCount >= 1)
            return;
        chk(3, 5, "Def");
        if(clickCount >= 1)
            return;
        //<--!> End of MAIN DEFENSIVE LOGIC


        //<--!> Start of ALT OFFENSIVE LOGIC
        //diagonals
        chk(0, 8);
        if(clickCount >= 1)
            return;
        chk(2, 6);
        if(clickCount >= 1)
            return;

        //sides
        chk(0, 2);
        if(clickCount >= 1)
            return;
        chk(2, 8);
        if(clickCount >= 1)
            return;
        chk(6, 8);
        if(clickCount >= 1)
            return;
        chk(0, 6);
        if(clickCount > 1)
            return;

        //cross
        chk(1, 7);
        if(clickCount >= 1)
            return;
        chk(3, 5);
        if(clickCount >= 1)
            return;
        //<--!> End of ALT OFFENSIVE LOGIC

        //Defensive logic for O
        if(sidePlayer.equals("X") && boxes.get(4).getTag().equals("N")){
            boxes.get(4).performClick();
        }
        else if(sidePlayer.equals("X") && boxes.get(4).getTag().equals("O")) {
            //diagonal
            if(checkIfCellFree(0))
                clickIfLineFree(0, 8);
            if (clickCount >= 1)
                return;
            if(checkIfCellFree(2))
                clickIfLineFree(2, 6);
            if (clickCount >= 1)
                return;
            /*
            if(checkIfCellFree(6))
                clickIfLineFree(6, 2);
            if (clickCount >= 1)
                return;
            if(checkIfCellFree(8))
                clickIfLineFree(8, 0);
            if (clickCount >= 1)
                return;
                */

            //cross
            if(checkIfCellFree(1))
                clickIfLineFree(1, 7);
            if (clickCount >= 1)
                return;
            if(checkIfCellFree(3))
                clickIfLineFree(3, 5);
            if (clickCount >= 1)
                return;
            /*
            if(checkIfCellFree(5))
                clickIfLineFree(5, 3);
            if (clickCount >= 1)
                return;
            if(checkIfCellFree(7))
                clickIfLineFree(7, 1);
            if (clickCount >= 1)
                return;
            */
        }
        else if(boxes.get(8).getTag().equals("N"))
            boxes.get(8).performClick();
        else{
            Random rand = new Random();
            int k = 0;
            while (true) {
                pos = rand.nextInt(8);
                if (boxes.get(pos).getTag().equals("N")) {
                    Toast.makeText(this, "Random click", Toast.LENGTH_SHORT).show();
                    boxes.get(pos).performClick();
                    break;
                }
                ++k;
            }
        }

    }

    //MAIN OFFENSE
    public void chk(int a, int b, int q){
        int c = (a + b) / 2, temp;
        for(int i = 0; i < 3; ++i) {
            if (boxes.get(a).getTag().equals(boxes.get(b).getTag()) && boxes.get(a).getTag().equals(sideAI) && boxes.get(c).getTag().equals("N")) {
                Toast.makeText(this, String.format("clicked logically in main Att mode! at box %d", c), Toast.LENGTH_SHORT).show();
                clickCount++;
                boxes.get(c).performClick();
                return;
            }
            temp = c;
            c = a;
            a = b;
            b = temp;
        }
    }
    //DEFENSE
    public void chk(int a, int b, String mode){
        int c = (a + b) / 2, temp;
        for(int i = 0; i < 3; ++i) {
            if (boxes.get(a).getTag().equals(boxes.get(b).getTag()) && boxes.get(a).getTag().equals(sidePlayer) && boxes.get(c).getTag().equals("N")) {
                Toast.makeText(this, String.format("clicked logically in Def mode! at box %d", c), Toast.LENGTH_SHORT).show();
                clickCount++;
                boxes.get(c).performClick();
                return;
            }
            temp = c;
            c = a;
            a = b;
            b = temp;
        }
    }
    //ALT OFFENSE
    public void chk(int a, int b){
        int c = (a + b) / 2, temp;
        for(int i = 0; i < 3; ++i) {
            if (boxes.get(a).getTag().equals(boxes.get(b).getTag()) && boxes.get(a).getTag().equals("N") && boxes.get(c).getTag().equals(sideAI)) {
                Toast.makeText(this, String.format("clicked logically in alt Att mode! at box %d and b=%d", a, b), Toast.LENGTH_SHORT).show();
                clickCount++;
                boxes.get(a).performClick();
                return;
            }
            temp = c;
            c = a;
            a = b;
            b = temp;
        }
    }


    public boolean checkIfCellFree(int x) {
        return boxes.get(x).getTag().equals("N");
    }

    public void clickIfLineFree(int toClick, int endOfLine){
        if(!boxes.get(endOfLine).getTag().equals(sidePlayer) && !boxes.get(endOfLine/2 + toClick/2).getTag().equals(sidePlayer)){
            Toast.makeText(this, "clicked if free", Toast.LENGTH_SHORT).show();
            clickCount++;
            boxes.get(toClick).performClick();
        }
    }
}
