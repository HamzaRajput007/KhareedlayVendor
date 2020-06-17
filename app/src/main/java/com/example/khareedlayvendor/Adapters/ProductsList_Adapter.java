package com.example.khareedlayvendor.Adapters;

import android.content.Context;
import android.graphics.Paint;
import android.media.Image;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.khareedlayvendor.Models.Models_Products.Model_Products_Details;
import com.example.khareedlayvendor.R;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ProductsList_Adapter extends RecyclerView.Adapter<ProductsList_Adapter.MyViewHolder> {

    private ArrayList<Model_Products_Details> arrayList;
    private Context context;
    public boolean showShimmer = true;
    private int SHIMMER_ITEM_NUMBER = 5;
    private onItemClick listner;

    public ProductsList_Adapter(ArrayList<Model_Products_Details> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    public void refreshList(ArrayList<Model_Products_Details> arrayList){
        this.arrayList = arrayList;
        notifyDataSetChanged();
    }


    public interface onItemClick{
        void onClick(Model_Products_Details model_products_details);
    }

    public void setOnItemClickListner(onItemClick listner){
        this.listner = listner;
    }

    @NonNull
    @Override
    public ProductsList_Adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.row_item_products_list,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ProductsList_Adapter.MyViewHolder holder, int position) {

        if (showShimmer){
            holder.shimmerFrameLayout.startShimmer();
        }
        else {
            holder.shimmerFrameLayout.stopShimmer();
            holder.shimmerFrameLayout.setShimmer(null);
            final Model_Products_Details model_products_details = arrayList.get(position);
            holder.productName.setText(model_products_details.getName());
            holder.productName.setBackground(null);
            if (!TextUtils.isEmpty(model_products_details.getArrayList_images().get(0))) {
                Picasso.get().load(model_products_details.getArrayList_images().get(0)).placeholder(R.drawable.default_image).into(holder.imageView);
                holder.imageView.setBackground(null);
            } else {
                holder.imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.default_image));
                holder.imageView.setBackground(null);
            }
            if (!TextUtils.isEmpty(model_products_details.getRegular_price())) {
                holder.regularPrice.setText("Rs: "+model_products_details.getRegular_price());
                holder.regularPrice.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                holder.regularPrice.setBackground(null);
            } else {
                holder.regularPrice.setVisibility(View.GONE);
                holder.regularPrice.setBackground(null);
            }
            if (!TextUtils.isEmpty(model_products_details.getSale_price())) {
                holder.salePrice.setText("Rs:"+model_products_details.getSale_price());
                holder.salePrice.setBackground(null);
            }

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listner.onClick(model_products_details);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return showShimmer ? SHIMMER_ITEM_NUMBER : arrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView productName,regularPrice,salePrice;
        ShimmerFrameLayout shimmerFrameLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.product_image);
            productName = itemView.findViewById(R.id.tv_productName);
            regularPrice = itemView.findViewById(R.id.tv_regularPrice);
            salePrice = itemView.findViewById(R.id.tv_salePrice);
            shimmerFrameLayout = itemView.findViewById(R.id.shimmerLayout);
        }
    }
}
