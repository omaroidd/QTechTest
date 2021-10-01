package com.example.qtechtest.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.qtechtest.R;
import com.example.qtechtest.model.Product;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private List<Product> items;
    private Context context;

    public ProductAdapter(List<Product> items, Context context) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.row_product_layouts, parent, false);

        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {

        Product product = items.get(position);
        ProductViewHolder productViewHolder = (ProductViewHolder) holder;

        productViewHolder.productName.setText(product.getName());
        productViewHolder.productDescription.setText(product.getDescription().trim());
        productViewHolder.productColor.setText("Color: " + product.getColor().trim());
        productViewHolder.productSize.setText("Storge: " + product.getSize().trim());

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder {

        private AppCompatTextView productName, productDescription, productColor, productSize;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);

            productName = itemView.findViewById(R.id.productNameTextView);
            productDescription = itemView.findViewById(R.id.productDescriptionTextView);
            productColor = itemView.findViewById(R.id.productColorTextView);
            productSize = itemView.findViewById(R.id.productSizeTextView);

        }
    }
}
