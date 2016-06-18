package edu.skku.teama.imagebingo.game;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import edu.skku.teama.imagebingo.R;

public class Game extends AppCompatActivity {
    private int imageID;
    private HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
    private TextView state;
    private ImageView selectedImage;
    private TextView stateDetailed;
    private Button check;
    private ArrayList<ImageButton> bingo;
    private int numOfBingo;
    private ArrayList<Integer> checkBingo00;
    private ArrayList<Integer> checkBingo01;
    private ArrayList<Integer> checkBingo02;
    private ArrayList<Integer> checkBingo03;
    private ArrayList<Integer> checkBingo04;
    private ArrayList<Integer> checkBingo05;
    private ArrayList<Integer> checkBingo06;
    private ArrayList<Integer> checkBingo07;
    private ArrayList<Integer> checkBingo08;
    private ArrayList<Integer> checkBingo09;

    private static final int[] BINGO_IDS = {
            R.id.bingo00,
            R.id.bingo01,
            R.id.bingo02,
            R.id.bingo03,
            R.id.bingo04,
            R.id.bingo05,
            R.id.bingo06,
            R.id.bingo07,
            R.id.bingo08,
            R.id.bingo09,
            R.id.bingo10,
            R.id.bingo11,
            R.id.bingo12,
            R.id.bingo13,
            R.id.bingo14,
            R.id.bingo15,
    };
    private static final int[] IMAGE_IDS = {
            R.drawable.image_00,
            R.drawable.image_01,
            R.drawable.image_02,
            R.drawable.image_03,
            R.drawable.image_04,
            R.drawable.image_05,
            R.drawable.image_06,
            R.drawable.image_07,
            R.drawable.image_08,
            R.drawable.image_09,
            R.drawable.image_10,
            R.drawable.image_11,
            R.drawable.image_12,
            R.drawable.image_13,
            R.drawable.image_14,
            R.drawable.image_15,
    };

    public void CheckBingo(int i) {
        switch (i/4) {
            case 0:
                checkBingo00.add(i);
                if(checkBingo00.size() == 4) numOfBingo++;
                break;
            case 1:
                checkBingo01.add(i);
                if(checkBingo01.size() == 4) numOfBingo++;
                break;
            case 2:
                checkBingo02.add(i);
                if(checkBingo02.size() == 4) numOfBingo++;
                break;
            case 3:
                checkBingo03.add(i);
                if(checkBingo03.size() == 4) numOfBingo++;
                break;
        }
        switch (i%4) {
            case 0:
                checkBingo04.add(i);
                if(checkBingo04.size() == 4) numOfBingo++;
                break;
            case 1:
                checkBingo05.add(i);
                if(checkBingo05.size() == 4) numOfBingo++;
                break;
            case 2:
                checkBingo06.add(i);
                if(checkBingo06.size() == 4) numOfBingo++;
                break;
            case 3:
                checkBingo07.add(i);
                if(checkBingo07.size() == 4) numOfBingo++;
                break;
        }
        if(i/4 == i%4) {
            checkBingo08.add(i);
            if(checkBingo08.size() == 4) numOfBingo++;
        }
        if((i/4 + i%4) == 3) {
            checkBingo09.add(i);
            if(checkBingo09.size() == 4) numOfBingo++;
        }
    }
    public void CheckMethod(final int i) {
        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(numOfBingo < 3) {
                    check.setEnabled(false);
                    check.setBackgroundColor(Color.parseColor("#c4c4cf"));
                    selectedImage.setImageResource(R.drawable.selected);
                    bingo.get(i).setEnabled(false);
                    bingo.get(i).setImageResource(R.drawable.selected);
                    CheckBingo(i);
                    if (numOfBingo < 3) {
                        stateDetailed.setText("그림을 선택하세요");
                    } else {
                        state.setText("승리!!!!");
                        stateDetailed.setText("축하합니다.");
                        for (int j = 0; j < 16; j++) {
                            bingo.get(j).setEnabled(false);
                        }
                        check.setEnabled(true);
                        check.setBackgroundColor(Color.parseColor("#96CDCD"));
                        Toast.makeText(getApplicationContext(), "Victory!", Toast.LENGTH_SHORT).show();
                        getWindow().getDecorView().setBackgroundColor(Color.parseColor("#d15354"));
                    }
                } else {
                    finish();
                }
            }
        });
    }
    public void ButtonMethod(final int i) {
        bingo.get(i).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check.setEnabled(true);
                check.setBackgroundColor(Color.parseColor("#96CDCD"));
                imageID = map.get(BINGO_IDS[i]);
                selectedImage.setImageResource(imageID);
                stateDetailed.setText("선택한 그림이 맞으면 확인 버튼을 누르세요");
                CheckMethod(i);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_relative);
        getWindow().getDecorView().setBackgroundColor(Color.parseColor("#c4c4cf"));
        ArrayList<Integer> ran = new ArrayList<Integer>();
        for(int i = 0; i < 16; i++) {
            ran.add(i);
        }
        state = (TextView) findViewById(R.id.state);
        selectedImage = (ImageView) findViewById(R.id.selectedImage);
        stateDetailed = (TextView) findViewById(R.id.stateDetailed);
        check = (Button) findViewById(R.id.check);
        bingo = new ArrayList<ImageButton>();
        numOfBingo = 0;
        checkBingo00 = new ArrayList<Integer>();
        checkBingo01 = new ArrayList<Integer>();
        checkBingo02 = new ArrayList<Integer>();
        checkBingo03 = new ArrayList<Integer>();
        checkBingo04 = new ArrayList<Integer>();
        checkBingo05 = new ArrayList<Integer>();
        checkBingo06 = new ArrayList<Integer>();
        checkBingo07 = new ArrayList<Integer>();
        checkBingo08 = new ArrayList<Integer>();
        checkBingo09 = new ArrayList<Integer>();

        final Random bimag = new Random();
        Integer b[] = new Integer[16];
        int img[] = new int[16];
        for(int i = 0; i < 16; i++) {
            int j = bimag.nextInt(ran.size());
            b[i] = ran.get(j);
            ran.remove(j);
        }

        for(int i = 0; i < 16; i++) {
            ImageButton button = (ImageButton)findViewById(BINGO_IDS[i]);
            button.setImageResource(IMAGE_IDS[b[i]]);
            map.put(BINGO_IDS[i], IMAGE_IDS[b[i]]);
            bingo.add(button);
        }

        check.setEnabled(false);
        check.setBackgroundColor(Color.parseColor("#c4c4cf"));
        selectedImage.setImageResource(R.drawable.selected);
        stateDetailed.setText("그림을 선택하세요");

        for(int i = 0; i < 16; i++) {
            ButtonMethod(i);
        }
    }
}