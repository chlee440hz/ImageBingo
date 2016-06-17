package edu.skku.teama.imagebingo.game;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import edu.skku.teama.imagebingo.R;

public class Game extends AppCompatActivity {
    public int imageID;
    HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();

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
            R.drawable.image_00,
            R.drawable.image_00,
            R.drawable.image_00,
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_relative);
        ArrayList<Integer> ran = new ArrayList<Integer>();
        for(int i = 0; i < 16; i++) {
            ran.add(i);
        }
        TextView state = (TextView) findViewById(R.id.state);
        final ImageView selectedImage = (ImageView) findViewById(R.id.selectedImage);
        final TextView stateDetailed = (TextView) findViewById(R.id.stateDetailed);
        final Button check = (Button) findViewById(R.id.check);
        final ArrayList<ImageButton> bingo = new ArrayList<ImageButton>();

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
        selectedImage.setImageResource(R.drawable.logo);
        stateDetailed.setText("그림을 선택하세요");

        bingo.get(0).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check.setEnabled(true);
                check.setBackgroundColor(Color.parseColor("#96CDCD"));
                imageID = map.get(BINGO_IDS[0]);
                selectedImage.setImageResource(imageID);
                stateDetailed.setText("선택한 그림이 맞으면 확인 버튼을 누르세요");
            }
        });
        bingo.get(1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check.setEnabled(true);
                check.setBackgroundColor(Color.parseColor("#96CDCD"));
                imageID = map.get(BINGO_IDS[1]);
                selectedImage.setImageResource(imageID);
                stateDetailed.setText("선택한 그림이 맞으면 확인 버튼을 누르세요");
            }
        });
        bingo.get(2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check.setEnabled(true);
                check.setBackgroundColor(Color.parseColor("#96CDCD"));
                imageID = map.get(BINGO_IDS[2]);
                selectedImage.setImageResource(imageID);
                stateDetailed.setText("선택한 그림이 맞으면 확인 버튼을 누르세요");
            }
        });
        bingo.get(3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check.setEnabled(true);
                check.setBackgroundColor(Color.parseColor("#96CDCD"));
                imageID = map.get(BINGO_IDS[3]);
                selectedImage.setImageResource(imageID);
                stateDetailed.setText("선택한 그림이 맞으면 확인 버튼을 누르세요");
            }
        });
        bingo.get(4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check.setEnabled(true);
                check.setBackgroundColor(Color.parseColor("#96CDCD"));
                imageID = map.get(BINGO_IDS[4]);
                selectedImage.setImageResource(imageID);
                stateDetailed.setText("선택한 그림이 맞으면 확인 버튼을 누르세요");
            }
        });
        bingo.get(5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check.setEnabled(true);
                check.setBackgroundColor(Color.parseColor("#96CDCD"));
                imageID = map.get(BINGO_IDS[5]);
                selectedImage.setImageResource(imageID);
                stateDetailed.setText("선택한 그림이 맞으면 확인 버튼을 누르세요");
            }
        });
        bingo.get(6).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check.setEnabled(true);
                check.setBackgroundColor(Color.parseColor("#96CDCD"));
                imageID = map.get(BINGO_IDS[6]);
                selectedImage.setImageResource(imageID);
                stateDetailed.setText("선택한 그림이 맞으면 확인 버튼을 누르세요");
            }
        });
        bingo.get(7).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check.setEnabled(true);
                check.setBackgroundColor(Color.parseColor("#96CDCD"));
                imageID = map.get(BINGO_IDS[7]);
                selectedImage.setImageResource(imageID);
                stateDetailed.setText("선택한 그림이 맞으면 확인 버튼을 누르세요");
            }
        });
        bingo.get(8).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check.setEnabled(true);
                check.setBackgroundColor(Color.parseColor("#96CDCD"));
                imageID = map.get(BINGO_IDS[8]);
                selectedImage.setImageResource(imageID);
                stateDetailed.setText("선택한 그림이 맞으면 확인 버튼을 누르세요");
            }
        });
        bingo.get(9).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check.setEnabled(true);
                check.setBackgroundColor(Color.parseColor("#96CDCD"));
                imageID = map.get(BINGO_IDS[9]);
                selectedImage.setImageResource(imageID);
                stateDetailed.setText("선택한 그림이 맞으면 확인 버튼을 누르세요");
            }
        });
        bingo.get(10).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check.setEnabled(true);
                check.setBackgroundColor(Color.parseColor("#96CDCD"));
                imageID = map.get(BINGO_IDS[10]);
                selectedImage.setImageResource(imageID);
                stateDetailed.setText("선택한 그림이 맞으면 확인 버튼을 누르세요");
            }
        });
        bingo.get(11).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check.setEnabled(true);
                check.setBackgroundColor(Color.parseColor("#96CDCD"));
                imageID = map.get(BINGO_IDS[11]);
                selectedImage.setImageResource(imageID);
                stateDetailed.setText("선택한 그림이 맞으면 확인 버튼을 누르세요");
            }
        });
        bingo.get(12).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check.setEnabled(true);
                check.setBackgroundColor(Color.parseColor("#96CDCD"));
                imageID = map.get(BINGO_IDS[12]);
                selectedImage.setImageResource(imageID);
                stateDetailed.setText("선택한 그림이 맞으면 확인 버튼을 누르세요");
            }
        });
        bingo.get(13).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check.setEnabled(true);
                check.setBackgroundColor(Color.parseColor("#96CDCD"));
                imageID = map.get(BINGO_IDS[13]);
                selectedImage.setImageResource(imageID);
                stateDetailed.setText("선택한 그림이 맞으면 확인 버튼을 누르세요");
            }
        });
        bingo.get(14).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check.setEnabled(true);
                check.setBackgroundColor(Color.parseColor("#96CDCD"));
                imageID = map.get(BINGO_IDS[14]);
                selectedImage.setImageResource(imageID);
                stateDetailed.setText("선택한 그림이 맞으면 확인 버튼을 누르세요");
            }
        });
        bingo.get(15).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check.setEnabled(true);
                check.setBackgroundColor(Color.parseColor("#96CDCD"));
                imageID = map.get(BINGO_IDS[15]);
                selectedImage.setImageResource(imageID);
                stateDetailed.setText("선택한 그림이 맞으면 확인 버튼을 누르세요");
            }
        });

        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check.setEnabled(false);
                check.setBackgroundColor(Color.parseColor("#c4c4cf"));
                selectedImage.setImageResource(R.drawable.logo);
                stateDetailed.setText("그림을 선택하세요");
            }
        });
    }
}