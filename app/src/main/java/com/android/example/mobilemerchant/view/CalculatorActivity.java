package com.android.example.mobilemerchant.view;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.example.mobilemerchant.R;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ast.Scope;

public class CalculatorActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);

        final TextView tvInput, tvOutput;
        final Button n0,n00,n1,n2,n3,n4,n5,n6,n7,n8,n9, substract, add, multiply, divide, clear,
                bracket, percentage, equal, comma;
        final String[] process = new String[0];
        boolean checkBracket = false;


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
               process[0] = tvInput.getText().toString();
               tvInput.setText(process[0] + "0");

            }
        });
        n00.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                process[0] = tvInput.getText().toString();
                tvInput.setText(process[0] + "00");

            }
        });
        n1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                process[0] = tvInput.getText().toString();
                tvInput.setText(process[0] + "1");

            }
        });
        n2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                process[0] = tvInput.getText().toString();
                tvInput.setText(process[0] + "2");

            }
        });
        n3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                process[0] = tvInput.getText().toString();
                tvInput.setText(process[0] + "3");

            }
        });
        n4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                process[0] = tvInput.getText().toString();
                tvInput.setText(process[0] + "4");

            }
        });
        n5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                process[0] = tvInput.getText().toString();
                tvInput.setText(process[0] + "5");

            }
        });
        n6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                process[0] = tvInput.getText().toString();
                tvInput.setText(process[0] + "6");

            }
        });
        n7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                process[0] = tvInput.getText().toString();
                tvInput.setText(process[0] + "7");

            }
        });
        n8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                process[0] = tvInput.getText().toString();
                tvInput.setText(process[0] + "8");

            }
        });
        n9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                process[0] = tvInput.getText().toString();
                tvInput.setText(process[0] + "9");

            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                process[0] = tvInput.getText().toString();
                tvInput.setText(process[0] + "+");

            }
        });
        substract.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                process[0] = tvInput.getText().toString();
                tvInput.setText(process[0] + "-");

            }
        });
        divide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                process[0] = tvInput.getText().toString();
                tvInput.setText(process[0] + "/");

            }
        });
        multiply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                process[0] = tvInput.getText().toString();
                tvInput.setText(process[0] + "*");

            }
        });
        comma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                process[0] = tvInput.getText().toString();
                tvInput.setText(process[0] + ".");

            }
        });
        percentage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                process[0] = tvInput.getText().toString();
                tvInput.setText(process[0] + "%");

            }
        });
        bracket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checkBracket = false;

                if (checkBracket == true){
                    process[0] = tvInput.getText().toString();
                    tvInput.setText(process[0] + ")");
                }else {
                    process[0] = tvInput.getText().toString();
                    tvInput.setText(process[0] + "(");
                    checkBracket = true;
                }

            }
        });
        equal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                process[0] = tvInput.getText().toString();
                

                Context rhino = Context.enter();
                rhino.setOptimizationLevel(-1);

                String finalResult = "";

                try {
                    Scriptable scriptable = rhino.initStandardObjects();
                    finalResult = rhino.evaluateString(scriptable,process[0], "javascript", 1, null).toString();
                }catch (Exception e){
                    finalResult = "0";

                }

                tvOutput.setText(finalResult);

            }
        });



    }
}
