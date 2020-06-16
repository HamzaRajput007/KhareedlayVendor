package com.example.khareedlayvendor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


import com.example.khareedlayvendor.AppUtils.Constants;
import com.example.khareedlayvendor.Models.Models_Login.Model_Login_CombinedData;
import com.example.khareedlayvendor.ViewModels.LoginActivity_ViewModel;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private EditText editText_username;
    private EditText editText_password;
    private MaterialButton button_login;
    private LoginActivity_ViewModel viewModel;
    private SharedPreferences sharedPreferences_login;
    private Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();
        if (!sharedPreferences_login.getBoolean(Constants.KEY_LOGOUT,true)){
            startActivity(new Intent(LoginActivity.this,MainActivity.class));
            finish();
        }


        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData();
            }
        });
    }

    private void init() {

        viewModel = ViewModelProviders.of(LoginActivity.this).get(LoginActivity_ViewModel.class);

        sharedPreferences_login = getSharedPreferences(Constants.KEY_LOGIN_PREFRENCES,MODE_PRIVATE);

        editText_username = findViewById(R.id.et_usernameOrEmailAddress);
        editText_password = findViewById(R.id.et_password);
        button_login = findViewById(R.id.button_login);

        dialog = new Dialog();
    }

    public void getData(){
        String username = editText_username.getText().toString().trim();
        String password = editText_password.getText().toString().trim();

        if (TextUtils.isEmpty(username) || username.equals("")){
            editText_username.setError("This field is required");
            editText_username.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(password) || password.equals("")){
            editText_password.setError("This field is required");
            editText_password.requestFocus();
            return;
        }

        dialog.startDialog(LoginActivity.this);
        viewModel.init(username,password);
        viewModel.getLoginData().observe(LoginActivity.this, new Observer<Model_Login_CombinedData>() {
            @Override
            public void onChanged(Model_Login_CombinedData model_login_combinedData) {
                if (model_login_combinedData.getRole().equals("seller")) {
                    SharedPreferences.Editor editor = sharedPreferences_login.edit();
                    editor.putString(Constants.KEY_USER_ID, model_login_combinedData.getUser_id());
                    editor.putString(Constants.KEY_ROLE, model_login_combinedData.getRole());
                    editor.putString(Constants.KEY_USER_DISPLAY_NAME, model_login_combinedData.getDisplay_name());
                    editor.putString(Constants.KEY_USER_EMAIL, model_login_combinedData.getUser_email());
                    editor.putString(Constants.KEY_USER_REGISTRATION_DATETIME, model_login_combinedData.getUser_registrationDateTime());
                    editor.putString(Constants.kEY_USER_NICENAME, model_login_combinedData.getUser_nicename());
                    editor.putString(Constants.KEY_USER_NAME, model_login_combinedData.getUserName());
                    editor.putBoolean(Constants.KEY_LOGOUT,false);
                    editor.apply();
                    dialog.progressDialog.dismiss();
                    startService(new Intent(LoginActivity.this,NotificationServices.class));
                    startActivity(new Intent(LoginActivity.this,MainActivity.class));
                    finish();
                }
                else {
                    dialog.progressDialog.dismiss();
                    Toast.makeText(LoginActivity.this, "No Data Found", Toast.LENGTH_SHORT).show();
                }
            }
        });

        viewModel.getToast().observe(LoginActivity.this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                dialog.progressDialog.dismiss();
                Toast.makeText(LoginActivity.this, ""+s, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
