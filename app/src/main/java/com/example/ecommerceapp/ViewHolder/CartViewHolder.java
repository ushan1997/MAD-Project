package com.example.ecommerceapp.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.ecommerceapp.InterFace.ItemClickListner;
import com.example.ecommerceapp.R;

public class CartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView productName,productPrice, productDescription,productTotalPrice;
    public TextView productQuantity;
    public ImageView product_image;
    private ItemClickListner itemClickListner;

    public CartViewHolder(@NonNull View itemView) {
        super(itemView);

        productName         = (TextView) itemView.findViewById(R.id.product_name_in_cart);
        productDescription  = (TextView) itemView.findViewById(R.id.product_description_details_in_cart);
        productPrice        = (TextView) itemView.findViewById(R.id.product_cart_price);
        productQuantity     = (TextView) itemView.findViewById(R.id.product_cart_quantity);

        product_image       = (ImageView)itemView.findViewById(R.id.cart_product_image);

        productTotalPrice   = (TextView)itemView.findViewById(R.id.cart_total);

    }

    @Override
    public void onClick(View view) {
        itemClickListner.onClick(view,getAdapterPosition(),false);

    }
}
