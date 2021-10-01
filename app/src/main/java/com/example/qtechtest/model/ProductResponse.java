package com.example.qtechtest.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class ProductResponse {
    @SerializedName("data")
    private List<Product> response;

    public List<Product> getResponse() {
        if (response == null) {
            response = new ArrayList<>();
        }
        return response;
    }

    public void setResponse(List<Product> response) {
        this.response = response;
    }


}



