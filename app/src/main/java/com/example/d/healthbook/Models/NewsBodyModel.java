package com.example.d.healthbook.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by User on 11.08.2017.
 */

public class NewsBodyModel {
    public class RootObject{
        public List<Data> getData() {
            return data;
        }

        public void setData(List<Data> data) {
            this.data = data;
        }

        @SerializedName("data")
        @Expose
        private List<Data> data;
    }
    public class Data{
        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public _Data getData() {
            return data;
        }

        public void setData(_Data data) {
            this.data = data;
        }

        @SerializedName("type")
        @Expose
        private String type;
        @SerializedName("data")
        @Expose
        private _Data data;
    }
    public class _Data{
        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        @SerializedName("text")
        @Expose

        private String text;
    }
}

