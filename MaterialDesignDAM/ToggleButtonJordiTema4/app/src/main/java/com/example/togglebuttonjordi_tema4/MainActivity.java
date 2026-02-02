package com.example.togglebuttonjordi_tema4;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    TextView display;
    String input = "";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.linea); // tu layout linea.xml

        display = findViewById(R.id.display);


        int[] nums = {R.id.btn0,R.id.btn1,R.id.btn2,R.id.btn3,R.id.btn4,R.id.btn5,R.id.btn6,R.id.btn7,R.id.btn8,R.id.btn9};
        for(int id:nums)
            findViewById(id).setOnClickListener(v -> {
                String n = ((Button)v).getText().toString();
                if(input.equals("0")) input=""; // no empezar con 0
                input += n;
                display.setText(input);
            });


        int[] ops = {R.id.btnSum,R.id.btnSub,R.id.btnMul,R.id.btnDiv};
        for(int id:ops)
            findViewById(id).setOnClickListener(v -> {
                if(input.length()>0 && !"+-*/".contains(input.substring(input.length()-1)))
                    input += ((Button)v).getText().toString();
                display.setText(input);
            });


        findViewById(R.id.btnDot).setOnClickListener(v -> {
            if(input.isEmpty()) input="0";
            if(!input.substring(Math.max(0,input.lastIndexOf("+")+1))
                    .substring(Math.max(0,input.lastIndexOf("-")+1))
                    .contains("."))
                input += ".";
            display.setText(input);
        });


        findViewById(R.id.btnAC).setOnClickListener(v -> {
            input = "";
            display.setText("0");
        });


        findViewById(R.id.btnC).setOnClickListener(v -> {
            if(input.length()>0) input = input.substring(0,input.length()-1);
            display.setText(input.isEmpty()?"0":input);
        });


        findViewById(R.id.btnEqual).setOnClickListener(v -> {
            try{
                double res = eval(input);
                input = String.valueOf(res);
                display.setText(input);
            }catch(Exception e){
                display.setText("Error");
                input="";
            }
        });
    }


    private double eval(String s){
        return new Object(){
            int pos=-1,ch;
            void nextChar(){ ch = (++pos<s.length())?s.charAt(pos):-1; }
            boolean eat(int c){ while(ch==' ') nextChar(); if(ch==c){nextChar();return true;} return false;}
            double parse(){ nextChar(); double x=parseExpr(); if(pos<s.length()) throw new RuntimeException(); return x;}
            double parseExpr(){ double x=parseTerm(); for(;;){if(eat('+'))x+=parseTerm(); else if(eat('-'))x-=parseTerm(); else return x;}}
            double parseTerm(){ double x=parseFact(); for(;;){if(eat('*'))x*=parseFact(); else if(eat('/'))x/=parseFact(); else return x;}}
            double parseFact(){ if(eat('+'))return parseFact(); if(eat('-'))return -parseFact(); int start=pos; while((ch>='0'&&ch<='9')||ch=='.') nextChar(); return Double.parseDouble(s.substring(start,pos));}
        }.parse();
    }
}