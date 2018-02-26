package com.example.d.healthbook.View;

import com.example.d.healthbook.Models.Document;

import java.util.List;

/**
 * Created by User on 31.08.2017.
 */

public interface ChannelInfoView {
    void onSubscribed();
    void onUnSubscribed();
    void onDataLoaded(List<Document> datum);
    void onLoadedError(String reason);
}
