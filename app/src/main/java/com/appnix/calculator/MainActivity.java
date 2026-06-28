package com.appnix.calculator;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    TextView tvResult, tvExpression;

    String currentInput = "";
    double firstNumber = 0;
    String operator = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // TextViews
        tvResult = findViewById(R.id.tvResult);
        tvExpression = findViewById(R.id.tvExpression);

        // Buttons
        Button btn0 = findViewById(R.id.btn0);
        Button btn1 = findViewById(R.id.btn1);
        Button btn2 = findViewById(R.id.btn2);
        Button btn3 = findViewById(R.id.btn3);
        Button btn4 = findViewById(R.id.btn4);
        Button btn5 = findViewById(R.id.btn5);
        Button btn6 = findViewById(R.id.btn6);
        Button btn7 = findViewById(R.id.btn7);
        Button btn8 = findViewById(R.id.btn8);
        Button btn9 = findViewById(R.id.btn9);

        Button btnPlus = findViewById(R.id.btnPlus);
        Button btnMinus = findViewById(R.id.btnMinus);
        Button btnMultiply = findViewById(R.id.btnMultiply);
        Button btnDivide = findViewById(R.id.btnDivide);

        Button btnEqual = findViewById(R.id.btnEqual);
        Button btnAC = findViewById(R.id.btnAC);
        Button btnDot = findViewById(R.id.btnDot);
        Button btnPlusMinus = findViewById(R.id.btnPlusMinus);
        Button btnPercent = findViewById(R.id.btnPercent);

        // Copy Result
        tvResult.setOnLongClickListener(v -> {

            ClipboardManager clipboard =
                    (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);

            ClipData clip =
                    ClipData.newPlainText("Result", tvResult.getText().toString());

            clipboard.setPrimaryClip(clip);

            Toast.makeText(this, "Copied", Toast.LENGTH_SHORT).show();

            return true;
        });

        // Number Buttons
        btn0.setOnClickListener(v -> appendNumber("0"));
        btn1.setOnClickListener(v -> appendNumber("1"));
        btn2.setOnClickListener(v -> appendNumber("2"));
        btn3.setOnClickListener(v -> appendNumber("3"));
        btn4.setOnClickListener(v -> appendNumber("4"));
        btn5.setOnClickListener(v -> appendNumber("5"));
        btn6.setOnClickListener(v -> appendNumber("6"));
        btn7.setOnClickListener(v -> appendNumber("7"));
        btn8.setOnClickListener(v -> appendNumber("8"));
        btn9.setOnClickListener(v -> appendNumber("9"));

        // Decimal
        btnDot.setOnClickListener(v -> {

            if (!currentInput.contains(".")) {

                if (currentInput.isEmpty()) {
                    currentInput = "0.";
                } else {
                    currentInput += ".";
                }

                tvResult.setText(currentInput);
            }
        });

        // Operators
        btnPlus.setOnClickListener(v -> setOperator("+"));
        btnMinus.setOnClickListener(v -> setOperator("-"));
        btnMultiply.setOnClickListener(v -> setOperator("×"));
        btnDivide.setOnClickListener(v -> setOperator("÷"));

        // AC
        btnAC.setOnClickListener(v -> {

            currentInput = "";
            firstNumber = 0;
            operator = "";

            tvExpression.setText("");
            tvResult.setText("0");
        });

        // Plus Minus
        btnPlusMinus.setOnClickListener(v -> {

            if (currentInput.isEmpty()) return;

            if (currentInput.startsWith("-")) {
                currentInput = currentInput.substring(1);
            } else {
                currentInput = "-" + currentInput;
            }

            tvResult.setText(currentInput);
        });

        // Percent
        btnPercent.setOnClickListener(v -> {

            if (currentInput.isEmpty()) return;

            double value = Double.parseDouble(currentInput);

            value = value / 100;

            currentInput = formatNumber(value);

            tvResult.setText(currentInput);
        });

        // Equal
        btnEqual.setOnClickListener(v -> calculateResult());
    }

    private void appendNumber(String value) {

        if (currentInput.equals("0")) {
            currentInput = value;
        } else {
            currentInput += value;
        }

        tvResult.setText(currentInput);

        if (!operator.isEmpty()) {

            tvExpression.setText(
                    formatNumber(firstNumber)
                            + " "
                            + operator
                            + " "
                            + currentInput
            );
        }
    }

    private void setOperator(String op) {

        if (!currentInput.isEmpty()) {

            firstNumber = Double.parseDouble(currentInput);

            operator = op;

            tvExpression.setText(
                    formatNumber(firstNumber)
                            + " "
                            + operator
            );

            currentInput = "";

            tvResult.setText("0");
        }
    }

    private void calculateResult() {

        if (currentInput.isEmpty()) return;

        double secondNumber = Double.parseDouble(currentInput);

        double result = 0;

        switch (operator) {

            case "+":
                result = firstNumber + secondNumber;
                break;

            case "-":
                result = firstNumber - secondNumber;
                break;

            case "×":
                result = firstNumber * secondNumber;
                break;

            case "÷":

                if (secondNumber != 0) {
                    result = firstNumber / secondNumber;
                } else {
                    tvResult.setText("Error");
                    return;
                }

                break;
        }

        String expressionText =
                formatNumber(firstNumber)
                        + " "
                        + operator
                        + " "
                        + formatNumber(secondNumber);

        currentInput = formatNumber(result);

        tvExpression.setText(expressionText);

        tvResult.setText(currentInput);

        operator = "";
    }

    private String formatNumber(double value) {

        if (value == (long) value) {
            return String.valueOf((long) value);
        }

        return String.valueOf(value);
    }
}