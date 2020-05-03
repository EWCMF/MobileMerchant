package com.android.example.mobilemerchant.view;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.example.mobilemerchant.R;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ast.Scope;

public class CalculatorActivity extends AppCompatActivity {

    TextView tvInput, tvOutput;
    Button n0,n00,n1,n2,n3,n4,n5,n6,n7,n8,n9, substract, add, multiply, divide, clear,
            bracket, percentage, equal, comma;
    String process;
    boolean checkBracket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);



        n0 = findViewById(R.id.btnNum0);
        n00 = findViewById(R.id.btnNum00);
        n1 = findViewById(R.id.btnNum1);
        n2 = findViewById(R.id.btnNum2);
        n3 = findViewById(R.id.btnNum3);
        n4 = findViewById(R.id.btnNum4);
        n5 = findViewById(R.id.btnNum5);
        n6 = findViewById(R.id.btnNum6);
        n7 = findViewById(R.id.btnNum7);
        n8 = findViewById(R.id.btnNum8);
        n9 = findViewById(R.id.btnNum9);

        substract = findViewById(R.id.btnSubstract);
        add = findViewById(R.id.btnAdd);
        multiply = findViewById(R.id.btnMultiply);
        divide = findViewById(R.id.btnDivide);
        clear = findViewById(R.id.btnClear);
        bracket = findViewById(R.id.btnBracket);
        percentage = findViewById(R.id.btnPercentage);
        equal = findViewById(R.id.btnEqual);
        comma = findViewById(R.id.btnComma);

        tvInput = findViewById(R.id.tvInput);
        tvOutput = findViewById(R.id.tvOutput);

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvInput.setText("");
                tvOutput.setText("");
            }
        });

        n0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               process = tvInput.getText().toString();
               tvInput.setText(process + "0");

            }
        });
        n00.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                process = tvInput.getText().toString();
                tvInput.setText(process + "00");

            }
        });
        n1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                process = tvInput.getText().toString();
                tvInput.setText(process + "1");

            }
        });
        n2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                process = tvInput.getText().toString();
                tvInput.setText(process + "2");

            }
        });
        n3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                process = tvInput.getText().toString();
                tvInput.setText(process + "3");

            }
        });
        n4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                process = tvInput.getText().toString();
                tvInput.setText(process + "4");

            }
        });
        n5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                process = tvInput.getText().toString();
                tvInput.setText(process + "5");

            }
        });
        n6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                process = tvInput.getText().toString();
                tvInput.setText(process + "6");

            }
        });
        n7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                process = tvInput.getText().toString();
                tvInput.setText(process + "7");

            }
        });
        n8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                process = tvInput.getText().toString();
                tvInput.setText(process + "8");

            }
        });
        n9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                process = tvInput.getText().toString();
                tvInput.setText(process + "9");

            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                process = tvInput.getText().toString();
                tvInput.setText(process + "+");

            }
        });
        substract.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                process = tvInput.getText().toString();
                tvInput.setText(process + "-");

            }
        });
        divide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                process = tvInput.getText().toString();
                tvInput.setText(process + "/");

            }
        });
        multiply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                process = tvInput.getText().toString();
                tvInput.setText(process + "*");

            }
        });
        comma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                process = tvInput.getText().toString();
                tvInput.setText(process + ".");

            }
        });
        percentage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                process = tvInput.getText().toString();
                tvInput.setText(process + "%");

            }
        });
        bracket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkBracket = false;

                if (checkBracket == true){
                    process = tvInput.getText().toString();
                    tvInput.setText(process + ")");
                }else {
                    process = tvInput.getText().toString();
                    tvInput.setText(process + "(");
                    checkBracket = true;
                }

            }
        });
        equal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                process = tvInput.getText().toString();
                

                Context rhino = Context.enter();
                rhino.setOptimizationLevel(-1);

                String finalResult = "";

                try {
                    Scriptable scriptable = rhino.initStandardObjects();
                    finalResult = rhino.evaluateString(scriptable,process, "javascript", 1, null).toString();
                }catch (Exception e){
                    finalResult = "0";

                }

                tvOutput.setText(finalResult);

            }
        });



    }
}
