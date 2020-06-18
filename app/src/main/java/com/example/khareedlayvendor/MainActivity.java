package com.example.khareedlayvendor;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
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
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;
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
//                Toast.makeText(notificationServices, String.valueOf(model_products_details.getId()), Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        });

        Intent intent = new Intent(this,NotificationServices.class);
        startService(intent);
        SimpleJobIntentService.enqueueWork(getApplicationContext(),new Intent().setAction("start"));
    }


    private  boolean checkAndRequestPermissions() {
        int ReadMessage = ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE);
        int WritePermission = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int InternetPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET);
        int AudioPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO);
        List<String> listPermissionsNeeded = new ArrayList<>();
        if (ReadMessage != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }
        if (WritePermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (InternetPermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.INTERNET);
        }
        if (AudioPermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.RECORD_AUDIO);
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]),REQUEST_ID_MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_ID_MULTIPLE_PERMISSIONS: {

                Map<String, Integer> perms = new HashMap<>();
                // Initialize the map with both permissions
                perms.put(Manifest.permission.READ_EXTERNAL_STORAGE, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.WRITE_EXTERNAL_STORAGE, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.INTERNET, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.RECORD_AUDIO, PackageManager.PERMISSION_GRANTED);
                // Fill with actual results from user
                if (grantResults.length > 0) {
                    for (int i = 0; i < permissions.length; i++)
                        perms.put(permissions[i], grantResults[i]);
                    // Check for both permissions
                    if (perms.get(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                            && perms.get(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                            && perms.get(Manifest.permission.INTERNET) == PackageManager.PERMISSION_GRANTED
                            && perms.get(Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED) {

                        // process the normal flow
                        //else any one or both the permissions are not granted
                    } else {
                        //permission is denied (this is the first time, when "never ask again" is not checked) so ask again explaining the usage of permission
//                        // shouldShowRequestPermissionRationale will return true
                        //show the dialog or snackbar saying its necessary and try again otherwise proceed with setup.
                        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE) || ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) || ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.INTERNET) || ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.RECORD_AUDIO)) {
                            showDialogOK("Read, Write, Internet and Record Audio Permission required for this app",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            switch (which) {
                                                case DialogInterface.BUTTON_POSITIVE:
                                                    checkAndRequestPermissions();
                                                    break;
                                                case DialogInterface.BUTTON_NEGATIVE:
                                                    // proceed with logic by disabling the related features or quit the app.
                                                    break;
                                            }
                                        }
                                    });
                        }
                        //permission is denied (and never ask again is  checked)
                        //shouldShowRequestPermissionRationale will return false
                        else {
                            Toast.makeText(this, "Go to settings and enable permissions", Toast.LENGTH_LONG)
                                    .show();
                            //                            //proceed with logic by disabling the related features or quit the app.
                        }
                    }
                }
            }
        }

    }

    private void showDialogOK(String s, DialogInterface.OnClickListener onClickListener) {
        Toast.makeText(notificationServices, "Permissions Granted", Toast.LENGTH_SHORT).show();
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
