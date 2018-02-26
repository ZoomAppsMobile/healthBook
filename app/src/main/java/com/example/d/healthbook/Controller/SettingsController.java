package com.example.d.healthbook.Controller;

import com.example.d.healthbook.GlobalVariables.PrefSingleton;

/**
 * Created by User on 17.08.2017.
 */

public class SettingsController {
    private static final String Field_Notification  = "notification_status";
    private static SettingsController mInstance;

    public static SettingsController getInstance(){
        if (mInstance == null) mInstance = new SettingsController();
        return mInstance;
    }

    private SettingsController(){
        this.notificationStatus = (Boolean) PrefSingleton.getInstance().getValue(Field_Notification,true);
    }

    private Boolean notificationStatus;
    public Boolean getNotificationStatus() {
        return notificationStatus;
    }

    public void changeNotifiactionStatus(Boolean new_status) {
        PrefSingleton.getInstance().setValue(Field_Notification,new_status);
    }
}
