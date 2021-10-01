package com.example.qtechtest.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.qtechtest.R;
import com.example.qtechtest.api.Api;
import com.example.qtechtest.api.RetrofitClient;
import com.example.qtechtest.model.LoginRequest;
import com.example.qtechtest.model.LoginResponse;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class LoginActivity extends AppCompatActivity {

    private EditText editTextEmail;
    private EditText editTextPassword;
    private Api apiRequest;

    private SharedPreferences save;


    @Override
    protected void onStart() {
        super.onStart();


        save = getSharedPreferences("Save", MODE_PRIVATE);
        int flag = save.getInt("Save", 5);

        if (flag == 0) {
            // User Login
            startActivity(new Intent(this, ProductsActivity.class));
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);

        apiRequest = RetrofitClient.getClient().create(Api.class);

    }

    public void userLogin(View view) {

        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if (email.isEmpty()) {
            editTextEmail.setError("Email is required");
            editTextEmail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError("Enter a valid email");
            editTextEmail.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            editTextPassword.setError("Password required");
            editTextPassword.requestFocus();
            return;
        }

        if (password.length() < 8) {
            editTextPassword.setError("Password should be at least 8 character long");
            editTextPassword.requestFocus();
            return;
        }

        LoginRequest loginByEmailU = new LoginRequest();
        loginByEmailU.setEmail(email);
        loginByEmailU.setPassword(password);

        apiRequest.LoginByEmail(loginByEmailU)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<LoginResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull LoginResponse loginResponse) {
                        Intent intent = new Intent(LoginActivity.this, ProductsActivity.class);

                        SharedPreferences.Editor editor = save.edit();
                        editor.putInt("Save", 0);
                        editor.apply();

                        startActivity(intent);
                    }

                    @Override
                    public void onError(Throwable e) {
                        editTextEmail.setError("Wrong in Email or password");
                        editTextEmail.requestFocus();
                    }

                    @Override
                    public void onComplete() {
                        finish();
                    }
                });


    }

}