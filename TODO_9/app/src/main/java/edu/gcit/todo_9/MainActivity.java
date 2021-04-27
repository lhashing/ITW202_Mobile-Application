package edu.gcit.todo_9;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "CalculatorActivity";
    private Calculator mCalculator;
    private EditText mOperandOneEditText;
    private EditText mOperandTwoEditText;
    private TextView mResultTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mCalculator = new Calculator();
        mResultTextView = findViewById(R.id.operation_result_text_view);
        mOperandOneEditText = findViewById(R.id.operand_one_edit_text);
        mOperandTwoEditText = findViewById(R.id.operand_two_edit_text);
    }

    public void onAdd(View view) {
/*
        String Value1 = mOperandOneEditText.getText().toString();
        String Value2 = mOperandTwoEditText.getText().toString();
        double Result = mCalculator.add(Double.valueOf(Value1),Double.valueOf(Value2));
        mResultTextView.setText(String.valueOf(Result));
*/
        compute(Calculator.Operator.ADD);
    }

    public void onSub(View view) {
/*
        String Value1 = mOperandOneEditText.getText().toString();
        String Value2 = mOperandTwoEditText.getText().toString();
        double Result = mCalculator.sub(Double.valueOf(Value1),Double.valueOf(Value2));
        mResultTextView.setText(String.valueOf(Result));
*/
        compute(Calculator.Operator.SUB);

    }

    public void onDiv(View view) {
/*
        String Value1 = mOperandOneEditText.getText().toString();
        String Value2 = mOperandTwoEditText.getText().toString();
        double Result = mCalculator.div(Double.valueOf(Value1),Double.valueOf(Value2));
        mResultTextView.setText(String.valueOf(Result));
*/
        try {
            compute(Calculator.Operator.DIV);
        } catch (IllegalArgumentException iae) {
            Log.e(TAG, "IllegalArgumentException", iae);
            mResultTextView.setText(getString(R.string.computationError));
        }
    }

    public void onMul(View view) {
/*
        String Value1 = mOperandOneEditText.getText().toString();
        String Value2 = mOperandTwoEditText.getText().toString();
        double Result = mCalculator.mul(Double.valueOf(Value1),Double.valueOf(Value2));
        mResultTextView.setText(String.valueOf(Result));
*/
        compute(Calculator.Operator.MUL);
    }

    private void compute(Calculator.Operator operator) {
        double operandOne;
        double operandTwo;
        try {
            operandOne = getOperand(mOperandOneEditText);
            operandTwo = getOperand(mOperandTwoEditText);
        } catch (NumberFormatException nfe) {
            Log.e(TAG, "NumberFormatException", nfe);
            mResultTextView.setText(getString(R.string.computationError));
            return;
        }

        String result;
        switch (operator) {
            case ADD:
                result = String.valueOf(
                        mCalculator.add(operandOne, operandTwo));
                break;
            case SUB:
                result = String.valueOf(
                        mCalculator.sub(operandOne, operandTwo));
                break;
            case DIV:
                result = String.valueOf(
                        mCalculator.div(operandOne, operandTwo));
                break;
            case MUL:
                result = String.valueOf(
                        mCalculator.mul(operandOne, operandTwo));
                break;
            default:
                result = getString(R.string.computationError);
                break;
        }
        mResultTextView.setText(result);
    }
    private static Double getOperand(EditText operandEditText) {
        String operandText = getOperandText(operandEditText);
        return Double.valueOf(operandText);
    }

    private static String getOperandText(EditText operandEditText) {
        return operandEditText.getText().toString();
    }

}