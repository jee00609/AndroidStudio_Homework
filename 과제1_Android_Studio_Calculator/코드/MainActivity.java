package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.*;

public class MainActivity extends AppCompatActivity {

    EditText calculate_text;
    Button calculate_Button;
    TextView result;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("계산기 mk2");

        calculate_text = (EditText)findViewById(R.id.calculate_Text);
        calculate_Button = (Button)findViewById(R.id.calculate_Button);
        result = (TextView)findViewById(R.id.calculate_result);

        calculate_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String calculate_input = calculate_text.getText().toString();
                ArrayList<String> input_list = new ArrayList<String>();

                Stack<Integer> num_stack = new Stack<Integer>();
                Stack<String > operator_stack = new Stack<String>();

                int num=0;

                StringTokenizer input_token = new StringTokenizer(calculate_input," "); //스페이스바로 토큰 즉 12를 1 2가 아닌 12로 받을 수 있다.
                while(input_token.hasMoreTokens()) {
                    input_list.add(input_token.nextToken());
                    System.out.println(input_list);
                }

                for(String s_Num : input_list){
                    try {   //숫자를 얻는다.
                        num = Integer.parseInt(s_Num);
                        num_stack.push(num);
                    }catch (NumberFormatException e){ //숫자로 바꿀 수 없는 문자열을 숫자열로 바꾸려했을 때! 즉 여기에 걸리면 연산자
                        operator_stack.push(s_Num);
                    }
                }

//                검토용으로 사용했습니다.
//                System.out.println(num_stack);//숫자 스택
//                System.out.println("\n");
//                System.out.println(operator_stack);//연산자 스택
//                System.out.println("\n");
//
//                System.out.println(convToExpression(calculate_input));
//                System.out.println("\n\n");
//                System.out.println(calPostfix(convToExpression(calculate_input)));

                int input_result = calPostfix(convToExpression(calculate_input));
                String string_input_result = Integer.toString(input_result);

                result.setText(string_input_result);

            }
        });

    }

    //후위 연산식을 계산 하는 함수
    int calPostfix(String input) {

        Stack<Integer> stack = new Stack<>();
        int len = input.length();

        for (int i = 0; i < len; i++) {
            char op = input.charAt(i);

            if (op >= '1' && op <= '9') {
                stack.push(op - '0');
            }
            else {
                int op2 = stack.pop();
                int op1 = stack.pop();

                switch (op) {

                    case '+':
                        stack.push(op1 + op2);
                        break;
                    case '-':
                        stack.push(op1 - op2);
                        break;
                    case '*':
                        stack.push(op1 * op2);
                        break;
                    case '/':
                        stack.push(op1 / op2);
                        break;
                }
            }
        }
        return stack.pop();
    }


//  중위 연산식을 후위 연산식으로 바꿔주는 함수
    String convToExpression(String exp) {

        Stack<Character> stack = new Stack<>(); //연산자 스택
        int len = exp.length(); //중위 연산식의 길이
        String postFix = "";    //후위 연산식 String

        for (int i = 0; i < len; i++) { //중위 연산자가 끝날 때 까지
            char ch = exp.charAt(i);

            if (ch >= '1' && ch <= '9') {   //문자열이 123456789 인것을 의미한다. 즉 연산자는 이 때 받지 않는다.
                System.out.println("1~9 ch : "+ch);
                postFix += ch;
            }
            else {  //연산자일 경우 스택에 쌓으며...
                switch (ch) {
                    //  ( 는 가중치에 상관 없이 무조건 stack에 쌓는다.
                    //  )를 만나는 순간 ( 위에 있는 모든 연산자를 stack에 pop 으로 넣고 ( 는 없앤다.
                    case '(':
                        stack.push(ch);
                        break;

                    case ')':
                        while (true) {
                            char pop = stack.pop();
                            if (pop == '(')
                                break;
                            postFix += pop;
                        }
                        break;

//                        우선순위
//                    *, / : 제일 크다
//                    +, - : 두번째
//                    ( :가장 작다
//                  즉 이걸 스위치문으로 돌리려면
//                  case 순서가 ( ) << + 또는 - << * 또는 / 으로 해야한단 거다.
//                  while 문의 의미 : 스택이 비어있지 않으며
                    case '+':
                    case '-':
                    case '*':
                    case '/':
                        while (!stack.isEmpty() && isProceed(stack.peek(), ch)) {
                            postFix += stack.pop();
                        }
                        System.out.println("ch 값 : "+ch);
                        stack.push(ch);
                        break;
                }
            }
        }

        while (!stack.isEmpty()){   //스택이 비어있지 않을 때 까지(while)
            System.out.println("postFix : "+postFix);
            postFix += stack.pop(); //후위연산식의 숫자에 연산자를 붙인다. 야매로 한거긴 한데 이게 자바에서는 먹히는 것 같다.
        }

        return postFix;

    }

    //Operator 우선순위 비교 함수
    boolean isProceed(char op1, char op2) { //op1 : stack의 최상단, op2 : 현재 들어온 값

        int op1Prec = getOpPrec(op1);
        int op2Prec = getOpPrec(op2);

        if (op1Prec >= op2Prec){ //op1의 우선순위가 높으면 true
            System.out.println("op1 : "+op1);
            return true;
        }
        else {    //낮으면 false를 반환한다.
            System.out.println("op2"+op2);
            return false;
        }
    }

    //연산자의 우선순위를 반환하는 코드
    int getOpPrec(char op) {

        switch (op) {
            case '*':
            case '/':
                return 5;

            case '+':
            case '-':
                return 3;

            case '(':
                return 1;
        }
        return -1;
    }

}