package edu.skku.teama.imagebingo.main;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import edu.skku.teama.imagebingo.R;
import edu.skku.teama.imagebingo.game.Game;
import edu.skku.teama.imagebingo.help.Help;

public class Main extends AppCompatActivity {

    private Button btnStartGame, btnRules, btnHelp, btnInfo, btnExit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        setTitle("메인 화면");

        //게임 시작
        btnStartGame = (Button)findViewById(R.id.btnStartGame);
        btnStartGame.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                Intent intent = new Intent(getApplicationContext(), Game.class);
                startActivity(intent);
            }
        });

        //도움말
        btnHelp = (Button)findViewById(R.id.btnHelp);
        btnHelp.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                Intent intent = new Intent(getApplicationContext(), Help.class);
                startActivity(intent);
            }
        });

        //나가기
        btnExit = (Button)findViewById(R.id.btnExit);
        btnExit.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                finish();
            }
        });
    }
}
