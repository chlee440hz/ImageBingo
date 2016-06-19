package edu.skku.teama.imagebingo.help;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import edu.skku.teama.imagebingo.R;


public class Help extends AppCompatActivity {

    private int indicator = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.help);
        setTitle("도움말");

        final String[] help = {
                "주의 사항 !",
                "연결 방법 1",
                "연결 방법 2",
                "연결 방법 3",
                "연결 방법 4",
                "게임 방법 1",
                "게임 방법 2",
                "게임 방법 3",
                "게임 방법 4",
                "게임 방법 5",
                "게임 방법 6",
                "도움이 되었나요 ?"
        };
        final String[] helpDetailed = {
              "게임을 시작하기 전에 \n상대와 블루투스 페어링을 해주세요 !",
                "게임을 시작하면 선공과 후공을 정합니다",
                "선공 플레이어는\n대전할 상대의 기기를 선택하여 연결합니다",
                "후공 플레이어는\n선공 플레이어가 연결할 때까지 대기합니다",
                "연결이 완료되면 게임이 시작됩니다",
                "선공 플레이어는 원하는 그림을 선택합니다",
                "확인 버튼을 클릭하여\n후공 플레이어에게 턴을 넘겨 줍니다",
                "후공 플레이어도 원하는 그림을 선택합니다",
                "확인 버튼을 클릭하여\n다시 선공 플레이어에게 턴을 넘깁니다.",
                "가로, 세로 또는 대각선으로\n한 줄을 완성하면 빙고가 됩니다",
                "1~4를 반복, 먼저 빙고 3개를 완성하는\n플레이어가 승리하게 됩니다",
                "지금 바로 이미지 빙고를 즐겨보세요 !"
        };

        final TextView textHelp = (TextView) findViewById(R.id.textHelp);
        final TextView textHelpDetailed = (TextView) findViewById(R.id.textHelpDetailed);
        final Button btnPrev = (Button) findViewById(R.id.btnPrev);
        final Button btnNext = (Button) findViewById(R.id.btnNext);

        textHelp.setText(help[0]);
        textHelpDetailed.setText(helpDetailed[0]);

        btnPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(indicator ==11) {
                    indicator--;
                    textHelp.setText(help[indicator]);
                    textHelpDetailed.setText(helpDetailed[indicator]);
                    btnNext.setText("다음");
                }else if(indicator > 0 && indicator <11){
                    indicator--;
                    textHelp.setText(help[indicator]);
                    textHelpDetailed.setText(helpDetailed[indicator]);
                }else if(indicator == 0){
                    Toast.makeText(getApplicationContext(), "처음입니다", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(indicator >= 0 && indicator < 10){
                    indicator++;
                    textHelp.setText(help[indicator]);
                    textHelpDetailed.setText(helpDetailed[indicator]);
                }else if(indicator == 10){
                    indicator++;
                    textHelp.setText(help[indicator]);
                    textHelpDetailed.setText(helpDetailed[indicator]);
                    btnNext.setText("종료");
                }else if(indicator > 10){
                    finish();
                }
            }
        });

    }
}
