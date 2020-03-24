package com.hb.numberpuzzle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;

import java.lang.reflect.Array;
import java.util.Random;

import static java.lang.Thread.sleep;

public class MainActivity extends AppCompatActivity {
    Button btnPlay;
    Button[] buttons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createButtons();

        btnPlay = findViewById(R.id.btnPlay);

        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String btnTxt = String.valueOf(btnPlay.getText());
                randomNumber(checkCellEmpty());
                if(btnTxt.equals("Play")){
                    for(int i = 0; i < 300; i++){
                        randomNumber(checkCellEmpty());
                    }
                    btnPlay.setText("New game");
                }

                if(btnTxt.equals("New game")){
                    for(int i = 1; i < buttons.length; i++){
                        buttons[i-1].setText("" + i);
                    }
                    buttons[buttons.length - 1].setText("");
                    btnPlay.setText("Play");
                }
                if(btnTxt.equals("Pause")){
                    btnPlay.setText("Continue");
                    btnPlay.setTextSize(30);
                }
                if(btnTxt.equals("Continue")){
                    btnPlay.setText("Pause");

                }
            }
        });

        for (int i = 0; i < buttons.length; i++) {
            final int finalI = i;
            buttons[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int move = isMove(finalI);
                    switch (move){
                        case 1: moveUp(finalI); break;
                        case 2: moveRight(finalI); break;
                        case 3: moveDown(finalI); break;
                        case 4: moveLeft(finalI); break;
                        default:
                    }
                    if(checkRS()) alertWin();
                }
            });
        }
    }

    public void alertWin(){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Congratulations, You Won!");
        alert.setIcon(R.mipmap.youwin1);
        alert.setMessage("Best Time: unknow\n\nTime: unknow");
        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alert.show();

        for(int i = 1; i < buttons.length; i++){
            buttons[i-1].setText("" + i);
        }
        buttons[buttons.length - 1].setText("");
        btnPlay.setText("Play");
    }

    public  void createButtons(){
        buttons = new Button[16];

        for(int i = 0; i < buttons.length; i++){
            String buttonID = "number" + i;
            int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
            buttons[i] = ((Button) findViewById(resID));
        }
    }

    public void randomNumber(int cellEmpty){
        Random random = new Random();
        switch(cellEmpty) {
            case 0:
                switch (random.nextInt(2)){
                    case 0: moveRight(cellEmpty);
                        break;
                    case 1: moveDown(cellEmpty);
                        break;
                    default:
                }
                break;
            case 1: case 2:
                switch (random.nextInt(3)){
                    case 0: moveRight(cellEmpty);
                        break;
                    case 1: moveDown(cellEmpty);
                        break;
                    case 2: moveLeft(cellEmpty);
                        break;
                    default:
                }
                break;
            case 3:
                switch (random.nextInt(2)){
                    case 0: moveLeft(cellEmpty);
                        break;
                    case 1: moveDown(cellEmpty);
                        break;
                    default:
                }
                break;
            case 4: case 8:
                switch (random.nextInt(3)){
                    case 0: moveRight(cellEmpty);
                        break;
                    case 1: moveUp(cellEmpty);
                        break;
                    case 2: moveDown(cellEmpty);
                        break;
                    default:
                }
                break;
            case 7: case 11:
                switch (random.nextInt(3)){
                    case 0: moveLeft(cellEmpty);
                        break;
                    case 1: moveDown(cellEmpty);
                        break;
                    case 2: moveUp(cellEmpty);
                        break;
                    default:
                }
                break;
            case 12:
                switch (random.nextInt(2)){
                    case 0: moveUp(cellEmpty);
                        break;
                    case 1: moveRight(cellEmpty);
                        break;
                    default:
                }
                break;
            case 15:
                switch (random.nextInt(2)){
                    case 0: moveUp(cellEmpty);
                        break;
                    case 1: moveLeft(cellEmpty);
                        break;
                    default:
                }
                break;
            case 5: case 6: case 9: case 10:
                switch (random.nextInt(4)){
                    case 0: moveLeft(cellEmpty); break;
                    case 1: moveRight(cellEmpty); break;
                    case 2: moveUp(cellEmpty); break;
                    case 3: moveDown(cellEmpty); break;
                    default:
                }
                break;

            case 13: case 14:
                switch (random.nextInt(3)){
                    case 0: moveRight(cellEmpty); break;
                    case 1: moveUp(cellEmpty); break;
                    case 2: moveLeft(cellEmpty); break;
                    default:
                }
                break;

            default:
        }
    }
    public void moveRight(int position){
        String tmp = String.valueOf(buttons[position].getText());
        buttons[position].setText(buttons[position + 1].getText());
        buttons[position + 1].setText(tmp);
    }
    public void moveLeft(int position){
        String tmp = String.valueOf(buttons[position].getText());
        buttons[position].setText(buttons[position - 1].getText());
        buttons[position - 1].setText(tmp);
    }
    public void moveUp(int position){
        String tmp = String.valueOf(buttons[position].getText());
        buttons[position].setText(buttons[position - 4].getText());
        buttons[position - 4].setText(tmp);
    }
    public void moveDown(int position){
        String tmp = String.valueOf(buttons[position].getText());
        buttons[position].setText(buttons[position + 4].getText());
        buttons[position + 4].setText(tmp);
    }

    // 0: do nothing
    // 1: move up
    // 2: move right
    // 3: move down
    // 4: move left
    public int isMove(int cur_position){
        switch(cur_position) {
            case 0:
                if((buttons[cur_position + 1].getText()).equals("")) return 2;
                if((buttons[cur_position + 4].getText()).equals("")) return 3;
                break;
            case 1: case 2:
                if((buttons[cur_position - 1].getText()).equals("")) return 4;
                if((buttons[cur_position + 1].getText()).equals("")) return 2;
                if((buttons[cur_position + 4].getText()).equals("")) return 3;
                break;
            case 3:
                if((buttons[cur_position - 1].getText()).equals("")) return 4;
                if((buttons[cur_position + 4].getText()).equals("")) return 3;
                break;
            case 4: case 8:
                if((buttons[cur_position - 4].getText()).equals("")) return 1;
                if((buttons[cur_position + 1].getText()).equals("")) return 2;
                if((buttons[cur_position + 4].getText()).equals("")) return 3;
                break;
            case 7: case 11:
                if((buttons[cur_position - 1].getText()).equals("")) return 4;
                if((buttons[cur_position - 4].getText()).equals("")) return 1;
                if((buttons[cur_position + 4].getText()).equals("")) return 3;
                break;
            case 12:
                if((buttons[cur_position - 4].getText()).equals("")) return 1;
                if((buttons[cur_position + 1].getText()).equals("")) return 2;
                break;
            case 15:
                if((buttons[cur_position - 1].getText()).equals("")) return 4;
                if((buttons[cur_position - 4].getText()).equals("")) return 1;
                break;
            case 5: case 6: case 9: case 10:
                if((buttons[cur_position - 4].getText()).equals("")) return 1;
                if((buttons[cur_position - 1].getText()).equals("")) return 4;
                if((buttons[cur_position + 1].getText()).equals("")) return 2;
                if((buttons[cur_position + 4].getText()).equals("")) return 3;
                break;

            case 13: case 14:
                if((buttons[cur_position - 1].getText()).equals("")) return 4;
                if((buttons[cur_position + 1].getText()).equals("")) return 2;
                if((buttons[cur_position - 4].getText()).equals("")) return 1;
                break;

            default:
        }
        return 0;
    }

    public int checkCellEmpty(){
        for (int i = 0; i < 16; i++){
            String number = String.valueOf(buttons[i].getText());
            if(number.equals(""))
                return i;
        }
        return 0;
    }
    public boolean checkRS(){
        int count = 0;
        for(int i = 1; i < buttons.length; i++){
            if ((buttons[i - 1].getText()).equals("" + i)){
                count++;
            }
        }
        if (count == 15) return true;
        return false;
    }
}
