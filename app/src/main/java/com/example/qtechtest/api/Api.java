package com.example.qtechtest.api;

import com.example.qtechtest.model.LoginRequest;
import com.example.qtechtest.model.LoginResponse;
import com.example.qtechtest.model.ProductResponse;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;


public interface Api {

    /*
    Authentication
     */
    @POST("auth/login")
    Observable<LoginResponse> LoginByEmail(@Body LoginRequest loginByEmailRequest);

    @GET("get_all_products")
    Call<ProductResponse> getAllProducts();

}
