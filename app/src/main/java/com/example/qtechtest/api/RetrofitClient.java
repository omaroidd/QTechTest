package com.example.qtechtest.api;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.concurrent.TimeUnit;

import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static String BASE_URL = "https://qtechnetworks.co/testapi/public/api/";
    private static Retrofit retrofit = null;

    public static Retrofit getClient() {

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.connectTimeout(120, TimeUnit.SECONDS);
        httpClient.writeTimeout(120, TimeUnit.SECONDS);
        httpClient.readTimeout(120, TimeUnit.SECONDS);
        httpClient.retryOnConnectionFailure(true);

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.level(HttpLoggingInterceptor.Level.BODY);
        httpClient.addInterceptor(loggingInterceptor);


        httpClient.addInterceptor(chain -> {
            Request request = chain.request().newBuilder()
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Authorization","bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJodHRwOlwvXC9xdGVjaG5ldHdvcmtzLmNvXC90ZXN0YXBpXC9wdWJsaWNcL2FwaVwvYXV0aFwvbG9naW4iLCJpYXQiOjE2MzI5NDU4NTYsIm5iZiI6MTYzMjk0NTg1NiwianRpIjoiNEFjN0NvN3c4WkxsUHZvMCIsInN1YiI6MSwicHJ2IjoiMjNiZDVjODk0OWY2MDBhZGIzOWU3MDFjNDAwODcyZGI3YTU5NzZmNyJ9.mW642CX2if8a7o3rGhmbWxL6_P9MuvPJrDQfyUPR-LE")
                    .build();

            Response response = chain.proceed(request);
            // **
            // 2. add the headers from the Interceptor to our deserializer instance
            // **
            Headers headers = response.headers();
            String code = headers.get("Code");
            String message = headers.get("Message");

            return response;
        });


        OkHttpClient build = httpClient.build();
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(build)
                .build();

        return retrofit;
    }
}