package edu.skku.teama.imagebingo.game;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

import edu.skku.teama.imagebingo.R;

public class Game extends AppCompatActivity {
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game);
        ArrayList<Integer> ran = new ArrayList<Integer>();
        for(int i = 0; i < 16; i++) {
            ran.add(i);
        }
        TextView state = (TextView) findViewById(R.id.state);
        ImageView selectedImage = (ImageView) findViewById(R.id.selectedImage);
        Button check = (Button) findViewById(R.id.check);
        ArrayList<ImageButton> bingo = new ArrayList<ImageButton>();

        Random bimag = new Random();
        Integer b[] = new Integer[16];
        int img[] = new int[16];
        for(int i = 0; i < 16; i++) {
            int j = bimag.nextInt(ran.size());
            b[i] = ran.get(j);
            ran.remove(j);
        }

        for(int i = 0; i < 16; i++) {
            ImageButton button = (ImageButton)findViewById(BINGO_IDS[i]);
            button.setImageResource(IMAGE_IDS[i]);
            bingo.add(button);
        }
    }
}