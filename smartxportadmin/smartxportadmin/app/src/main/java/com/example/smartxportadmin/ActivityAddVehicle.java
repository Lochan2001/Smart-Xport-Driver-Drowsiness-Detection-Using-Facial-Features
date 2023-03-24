package com.example.smartxportadmin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.smartxportadmin.contstants.MyNode;
import com.example.smartxportadmin.pojo.Personal_Info;
import com.example.smartxportadmin.pojo.VehicleInfo;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

public class ActivityAddVehicle extends AppCompatActivity {
    EditText etname,etcontact,etvehicle,etpwd;
    Button btadd;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_vehicle);

        etname=findViewById(R.id.etname);
        etcontact=findViewById(R.id.etcontact);
        etvehicle=findViewById(R.id.etveh);
        etpwd=findViewById(R.id.etpwd);

        btadd=findViewById(R.id.btAdd);
        databaseReference = FirebaseDatabase.getInstance().getReference(MyNode.VEHICLE_INFO);
        btadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean flag=false;
                if(etname.getText().toString().equalsIgnoreCase(""))
                {
                    etname.setError("Enter name");
                    flag=true;
                }

                if(etcontact.getText().toString().length()!=10)
                {
                    etcontact.setError("Enter valid contact");
                    flag=true;
                }
                if(etvehicle.getText().toString().equalsIgnoreCase(""))
                {
                    etvehicle.setError("Enter vehicle no");
                    flag=true;
                }
                if(etpwd.getText().toString().equalsIgnoreCase("") )
                {
                    etpwd.setError("Enter password");
                    flag=true;
                }
                if(flag)
                {
                    return;
                }
                SharedPreferences prefs = ActivityAddVehicle.this.getSharedPreferences("eyetrack", MODE_PRIVATE);

                Gson gson = new Gson();
                String json = prefs.getString("admin", "");
                Personal_Info pi = gson.fromJson(json, Personal_Info.class);
                Log.i("#admin:",pi.getName());

                VehicleInfo vi=new VehicleInfo();
                vi.setVehicleAdmin(pi.getContact());
                vi.setDriverContact(etcontact.getText().toString());
                vi.setDriverName(etname.getText().toString());
                vi.setVehicleNo(etvehicle.getText().toString());
                vi.setPassword(etpwd.getText().toString());
                vi.setLatitude("0.0");
                vi.setLongitude("0.0");
                vi.setSpeed("0.0");


                databaseReference.child(vi.getVehicleNo()).setValue(vi, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(DatabaseError databaseError, DatabaseReference dr) {
                        Toast.makeText(ActivityAddVehicle.this, "Vehicle added "+dr.getKey(), Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(ActivityAddVehicle.this, ActivityDashboard.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                });
            }

        });
    }
}