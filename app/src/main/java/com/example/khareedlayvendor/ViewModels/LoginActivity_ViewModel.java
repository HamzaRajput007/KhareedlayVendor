package com.example.khareedlayvendor.ViewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.khareedlayvendor.Models.Models_Login.Model_Login_CombinedData;
import com.example.khareedlayvendor.Repositry.LoginActivity_Repositry;

public class LoginActivity_ViewModel extends ViewModel {
    private MutableLiveData<Model_Login_CombinedData> data;
    public MutableLiveData<String> toast;
    private LoginActivity_Repositry repositry;

    public void init(String userName,String password){
        repositry = new LoginActivity_Repositry(userName,password);
        this.data = repositry.getLoginDetails();
        this.toast = repositry.toast;
    }

    public LiveData<Model_Login_CombinedData> getLoginData(){
        return this.data;
    }

    public LiveData<String> getToast(){
        return this.toast;
    }
}
