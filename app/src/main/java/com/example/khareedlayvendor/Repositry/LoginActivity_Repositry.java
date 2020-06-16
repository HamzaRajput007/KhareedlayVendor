package com.example.khareedlayvendor.Repositry;

import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import com.example.khareedlayvendor.Models.Models_Login.Model_Login_Call;
import com.example.khareedlayvendor.Models.Models_Login.Model_Login_CombinedData;
import com.example.khareedlayvendor.Models.Models_Login.Model_Login_UserDetails;
import com.example.khareedlayvendor.RetrofitClient_Base;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity_Repositry {

    private MutableLiveData<Model_Login_CombinedData> data = new MutableLiveData<>();
    public MutableLiveData<String> toast = new MutableLiveData<>();
    private String userName,password;
    private Model_Login_UserDetails model_login_userDetails = new Model_Login_UserDetails();
    private ArrayList<String> arrayList = new ArrayList<>();
    private Model_Login_CombinedData model_login_combinedData = new Model_Login_CombinedData();

    public LoginActivity_Repositry(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public MutableLiveData<Model_Login_CombinedData> getLoginDetails(){
        Call<Model_Login_Call> call = RetrofitClient_Base.getInstance().getApi().getLoginInfo(userName,password);
        call.enqueue(new Callback<Model_Login_Call>() {
            @Override
            public void onResponse(Call<Model_Login_Call> call, Response<Model_Login_Call> response) {

                if ("<strong>Error</strong>: The password field is empty.".equalsIgnoreCase(response.toString()) ||
                "Unknown username. Check again or try your email address.".equalsIgnoreCase(response.toString()) ||
                "<strong>Error</strong>: The username field is empty.".equalsIgnoreCase(response.toString()) ||
                "<strong>Error</strong>: The password you entered for the username <strong>rockingwahab9</strong> is incorrect. <a href=\\\"https://khareedlaay.com/my-account/lost-password/\\\">Lost your password?</a>".equalsIgnoreCase(response.toString()))
                {
                    toast.setValue("Username or Password InCorrect");
                }
                else {

                    model_login_userDetails = response.body().getModel_login_userDetails();
                    arrayList = response.body().getArrayList();

                    model_login_combinedData.setUser_id(model_login_userDetails.getID());
                    model_login_combinedData.setUserName(model_login_userDetails.getUserName());
                    model_login_combinedData.setUserPass(model_login_userDetails.getUser_pass());
                    model_login_combinedData.setUser_nicename(model_login_userDetails.getUser_nicename());
                    model_login_combinedData.setUser_email(model_login_userDetails.getUser_email());
                    model_login_combinedData.setUser_registrationDateTime(model_login_userDetails.getUser_registered());
                    model_login_combinedData.setUser_status(model_login_userDetails.getUser_status());
                    model_login_combinedData.setDisplay_name(model_login_userDetails.getDisplay_name());
                    model_login_combinedData.setRole(arrayList.get(0));

                    data.setValue(model_login_combinedData);
                }
            }

            @Override
            public void onFailure(Call<Model_Login_Call> call, Throwable t) {
                toast.setValue("Username or Password InCorrect");
            }
        });

        return data;
    }
}
