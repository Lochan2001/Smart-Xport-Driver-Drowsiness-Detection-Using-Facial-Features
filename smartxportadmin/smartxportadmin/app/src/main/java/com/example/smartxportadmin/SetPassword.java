package com.example.smartxportadmin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.smartxportadmin.contstants.MyNode;
import com.example.smartxportadmin.pojo.Personal_Info;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

public class SetPassword extends AppCompatActivity {

    EditText contact,password,confirmPWD;
    Button SavePWD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_password);

        password=findViewById(R.id.etpassword);
        confirmPWD=findViewById(R.id.etconfirmPWD);

        SavePWD=findViewById(R.id.btn_Save);

        SavePWD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (password.getText().toString().isEmpty())
                {
                    password.setError("Enter Password");
                    return;
                }
                if (!password.getText().toString().equals(confirmPWD.getText().toString()))
                {
                    confirmPWD.setError("Password mismatch!");
                    return;
                }

                SharedPreferences sh =SetPassword.this.getSharedPreferences("eyetrack", MODE_PRIVATE);
                Gson gson = new Gson();
                String json=  sh.getString("tempuser", "");
                Personal_Info pi=gson.fromJson(json,Personal_Info.class);
                pi.setPassword(password.getText().toString());
                DatabaseReference db= FirebaseDatabase.getInstance().getReference(MyNode.VEHICLE_ADMIN);
                db.child(pi.getContact()).setValue(pi);
                Intent intent=new Intent(SetPassword.this,ActivityLogin.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
    }
}