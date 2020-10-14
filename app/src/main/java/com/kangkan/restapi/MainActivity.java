package com.kangkan.restapi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

public class MainActivity extends AppCompatActivity {

    private TextInputEditText editTextName, editTextEmail, editTextPassword,editTextConfirmPassword, editTextPhone;
    Button btn_reg;

    Animation btnAnim ;
    NestedScrollView scrollView;

    LottieAnimationView animationView;

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


        if (name.isEmpty()) {
            editTextName.setError("UserName Can't be Empty");
            editTextName.requestFocus();
            return;
        }

        if (email.isEmpty()) {
            editTextEmail.setError("Email Can't be Empty");
            editTextEmail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError("Invalid Email Address");
            editTextEmail.requestFocus();
            return;
        }

        if (phone.isEmpty()) {
            editTextPhone.setError("Phone Can't be Empty");
            editTextPhone.requestFocus();
            return;
        }

        if (phone.length() != 11) {
            editTextPhone.setError("Phone number must be 11 digit");
            editTextPhone.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            editTextPassword.setError("Password Can't be Empty");
            editTextPassword.requestFocus();
            return;
        }
        if (password.length() < 6) {
            editTextPassword.setError("Password length must be 6 digit");
            editTextPassword.requestFocus();
            return;
        }
        if (confirm_password.isEmpty()) {
            editTextConfirmPassword.setError("Confirm Password Can't be Empty");
            editTextConfirmPassword.requestFocus();
            return;
        }
        if (!confirm_password.equals(password)) {
            editTextConfirmPassword.setError("Confirm Password and password does not match");
            editTextConfirmPassword.requestFocus();
            return;
        }

        btn_reg.setVisibility(View.GONE);
        animationView.setVisibility(View.VISIBLE);

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

}