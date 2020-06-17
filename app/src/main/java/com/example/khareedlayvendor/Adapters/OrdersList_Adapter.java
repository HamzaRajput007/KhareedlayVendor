package com.example.khareedlayvendor.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.khareedlayvendor.Models.Model_Orders.Model_OrderDetails;
import com.example.khareedlayvendor.R;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class OrdersList_Adapter extends RecyclerView.Adapter<OrdersList_Adapter.MyViewHolder> {

    private ArrayList<Model_OrderDetails> arrayList_orders;
    private Context context;
    public boolean showShimmer = true;
    private int SHOW_SHIMMER_NUMBER = 5;
    public onItemClick listner ;

    public OrdersList_Adapter(ArrayList<Model_OrderDetails> arrayList_orders, Context context ) {
        this.arrayList_orders = arrayList_orders;
        this.context = context;
    }

    public  void setOnItemClickListner(onItemClick listner){
        this.listner = listner;
    }

    public void refresh(ArrayList<Model_OrderDetails> arrayList_orders ){
        this.arrayList_orders = arrayList_orders;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public OrdersList_Adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.row_item_orders_list,parent,false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull OrdersList_Adapter.MyViewHolder holder, int position) {
        if (showShimmer){
            holder.shimmerFrameLayout.startShimmer();
        }
        else {
            holder.shimmerFrameLayout.stopShimmer();
            holder.shimmerFrameLayout.setShimmer(null);

            final Model_OrderDetails model_orderDetails = arrayList_orders.get(position);

            holder.imageView.setBackground(null);
            Picasso.get().load(R.drawable.default_image).into(holder.imageView);

            holder.textView_customerName.setBackground(null);
            holder.textView_customerName.setText(model_orderDetails.getModel_customerDetails().getFirst_name()+" "+
                    model_orderDetails.getModel_customerDetails().getLast_name());

            holder.textView_dateTime.setText(model_orderDetails.getModel_orderDateTime().getDate());
            holder.textView_dateTime.setBackground(null);

            holder.textView_orderDetails_text.setBackground(null);
            holder.textView_orderDetails_text.setText("Order Details");

            holder.linearLayout_items.setBackground(null);
            holder.textView_totalitemsText.setText("Total Items: ");
            holder.textView_totalitems.setText(String.valueOf(model_orderDetails.getArrayList_itemsDetails().size()));

            holder.linearLayout_bill.setBackground(null);
            holder.textView_totalbillText.setText("Total Bill: ");
            holder.textView_totalbill.setText(model_orderDetails.getTotal());

            holder.linearLayout_payment.setBackground(null);
            holder.textView_paymentmethodText.setText("Payment Method: ");
            holder.textView_paymentMethod.setText(model_orderDetails.getPayment_title());

            String orderStatus = model_orderDetails.getStatus();

            switch (orderStatus){

                case "pending":
                    holder.textView_orderStatus.setBackgroundColor(context.getResources().getColor(R.color.order_pending_color));
                    holder.textView_orderStatus.setText(model_orderDetails.getStatus());
                    break;
                case "processing":
                    holder.textView_orderStatus.setBackgroundColor(context.getResources().getColor(R.color.order_processing_color));
                    holder.textView_orderStatus.setText(model_orderDetails.getStatus());
                    break;

                case "on-hold":
                    holder.textView_orderStatus.setBackgroundColor(context.getResources().getColor(R.color.order_on_hold_color));
                    holder.textView_orderStatus.setText(model_orderDetails.getStatus());
                    break;

                case "completed":
                    holder.textView_orderStatus.setBackgroundColor(context.getResources().getColor(R.color.order_completed_color));
                    holder.textView_orderStatus.setText(model_orderDetails.getStatus());
                    break;

                case "cancelled":
                    holder.textView_orderStatus.setBackgroundColor(context.getResources().getColor(R.color.order_cancelled_color));
                    holder.textView_orderStatus.setText(model_orderDetails.getStatus());
                    break;

                case "refunded":
                    holder.textView_orderStatus.setBackgroundColor(context.getResources().getColor(R.color.order_refunded_color));
                    holder.textView_orderStatus.setText(model_orderDetails.getStatus());
                    break;

                case "failed":
                    holder.textView_orderStatus.setBackgroundColor(context.getResources().getColor(R.color.order_failed_color));
                    holder.textView_orderStatus.setText(model_orderDetails.getStatus());
                    break;

                case "trash":
                    holder.textView_orderStatus.setBackgroundColor(context.getResources().getColor(R.color.order_trash_color));
                    holder.textView_orderStatus.setText(model_orderDetails.getStatus());
                    break;

                default:
                    Toast.makeText(context, orderStatus + " is not a valid order status ", Toast.LENGTH_SHORT).show();
                    break;
            }

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listner.onClick(model_orderDetails);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return showShimmer ? SHOW_SHIMMER_NUMBER : arrayList_orders.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView textView_customerName,textView_dateTime,textView_orderDetails_text,textView_totalbillText,textView_totalbill,textView_totalitemsText,
        textView_totalitems,textView_paymentmethodText,textView_paymentMethod,textView_orderStatus;
        ShimmerFrameLayout shimmerFrameLayout;
        LinearLayout linearLayout_items,linearLayout_bill,linearLayout_payment;


        public MyViewHolder(@NonNull View itemView ) {
            super(itemView);

            imageView = itemView.findViewById(R.id.image_orders);
            textView_customerName = itemView.findViewById(R.id.customerName);
            textView_dateTime = itemView.findViewById(R.id.orderDateTime);
            textView_orderDetails_text = itemView.findViewById(R.id.tv_order);
            textView_totalitemsText = itemView.findViewById(R.id.tv_totalItems_Text);
            textView_totalitems = itemView.findViewById(R.id.tv_totalItems);
            textView_totalbillText = itemView.findViewById(R.id.tv_totalbill_Text);
            textView_totalbill = itemView.findViewById(R.id.tv_totalBill);
            textView_paymentmethodText = itemView.findViewById(R.id.tv_patmentMethod_Text);
            textView_paymentMethod = itemView.findViewById(R.id.tv_paymentMethod);
            textView_orderStatus = itemView.findViewById(R.id.tv_orderStatus);
            shimmerFrameLayout = itemView.findViewById(R.id.shimmerLayout_ordersList);

            linearLayout_items = itemView.findViewById(R.id.linearItems);
            linearLayout_bill = itemView.findViewById(R.id.linearbills);
            linearLayout_payment = itemView.findViewById(R.id.linearpayment);


        }
        

    }

    public interface onItemClick{
        void onClick(Model_OrderDetails model_orderDetails);
    }

}
