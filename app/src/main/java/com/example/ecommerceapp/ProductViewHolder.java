package com.example.ecommerceapp;
import android.content.Context;
import android.content.Intent;
import android.provider.ContactsContract;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

public class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView  txtProductName, txtProductPrice, txtProductQuantity;
    public ImageView imageView;
    public ItemClickListners listner;

    public ProductViewHolder(View itemView)
    {
        super(itemView);
        imageView = (ImageView) itemView.findViewById(R.id.pImage);
        txtProductName = (TextView) itemView.findViewById(R.id.pName);
        txtProductPrice = (TextView) itemView.findViewById(R.id.pPrice);
        txtProductQuantity = (TextView) itemView.findViewById(R.id.pQuantity);
    }

    public void  setItemClickListner(ItemClickListners listner){
        this.listner = listner;
    }
    @Override
    public void onClick(View view) {
        listner.onClick(view, getAdapterPosition(), false);
    }
}
















