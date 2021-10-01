package com.example.qtechtest.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class LoginResponse{
        @SerializedName("response")
        private ArrayList<Response> response;

        public ArrayList<Response> getResponse() {
            if(response== null){
                response = new ArrayList<>();
            }
            return response;
        }

        public void setResponse(ArrayList<Response> response) {
            this.response = response;
        }

        public static class Response implements Serializable{

        }

    }



