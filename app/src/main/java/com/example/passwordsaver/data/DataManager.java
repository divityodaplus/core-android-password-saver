package com.example.passwordsaver.data;

import android.content.Context;

import androidx.lifecycle.Observer;

import com.example.passwordsaver.api.ApiManager;
import com.example.passwordsaver.apiRequest.CreatePasswordRequest;
import com.example.passwordsaver.apiRequest.LoginRequest;
import com.example.passwordsaver.apiRequest.RegisterRequest;
import com.example.passwordsaver.apiResponse.LoginResponse;
import com.example.passwordsaver.apiResponse.RegisterResponse;
import com.example.passwordsaver.apiResponse.WebsiteLogo.WebsiteLogoresponse;
import com.example.passwordsaver.apiResponse.createPassword.CreatePasswordResponse;
import com.example.passwordsaver.apiResponse.passwordList.PasswordListResponse;
import com.example.passwordsaver.base.RichMediatorLiveData;
import com.example.passwordsaver.constants.AppConstants;
import com.example.passwordsaver.models.FailureResponse;
import com.example.passwordsaver.repo.LoginRepo;

import retrofit2.Call;

public class DataManager {

    private ApiManager apiManager;

    private static DataManager instance;
    public Context context;

    private PreferenceManager preferenceManager;

    public DataManager(Context context) {

        this.context = context;
        preferenceManager = PreferenceManager.getInstance(context);
    }

    public static DataManager   getInstance() {
        if (instance == null) {
            throw new IllegalStateException("Call init() before getInstance()");
        }
        return instance;
    }
    public synchronized static DataManager init(Context context) {
        if (instance == null) {
            instance = new DataManager(context);
        }
        return instance;
    }
    private Observer<Throwable> mErrorObserver;

    private Observer<FailureResponse> mFailureObserver;

    private RichMediatorLiveData<LoginResponse> loginResponseRichMediatorLiveData;

    public void initApiManager(){
        apiManager = ApiManager.getInstance();
    }

    public Call<LoginResponse> hitLoginApi(LoginRequest loginRequest){
        return apiManager.hitLoginApi(loginRequest);
    }
    public void setGenericListeners(Observer<Throwable> errorObserver,
                                    Observer<FailureResponse> failureResponseObserver) {
        this.mErrorObserver = errorObserver;
        this.mFailureObserver = failureResponseObserver;
        initLiveData();
    }

    private void initLiveData(){
        if(loginResponseRichMediatorLiveData == null){
            loginResponseRichMediatorLiveData = new RichMediatorLiveData<LoginResponse>() {
                @Override
                protected Observer<FailureResponse> getFailureObserver() {
                    return mFailureObserver;
                }

                @Override
                protected Observer<Throwable> getErrorObserver() {
                    return mErrorObserver;
                }
            };
        }
    }

    public Call<LoginResponse> hitApiLogin(LoginRequest loginRequest){
        return apiManager.hitLoginApi(loginRequest);
    }

    public  Call<RegisterResponse> hitRegisterApi(RegisterRequest registerRequest){
        return  apiManager.hitRegisterApi(registerRequest);
    }

    public void setAccessToken(String access_token){
        preferenceManager.putString(AppConstants.ACCESS_TOKEN_KEY,access_token);
    }

    public String getAccessToken(){
        return preferenceManager.getString(AppConstants.ACCESS_TOKEN_KEY);
    }

    public  void setRefreshToken(String refresh_token){
        preferenceManager.putString(AppConstants.REFRESH_TOKEN_KEY,refresh_token);
    }

    public void getRefreshToken(){
        preferenceManager.getString(AppConstants.REFRESH_TOKEN_KEY);
    }

    public Call<PasswordListResponse> hitPasswordList(){
        return apiManager.hitPasswordList();
    }

    public  Call<WebsiteLogoresponse> fetchLogo(String url){return apiManager.fetchLogo(url);}

    public Call<CreatePasswordResponse> hitCreatePassword(CreatePasswordRequest createPasswordRequest){
        return apiManager.hitCreatePassword(createPasswordRequest);
    }
}
