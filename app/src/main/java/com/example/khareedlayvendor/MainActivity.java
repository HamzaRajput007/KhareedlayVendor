package com.example.khareedlayvendor;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.khareedlayvendor.Adapters.ProductsList_Adapter;
import com.example.khareedlayvendor.AppUtils.Constants;
import com.example.khareedlayvendor.Models.Models_Products.Model_Products_Details;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.Collections;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public static final String START_WORK = "work";
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;
    private SharedPreferences sharedPreferences_login;
    private TextView textView_name;

    private RecyclerView recyclerView;
    private ArrayList<Model_Products_Details> arrayList_products;
    private ProductsList_Adapter adapter;
    private String vendorId;
    private TextView textView_empty;
    private boolean isScrolling = false;
    private LinearLayoutManager layoutManager;
    private ProgressBar progressBar;
    private NotificationServices notificationServices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        setSupportActionBar(toolbar);

        vendorId = sharedPreferences_login.getString(Constants.KEY_USER_ID,"");

        View hView = navigationView.getHeaderView(0);
        textView_name = hView.findViewById(R.id.tv_name_header);
        textView_name.setText(sharedPreferences_login.getString(Constants.KEY_USER_DISPLAY_NAME,""));

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        bindViews();
        bindDataSoure();

        adapter.setOnItemClickListner(new ProductsList_Adapter.onItemClick() {
            @Override
            public void onClick(Model_Products_Details model_products_details) {

                Intent intent = new Intent(MainActivity.this,ProductsDetailsActivity.class);
                intent.putExtra("model",model_products_details);
                startActivity(intent);
            }
        });

        Intent intent = new Intent(this,NotificationServices.class);
        startService(intent);
        SimpleJobIntentService.enqueueWork(getApplicationContext(),new Intent().setAction("start"));
    }

    private void init() {
        toolbar = findViewById(R.id.toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        textView_empty = findViewById(R.id.tv_empty_products);

        recyclerView = findViewById(R.id.recycler_products);
        arrayList_products = new ArrayList<>();

        sharedPreferences_login = getSharedPreferences(Constants.KEY_LOGIN_PREFRENCES,MODE_PRIVATE);

        progressBar = findViewById(R.id.progress);
    }

    public void bindViews(){
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(MainActivity.this);
        adapter = new ProductsList_Adapter(arrayList_products,MainActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    public void bindDataSoure() {
        if (TextUtils.isEmpty(vendorId)) {
            Toast.makeText(this, "Vendor Id missing", Toast.LENGTH_SHORT).show();
            return;
        }

        Call<ArrayList<Model_Products_Details>> call = RetrofitClient_Base.getInstance().getApi().getProductsList(vendorId, "1");
        call.enqueue(new Callback<ArrayList<Model_Products_Details>>() {
            @Override
            public void onResponse(Call<ArrayList<Model_Products_Details>> call, final Response<ArrayList<Model_Products_Details>> response) {
                    arrayList_products = response.body();
                    adapter.refreshList(response.body());
                    adapter.showShimmer = false;
            }

            @Override
            public void onFailure(Call<ArrayList<Model_Products_Details>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                adapter.showShimmer = false;
                textView_empty.setVisibility(View.VISIBLE);
                textView_empty.setText("Network Error");
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            finish();
            super.onBackPressed();
        }
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        if (menuItem.getItemId() == R.id.logout){
            navigationView.setCheckedItem(R.id.logout);
            SharedPreferences.Editor editor = sharedPreferences_login.edit();
            editor.putBoolean(Constants.KEY_LOGOUT,true);
            editor.apply();
            startActivity(new Intent(MainActivity.this,LoginActivity.class));
            finish();
        }
        if (menuItem.getItemId() == R.id.OrdersList){
            navigationView.setCheckedItem(R.id.logout);
            startActivity(new Intent(MainActivity.this,OrdersList.class));
            finish();
        }
        return true;
    }

    @Override
    protected void onResume() {
        navigationView.setCheckedItem(R.id.ProductsList);
        super.onResume();
    }
}
