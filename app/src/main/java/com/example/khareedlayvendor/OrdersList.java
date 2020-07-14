package com.example.khareedlayvendor;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.khareedlayvendor.Adapters.OrdersList_Adapter;
import com.example.khareedlayvendor.AppUtils.Constants;
import com.example.khareedlayvendor.Models.Model_Orders.Model_OrderDetails;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrdersList extends AppCompatActivity{

    private RecyclerView recyclerView;
    private ArrayList<Model_OrderDetails> arrayList_orders;
    private OrdersList_Adapter adapter;
    private String consumer_key = "ck_cec24f9d9e3882b3b6ba0f4db13127b564fec9bb";
    private String consumer_secrete = "cs_f18b2f622fa1ad0cbd7bfdcf730ad6ddc36d2bc0";
    private SharedPreferences sharedPreferences;
    private String vendorId;


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if (id==android.R.id.home) {
            Intent toMain = new Intent(getApplicationContext() , MainActivity.class);
            startActivity(toMain);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders_list);
        init();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(OrdersList.this));
        adapter = new OrdersList_Adapter(arrayList_orders, OrdersList.this );
        recyclerView.setAdapter(adapter);
        vendorId = sharedPreferences.getString(Constants.KEY_USER_ID,"");
        getOrders();
    }
    private void init(){
        recyclerView = findViewById(R.id.recycler_ordersList);
        arrayList_orders = new ArrayList<>();
        sharedPreferences = getSharedPreferences(Constants.KEY_LOGIN_PREFRENCES,MODE_PRIVATE);
    }
    private void getOrders(){
        Call<ArrayList<Model_OrderDetails>> call = RetrofitClient_Base.getInstance().getApi().getOrders(vendorId,consumer_key,consumer_secrete);
        call.enqueue(new Callback<ArrayList<Model_OrderDetails>>() {
            @Override
            public void onResponse(Call<ArrayList<Model_OrderDetails>> call, Response<ArrayList<Model_OrderDetails>> response) {
                adapter.showShimmer = false;
                adapter.refresh(response.body());
                adapter.setOnItemClickListner(new OrdersList_Adapter.onItemClick() {
                    @Override
                    public void onClick(Model_OrderDetails model_orderDetails) {
                        Toast.makeText(getApplicationContext(), "Status : " + arrayList_orders.get(0).getStatus(), Toast.LENGTH_SHORT).show();
                        Intent checkIntent = new Intent(getApplicationContext() , MainActivity.class);
                        startActivity(checkIntent);
                    }
                });
            }

            @Override
            public void onFailure(Call<ArrayList<Model_OrderDetails>> call, Throwable t) {
                Toast.makeText(OrdersList.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
                adapter.showShimmer = false;
            }
        });
    }
}
