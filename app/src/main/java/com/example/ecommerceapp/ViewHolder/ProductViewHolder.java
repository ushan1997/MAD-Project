
/**IT1975140
 Gunaratne U.A
 Metro Weekday*/

package com.example.ecommerceapp.ViewHolder;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecommerceapp.InterFace.ItemClickListner;
import com.example.ecommerceapp.R;


public class ProductViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener{

    public ImageButton imageViewButton;
    public TextView productName,productDescription,productPrice;
    public ItemClickListner listner;

    public ProductViewHolder(@NonNull View itemView) {
        super(itemView);

        imageViewButton = (ImageButton)itemView.findViewById(R.id.product_image_on_home);
         productName =(TextView )itemView.findViewById(R.id.product_name_details_on_home);
         productDescription =(TextView)itemView.findViewById(R.id.product_description_on_home);
         productPrice = (TextView)itemView.findViewById(R.id.product_price);

    }

    public void setItemClickListner(ItemClickListner listner){

        this.listner = listner;//get item click ItemClickListner class
    }


    @Override
    public void onClick(View view) {

        listner.onClick(view , getAdapterPosition() ,false);
    }



}
