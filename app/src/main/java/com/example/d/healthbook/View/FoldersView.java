package com.example.d.healthbook.View;

import java.util.List;

/**
 * Created by User on 21.09.2017.
 */

public interface FoldersView {
    void onShowToast(String message);
    void onShowError(String message);
    <T>  void onDataLoaded(List<T> data, Class<T> tClass);
}
