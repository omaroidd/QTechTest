package com.example.qtechtest.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.qtechtest.R;
import com.example.qtechtest.adapter.ProductAdapter;
import com.example.qtechtest.api.Api;
import com.example.qtechtest.api.RetrofitClient;
import com.example.qtechtest.model.Product;
import com.example.qtechtest.model.ProductResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;


public class ProductsActivity extends AppCompatActivity {

    private RecyclerView allProductsRV;
    private ProductAdapter productAdapter;

    private ImageView logOut;
    private SharedPreferences save;


    private ArrayList<ProductResponse> productResponseArrayList = new ArrayList<>();
    private Api apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);

        setReferences();

        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                save = getSharedPreferences("Save", MODE_PRIVATE);
                SharedPreferences.Editor editor = save.edit();
                editor.clear();
                editor.apply();
                startActivity(new Intent(ProductsActivity.this, LoginActivity.class));
                finish();
            }
        });

        getData();

    }

    private void getData() {

        apiService =
                RetrofitClient.getClient().create(Api.class);

        Call<ProductResponse> call = apiService.getAllProducts();
        call.enqueue(new Callback<ProductResponse>() {
            @Override
            public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
                List<Product> products = response.body().getResponse();

                productAdapter = new ProductAdapter(products, ProductsActivity.this);
                allProductsRV.setAdapter(productAdapter);

                Log.d(TAG, "Number of products received: " + products.size());
            }

            @Override
            public void onFailure(Call<ProductResponse> call, Throwable t) {
                Log.e(TAG, t.toString());
            }
        });
    }


    private void setReferences() {
        allProductsRV = findViewById(R.id.productRecyclerView);
        logOut = findViewById(R.id.logOutImageView);

        allProductsRV.setHasFixedSize(true);
        allProductsRV.setLayoutManager(new LinearLayoutManager(this));
    }
}