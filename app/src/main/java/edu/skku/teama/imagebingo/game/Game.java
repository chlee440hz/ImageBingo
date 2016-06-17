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
            R.id.bingo1,
            R.id.bingo2,
            R.id.bingo3,
            R.id.bingo4,
            R.id.bingo5,
            R.id.bingo6,
            R.id.bingo7,
            R.id.bingo8,
            R.id.bingo9,
            R.id.bingo10,
            R.id.bingo11,
            R.id.bingo12,
            R.id.bingo13,
            R.id.bingo14,
            R.id.bingo15,
            R.id.bingo16,
    };
    /*private static final int[] IMAGE_IDS = {
            R.drawable.image1,
            R.drawable.image2,
            R.drawable.image3,
            R.drawable.image4,
            R.drawable.image5,
            R.drawable.image6,
            R.drawable.image7,
            R.drawable.image8,
            R.drawable.image9,
            R.drawable.image10,
            R.drawable.image11,
            R.drawable.image12,
            R.drawable.image13,
            R.drawable.image14,
            R.drawable.image15,
            R.drawable.image16,
    };*/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game);
        TextView state = (TextView) findViewById(R.id.state);
        ImageView selectedImage = (ImageView) findViewById(R.id.selectedImage);
        Button check = (Button) findViewById(R.id.check);
        ArrayList<ImageButton> bingo = new ArrayList<ImageButton>();

        Random bimag = new Random();
        Integer b[] = new Integer[16];
        int img[] = new int[16];
        /*for(int i = 0; i < 16; i++) {
            b[i] = bimag.nextInt(15);
            for(int j = 0; j < i; j++) {
                //if(b[i] == b[j]) {
                //    i--;
                //    break;
                //}
                img[i] = IMAGE_IDS[i];
            }
        }*/

        for(int i = 0; i < 16; i++) {
            ImageButton button = (ImageButton)findViewById(BINGO_IDS[i]);
            button.setImageResource(R.drawable.logo);
            bingo.add(button);
        }
    }
}