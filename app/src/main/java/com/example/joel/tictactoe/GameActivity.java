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

public class GameActivity extends AppCompatActivity {

    private ArrayList<ImageView> boxes;
    private int ctr = 0, i;
    private Button newGameBtn;
    private TextView player1, player2;
    private String gameMode, player1Name, player2Name;

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

        for(i = 0; i < 9; ++i) {
            final int tempI = i;
            boxes.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (ctr % 2 == 0) {
                        boxes.get(tempI).setImageResource(R.drawable.x_blue);
                        boxes.get(tempI).setTag("X");
                        player1.setBackground(null);
                        player2.setBackgroundResource(R.drawable.grey_oval);
                        if(gameMode == "AI"){
                            int pos = nextMove();
                            boxes.get(pos).performClick();
                        }
                    }
                    else {
                        boxes.get(tempI).setImageResource(R.drawable.o_orange);
                        boxes.get(tempI).setTag("O");
                        player2.setBackground(null);
                        player1.setBackgroundResource(R.drawable.grey_oval);
                    }
                    ctr++;
                    boxes.get(tempI).setClickable(false);


                    //-----------------------DISABLING ONCLICKLISTENERS / GAME OVER CONDITIONS-----------------------

                    //-------------DIAGONAL WISE-------------
                    if(boxes.get(2).getTag().equals(boxes.get(4).getTag()) && boxes.get(2).getTag().equals(boxes.get(6).getTag()) && boxes.get(4).getTag().equals(boxes.get(6).getTag())){
                        if(!boxes.get(2).getTag().equals("N")){
                            Toast.makeText(GameActivity.this, String.format("%s wins in alt diagonal", boxes.get(2).getTag()=="X" ? player1Name : player2Name), Toast.LENGTH_SHORT).show();
                            for(int j = 0; j < 9; ++j) {
                                boxes.get(j).setClickable(false);
                            }
                        }
                    }
                    if(boxes.get(0).getTag().equals(boxes.get(0 + 4).getTag()) && boxes.get(0).getTag().equals(boxes.get(0 + 8).getTag()) && boxes.get(0 + 4).getTag().equals(boxes.get(0 + 8).getTag())) {
                        if(!boxes.get(0).getTag().equals("N")) {
                            Toast.makeText(GameActivity.this, String.format("%s wins in main diagonal", boxes.get(0).getTag()=="X" ? player1Name : player2Name), Toast.LENGTH_SHORT).show();
                            for(int j = 0; j < 9; ++j) {
                                boxes.get(j).setClickable(false);
                            }
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
                            }
                        }
                    }


                }
            });
        }

        newGameBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ctr = 0;
                for(i = 0; i < 9; ++i) {
                    boxes.get(i).setImageDrawable(null);
                    boxes.get(i).setClickable(true);
                    boxes.get(i).setTag("N");
                }
            }
        });

    }

    //--------------------------------Core code of the AI--------------------------------
    public int nextMove(){
        return 0;
    }


    /*
// CODE FROM - http://techblog.sharpmind.de/?p=49
    private boolean isImageEqualToRes(ImageView actualImageView,
                                      int expectedDrawable) {

        Drawable expected = getDrawable(expectedDrawable);
        Drawable actual = actualImageView.getDrawable();

        if (expected != null && actual != null) {
            Bitmap expectedBitmap = getBitmapOfDrawable(expected);
            Bitmap actualBitmap = getBitmapOfDrawable(actual);

            return areBitmapsEqual(expectedBitmap, actualBitmap);
        }
        return false;
    }
    private static Bitmap getBitmapOfDrawable(Drawable drawable) {
        BitmapDrawable bd = (BitmapDrawable) drawable;
        Bitmap bitmap = bd.getBitmap();
        return bitmap;
    }
    private static boolean areBitmapsEqual(Bitmap bitmap1, Bitmap bitmap2) {
        // compare two bitmaps by their bytes
        byte[] array1 = BitmapToByteArray(bitmap1);
        byte[] array2 = BitmapToByteArray(bitmap2);
        if (java.util.Arrays.equals(array1, array2)) {
            return true;
        }
        return false;
    }
    private static byte[] BitmapToByteArray(Bitmap bitmap) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
        byte[] result = bos.toByteArray();
        return result;
    }
//CODE FROM - http://techblog.sharpmind.de/?p=49
*/
}