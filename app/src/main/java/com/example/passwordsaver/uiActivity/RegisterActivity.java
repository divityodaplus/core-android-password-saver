package com.example.passwordsaver.uiActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.passwordsaver.R;
import com.example.passwordsaver.apiRequest.RegisterRequest;
import com.example.passwordsaver.apiResponse.RegisterResponse;
import com.example.passwordsaver.base.BaseActivity;
import com.example.passwordsaver.databinding.ActivityRegisterBinding;
import com.example.passwordsaver.models.FailureResponse;
import com.example.passwordsaver.viewModels.Registration.RegisterViewModel;


public class RegisterActivity extends BaseActivity {
    private ActivityRegisterBinding binding;
    private RegisterViewModel registerViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_register);
        setLiveData();

        binding.buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RegisterRequest registerRequest = new RegisterRequest();
                registerRequest.setUsername(String.valueOf(binding.usernameTextfield.getText()));
                registerRequest.setPassword(String.valueOf(binding.editTextTextPassword.getText()));
                registerRequest.setPassword2(String.valueOf(binding.editTextTextPasswordReenter.getText()));
                registerViewModel.hitRegisterApi(registerRequest);
            }
        });


    }

    private void setLiveData(){
            registerViewModel = new ViewModelProvider(this).get(RegisterViewModel.class);
            registerViewModel.setGenericListeners(getErrorObserver(),getFailureResponseObserver());

            registerViewModel.liveDataRegisterApi().observe(this, new Observer<RegisterResponse>() {
                @Override
                public void onChanged(RegisterResponse registerResponse) {
                    if(registerResponse.getUsername()!=null){
                        Toast.makeText(getApplicationContext(),"Registration successfull please login",Toast.LENGTH_LONG).show();
                        finish();
                    }
                }
            });
    }

    @Override
    protected void onFailure(FailureResponse failureResponse){
        Toast.makeText(this,failureResponse.getErrorMessage(),Toast.LENGTH_LONG).show();
    }
}