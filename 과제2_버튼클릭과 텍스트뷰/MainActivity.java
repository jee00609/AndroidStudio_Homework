package com.example.homework2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView result;
    Button num1B,num2B,plusB,minusB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        result = (TextView)findViewById(R.id.showMeText);
        //result.setText("");

        num1B = (Button)findViewById(R.id.num1);
        num2B = (Button)findViewById(R.id.num2);
        plusB = (Button)findViewById(R.id.plus);
        minusB = (Button)findViewById(R.id.minus);

//        num1B.setOnTouchListener(new View.OnClickListener() {
//            @Override
//            public boolean onTouch(View view, MotionEvent motionEvent) {
//                result.append("1");
//                return false;
//            }
//        });
//        이걸 터치로 만들시 글자가 두번 출력되는 광경을 볼 수 있다.
//        클릭으로 만드는 것이 정신 건강에 좋다.


        num1B.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                result.append("1");
            }
        });

        num2B.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                result.append("2");
            }
        });

        plusB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                result.append("+");
            }
        });

        minusB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                result.append("-");
            }
        });



    }
}
