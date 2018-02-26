
package com.example.d.healthbook.Models.DiaryModel;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class CompleteDiaryData {

    @SerializedName("birthday")
    private String mBirthday;

    private String mBirthday2;
    @SerializedName("create_date")
    private String mCreateDate;
    @SerializedName("email")
    private String mEmail;
    @SerializedName("gender")
    private Integer mGender;
    @SerializedName("height")
    private Integer mHeight;
    @SerializedName("id")
    private String mId;
    @SerializedName("is_deleted")
    private Boolean mIsDeleted;
    @SerializedName("name")
    private String mName;
    @SerializedName("parameters_count")
    private Integer mParametersCount;
    @SerializedName("records_count")
    private Integer mRecordsCount;
    @SerializedName("role")
    private Integer mRole;
    @SerializedName("shared_with")
    private List<Object> mSharedWith;
    @SerializedName("tracking_parameters")
    private List<Object> mTrackingParameters;
    @SerializedName("type")
    private Integer mType;
    @SerializedName("update_date")
    private String mUpdateDate;
    @SerializedName("user_id")
    private Integer mUserId;
    @SerializedName("user_name")
    private String mUserName;
    @SerializedName("user_patronymic")
    private String mUserPatronymic;
    @SerializedName("user_surname")
    private String mUserSurname;
    @SerializedName("weight")
    private Integer mWeight;
    @SerializedName("_id")
    private com.example.d.healthbook.Models.DiaryModel._id m_id;

    public String getBirthday() {
        return mBirthday;
    }

    public void setBirthday(String birthday) {
        mBirthday = birthday;
    }

    public String getCreateDate() {
        return mCreateDate;
    }

    public void setCreateDate(String createDate) {
        mCreateDate = createDate;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        mEmail = email;
    }

    public Integer getGender() {
        return mGender;
    }

    public void setGender(Integer gender) {
        mGender = gender;
    }

    public Integer getHeight() {
        return mHeight;
    }

    public void setHeight(Integer height) {
        mHeight = height;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public Boolean getIsDeleted() {
        return mIsDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        mIsDeleted = isDeleted;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public Integer getParametersCount() {
        return mParametersCount;
    }

    public void setParametersCount(Integer parametersCount) {
        mParametersCount = parametersCount;
    }

    public Integer getRecordsCount() {
        return mRecordsCount;
    }

    public void setRecordsCount(Integer recordsCount) {
        mRecordsCount = recordsCount;
    }

    public Integer getRole() {
        return mRole;
    }

    public void setRole(Integer role) {
        mRole = role;
    }

    public List<Object> getSharedWith() {
        return mSharedWith;
    }

    public void setSharedWith(List<Object> sharedWith) {
        mSharedWith = sharedWith;
    }

    public List<Object> getTrackingParameters() {
        return mTrackingParameters;
    }

    public void setTrackingParameters(List<Object> trackingParameters) {
        mTrackingParameters = trackingParameters;
    }

    public Integer getType() {
        return mType;
    }

    public void setType(Integer type) {
        mType = type;
    }

    public String getUpdateDate() {
        return mUpdateDate;
    }

    public void setUpdateDate(String updateDate) {
        mUpdateDate = updateDate;
    }

    public Integer getUserId() {
        return mUserId;
    }

    public void setUserId(Integer userId) {
        mUserId = userId;
    }

    public String getUserName() {
        return mUserName;
    }

    public void setUserName(String userName) {
        mUserName = userName;
    }

    public String getUserPatronymic() {
        return mUserPatronymic;
    }

    public void setUserPatronymic(String userPatronymic) {
        mUserPatronymic = userPatronymic;
    }

    public String getUserSurname() {
        return mUserSurname;
    }

    public void setUserSurname(String userSurname) {
        mUserSurname = userSurname;
    }

    public Integer getWeight() {
        return mWeight;
    }

    public void setWeight(Integer weight) {
        mWeight = weight;
    }

    public com.example.d.healthbook.Models.DiaryModel._id get_id() {
        return m_id;
    }

    public void set_id(com.example.d.healthbook.Models.DiaryModel._id _id) {
        m_id = _id;
    }


    public String getmBirthday2() {
        return mBirthday2;
    }

    public void setmBirthday2(String mBirthday2) {
        this.mBirthday2 = mBirthday2;
    }
}
