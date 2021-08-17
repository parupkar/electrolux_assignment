package com.sample.electroluxtest.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ConnectionResponse {

    @SerializedName("status")
    @Expose
    private boolean status;
}