
package com.example.d.healthbook.Models.DiaryModel;

import com.google.gson.annotations.SerializedName;

public class _id {

    @SerializedName("counter")
    private Long mCounter;
    @SerializedName("date")
    private Long mDate;
    @SerializedName("machineIdentifier")
    private Long mMachineIdentifier;
    @SerializedName("processIdentifier")
    private Long mProcessIdentifier;
    @SerializedName("time")
    private Long mTime;
    @SerializedName("timeSecond")
    private Long mTimeSecond;
    @SerializedName("timestamp")
    private Long mTimestamp;

    public Long getCounter() {
        return mCounter;
    }

    public void setCounter(Long counter) {
        mCounter = counter;
    }

    public Long getDate() {
        return mDate;
    }

    public void setDate(Long date) {
        mDate = date;
    }

    public Long getMachineIdentifier() {
        return mMachineIdentifier;
    }

    public void setMachineIdentifier(Long machineIdentifier) {
        mMachineIdentifier = machineIdentifier;
    }

    public Long getProcessIdentifier() {
        return mProcessIdentifier;
    }

    public void setProcessIdentifier(Long processIdentifier) {
        mProcessIdentifier = processIdentifier;
    }

    public Long getTime() {
        return mTime;
    }

    public void setTime(Long time) {
        mTime = time;
    }

    public Long getTimeSecond() {
        return mTimeSecond;
    }

    public void setTimeSecond(Long timeSecond) {
        mTimeSecond = timeSecond;
    }

    public Long getTimestamp() {
        return mTimestamp;
    }

    public void setTimestamp(Long timestamp) {
        mTimestamp = timestamp;
    }

}
