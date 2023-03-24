package com.example.smartxportadmin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ForgotPassword extends AppCompatActivity {
    // variable for FirebaseAuth class
    // variable for our text input
    // field for phone and OTP.
    private EditText edtOTP;
    // buttons for generating OTP and verifying OTP
    private Button verifyOTPBtn;
    // string for storing our verification ID
    private String verificationId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        // initializing variables for button and Edittext.
        edtOTP = findViewById(R.id.idEdtOtp);
        verifyOTPBtn = findViewById(R.id.idBtnVerify);

        Intent intent=this.getIntent();
        String otp=intent.getExtras().getString("otp","");
        Log.i("otp:",otp);
        //Toast.makeText(this, "mail otp:"+otp, Toast.LENGTH_SHORT).show();
        // initializing on click listener
        // for verify otp button
        verifyOTPBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("otp in",otp);
                if(edtOTP.getText().toString().equalsIgnoreCase(otp))
                {
                    Toast.makeText(ForgotPassword.this, "OTP verified", Toast.LENGTH_SHORT).show();
                 Intent intent1=new Intent(ForgotPassword.this,SetPassword.class);
                 startActivity(intent1);
                }
            }
        });
    }
}