package com.example.d.healthbook.View;

/**
 * Created by User on 20.09.2017.
 */

public interface DialogCallback<T> {
    <T> void onDialogResult(T data , Integer id);
    void onDialogFailed(String message);
}
