package com.azshelton.calculator7;

/**
 * Created by drwil on 9/30/2016.
 */

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.regex.Pattern;

public class Calculator extends AppCompatActivity {
    private TextView _screen;
    private String display = "";
    private String currentOperator = "";
    private String result = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);
        _screen = (TextView)findViewById(R.id.textView);
        _screen.setText(display);
    }
    //updates the screen
    private void updateScreen(){
        _screen.setText(display);
    }

    //get the number from the user
    public void onClickNumber(View v){
        if(result != ""){
            clear();
            updateScreen();
        }
        Button b = (Button) v;
        display += b.getText();
        updateScreen();
    }
    //the accepted operations
    private boolean isOperator(char op){
        switch (op){
            case '+':
            case '-':
            case '*':
            case '÷':return true;
            default: return false;
        }
    }
    //gets input from the user on the operation they wish to perform
    public void onClickOperator(View v){
        if(display == "") return;

        Button b = (Button)v;

        if(result != ""){
            String _display = result;
            clear();
            display = _display;
        }
        //puts the input on to the screen
        if(currentOperator != ""){
            Log.d("CalcX", ""+display.charAt(display.length()-1));
            if(isOperator(display.charAt(display.length()-1))){
                display = display.replace(display.charAt(display.length()-1), b.getText().charAt(0));
                updateScreen();
                return;
            }else{
                getResult();
                display = result;
                result = "";
            }
            currentOperator = b.getText().toString();
        }
        display += b.getText();
        currentOperator = b.getText().toString();
        updateScreen();
    }
    //clears the input on the screen
    private void clear(){
        display = "";
        currentOperator = "";
        result = "";
    }
    //takes input from the user to run the clear
    public void onClickClear(View v){
        clear();
        updateScreen();
    }
    //runs the operations on the input
    private double operate(String a, String b, String op){
        switch (op){
            case "+": return Double.valueOf(a) + Double.valueOf(b);
            case "-": return Double.valueOf(a) - Double.valueOf(b);
            case "*": return Double.valueOf(a) * Double.valueOf(b);
            case "÷": try{
                return Double.valueOf(a) / Double.valueOf(b);
            }catch (Exception e){
                Log.d("Calc", e.getMessage());
            }
            default: return -1;
        }
    }
    //displays the results
    private boolean getResult(){
        if(currentOperator == "") return false;
        String[] operation = display.split(Pattern.quote(currentOperator));
        if(operation.length < 2) return false;
        result = String.valueOf(operate(operation[0], operation[1], currentOperator));
        return true;
    }
    //takes input from the user that they wish to get results
    public void onClickEqual(View v){
        if(display == "") return;
        if(!getResult()) return;
        _screen.setText(display + "\n" + String.valueOf(result));
    }
}