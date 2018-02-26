package com.example.d.healthbook.UI;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.IntegerRes;
import android.text.method.CharacterPickerDialog;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * Created by User on 14.08.2017.
 */

public class CustomTimeMaskedEditText extends android.support.v7.widget.AppCompatEditText {

    public CustomTimeMaskedEditText(Context context) {
        super(context);
    }

    public CustomTimeMaskedEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomTimeMaskedEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private Integer Hour_First_Digit = 0;
    private Integer Hour_Second_Digit = 1;
    private Integer MINUTE_First_DIGIT = 2;

    private boolean checkTimeDigit(Integer type , Integer digit){
        switch (type){
            case 0:{
                if(digit >=0 && digit<=2){
                    return true;
                }
                break;
            }
            case 1:{
                if(hourFirstDigit==2){
                    if(digit<=3){
                        return true;
                    }
                }
                else if(hourFirstDigit <2){
                    if(digit>=0)
                        return true;
                }
                break;
            }
            case 2:{
                if(digit>=0 && digit<=5)
                    return true;
            }
        }
        return false;
    }

    Integer hourFirstDigit = 0;
    String lastSuccesfullText = "";

    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
        Integer start_N = start+1;
        Integer lengthBfeore_N = lengthBefore;
        Integer lengthAfter_N = lengthAfter;
        if(!(text.length()>0))
            return;
        if(!lastSuccesfullText.isEmpty() && lengthBefore>lengthAfter){
            return;
        }

        char currentChar = (text.charAt(start_N-1));
        switch (start_N){
            case 1:{
                if(text.length()==start_N) {
                    if (checkTimeDigit(Hour_First_Digit, Character.getNumericValue(currentChar))) {
                        lastSuccesfullText = text.toString();
                        hourFirstDigit = Character.getNumericValue(currentChar);
                    } else {
                        setText(lastSuccesfullText);
                        setSelection(lastSuccesfullText.length());
                        hourFirstDigit = null;
                    }
                }
                else if( text.length()>start_N){
                    if (checkTimeDigit(Hour_First_Digit, Character.getNumericValue(currentChar))) {
                        hourFirstDigit = Character.getNumericValue(currentChar);
                        String tmp_text_holder_start = lastSuccesfullText.substring(1,2);
                        if(checkTimeDigit( Hour_Second_Digit,Integer.parseInt(tmp_text_holder_start))){
                            lastSuccesfullText = text.toString();
                            setSelection(1);
                        }
                        else{
                            String tmp_text_holder_start1 = lastSuccesfullText.substring(0,1);
                            String tmp_text_holder_end = lastSuccesfullText.substring(2);
                            String tmp_text = tmp_text_holder_start1 + tmp_text_holder_end;
                            setText(tmp_text);
                            setSelection(1);
                        }
                    } else {
                        setText(lastSuccesfullText);
                        setSelection(lastSuccesfullText.length());
                        hourFirstDigit = null;
                    }
                }
                break;
            }
            case 2:{
                if(checkTimeDigit( Hour_Second_Digit,Character.getNumericValue(currentChar))){
                    lastSuccesfullText = text.toString();
                    if(start_N == text.length()) {
                        String tmp_text = lastSuccesfullText + ":";
                        setText(tmp_text);
                        setSelection(tmp_text.length());
                    }
                    else if( text.length()>start_N && !text.toString().contains(":")){
                        String tmp_text_holder_start = lastSuccesfullText.substring(0,2);
                        String tmp_text_holder_end = lastSuccesfullText.substring(2);
                        String tmp_text = tmp_text_holder_start + ":"+tmp_text_holder_end;
                        setText(tmp_text);
                        setSelection(3);
                    }
                }
                else{
                    setText(lastSuccesfullText);
                    setSelection(lastSuccesfullText.length());
                }
                break;
            }
            case 3:{
               if(currentChar==':'){
                   lastSuccesfullText = text.toString();
                   break;
               }
               else{
                   setText(lastSuccesfullText);
                   setSelection(lastSuccesfullText.length());
               }
            }
            case 4:{
                if(checkTimeDigit( MINUTE_First_DIGIT,Character.getNumericValue(currentChar)))
                    lastSuccesfullText = text.toString();
                else{
                    setText(lastSuccesfullText);
                    setSelection(lastSuccesfullText.length());
                }
                break;
            }
            case 5:{
                lastSuccesfullText = text.toString();
                break;
            }
            case 6:{

                break;
            }
        }
    }

}
