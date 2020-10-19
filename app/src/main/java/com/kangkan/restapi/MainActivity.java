package com.kangkan.restapi;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.kangkan.restapi.Network.APIUtils;
import com.kangkan.restapi.Network.User_Service;
import com.kangkan.restapi.model.User_Response;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private TextInputEditText editTextName, editTextEmail, editTextPassword,editTextConfirmPassword, editTextPhone;
    Button btn_reg;

    Animation btnAnim ;
    ScrollView scrollView;

    LottieAnimationView animationView;

    User_Service service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    private void init() {
        editTextName = findViewById(R.id.edit_text_name);
        editTextEmail = findViewById(R.id.edit_text_email);
        editTextPassword = findViewById(R.id.edit_text_password);
        editTextConfirmPassword = findViewById(R.id.edit_text_conform_password);
        editTextPhone = findViewById(R.id.edit_text_phone);
        animationView = findViewById(R.id.lottieAnimation);

        scrollView = findViewById(R.id.layout_anim);
        btnAnim = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.bottom_up);
        scrollView.setAnimation(btnAnim);

        service= APIUtils.Add_User();
        btn_reg=findViewById(R.id.button_register);

        btn_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isConnected(MainActivity.this)) {
                    Snackbar snackbar = Snackbar
                            .make(scrollView, "No Internet Connection", Snackbar.LENGTH_SHORT);
                    snackbar.show();
                }else {
                    registerUser();
                }
            }
        });

    }

    private void registerUser() {
        final String name = editTextName.getText().toString().trim();
        final String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String confirm_password = editTextConfirmPassword.getText().toString().trim();
        final String phone = editTextPhone.getText().toString().trim();


        if (validateInputs()){
            btn_reg.setVisibility(View.GONE);
            animationView.setVisibility(View.VISIBLE);
            addUser(name,email,phone,password);
        }

    }

    private void addUser(String name, String email, String phone, String password){

        Call<User_Response> call = service.addUser(name,email,phone,password);
        call.enqueue(new Callback<User_Response>() {

            @Override
            public void onResponse(Call<User_Response> call, Response<User_Response> response) {
                if(response.isSuccessful()){
                    Toast.makeText(MainActivity.this, "User created successfully!", Toast.LENGTH_SHORT).show();
                    btn_reg.setVisibility(View.VISIBLE);
                    animationView.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<User_Response> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d("onFailure",t.getMessage());
                btn_reg.setVisibility(View.VISIBLE);
                animationView.setVisibility(View.GONE);
            }
        });
    }

    public boolean isConnected(Context context) {

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netinfo = cm.getActiveNetworkInfo();

        if (netinfo != null && netinfo.isConnectedOrConnecting()) {
            android.net.NetworkInfo wifi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            android.net.NetworkInfo mobile = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

            if((mobile != null && mobile.isConnectedOrConnecting()) || (wifi != null && wifi.isConnectedOrConnecting())) return true;
            else return false;
        } else
            return false;
    }

    private boolean validateInputs() {
        if (editTextName.length() == 0) {
            editTextName.setError("UserName Can't be Empty");
            editTextName.requestFocus();
            return false;
        }

        if (editTextEmail.length() == 0) {
            editTextEmail.setError("Email Can't be Empty");
            editTextEmail.requestFocus();
            return false;
        }
       /* if (!Patterns.EMAIL_ADDRESS.matcher(editTextEmail).matches()) {
            editTextEmail.setError("Invalid Email Address");
            editTextEmail.requestFocus();
            return false;
        }*/

        if (editTextPhone.length()==0) {
            editTextPhone.setError("Phone Can't be Empty");
            editTextPhone.requestFocus();
            return false;
        }

        if (editTextPhone.length()!=11) {
            editTextPhone.setError("Phone number must be 11 digit");
            editTextPhone.requestFocus();
            return false;
        }

        if (editTextPassword.length()==0) {
            editTextPassword.setError("Password Can't be Empty");
            editTextPassword.requestFocus();
            return false;
        }
        if (editTextPassword.length() < 6) {
            editTextPassword.setError("Password length must be 6 digit");
            editTextPassword.requestFocus();
            return false;
        }
        if (editTextConfirmPassword.length()==0) {
            editTextConfirmPassword.setError("Confirm Password Can't be Empty");
            editTextConfirmPassword.requestFocus();
            return false;
        }
        if (!editTextConfirmPassword.equals(editTextConfirmPassword)) {
            editTextConfirmPassword.setError("Confirm Password and password does not match");
            editTextConfirmPassword.requestFocus();
            return false;
        }
        return true;

    }

}