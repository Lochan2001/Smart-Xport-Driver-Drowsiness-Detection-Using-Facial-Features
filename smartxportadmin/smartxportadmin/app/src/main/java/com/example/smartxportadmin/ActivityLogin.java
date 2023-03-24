package com.example.smartxportadmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.creativityapps.gmailbackgroundlibrary.BackgroundMail;
import com.example.smartxportadmin.contstants.MyNode;
import com.example.smartxportadmin.pojo.Personal_Info;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.Random;

public class ActivityLogin extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    EditText etname, etcontact, etemail, etpwd;
    Button btsignup, btlogin;
    TextView tvForgotPWd;
    DatabaseReference databaseReference;

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ImageView menuIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity__login);

        etcontact = findViewById(R.id.etcontact);
        etpwd = findViewById(R.id.etpwd);
        tvForgotPWd = findViewById(R.id.ForgotPassword);
        btsignup = findViewById(R.id.btAdd);
        btlogin = findViewById(R.id.btlogin);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);
        menuIcon = findViewById(R.id.nav_icon);


        navigationDrawer();

        tvForgotPWd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Query query = databaseReference.orderByChild("contact").equalTo(etcontact.getText().toString());
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        for (DataSnapshot data : dataSnapshot.getChildren()) {
                            Personal_Info pi = data.getValue(Personal_Info.class);
                            SharedPreferences.Editor prefsEditor = ActivityLogin.this.getSharedPreferences("eyetrack", MODE_PRIVATE).edit();
                            Gson gson = new Gson();
                            String json = gson.toJson(pi);
                            prefsEditor.putString("tempuser", json);
                            prefsEditor.commit();

                            Toast.makeText(ActivityLogin.this, "Login sucessfull", Toast.LENGTH_SHORT).show();
                            databaseReference.removeEventListener(this);
                            final int min = 1010;
                            final int max = 9999;
                            final int otp = new Random().nextInt((max - min) + 1) + min;

                            BackgroundMail.newBuilder(ActivityLogin.this)
                                    .withUsername("trafficsignal123@gmail.com")
                                    .withPassword("signal123")
                                    .withMailto(pi.getEmail())
                                    .withType(BackgroundMail.TYPE_PLAIN)
                                    .withSubject("Password forget reset link")
                                    .withBody("Your otp is " + otp)
                                    .withOnSuccessCallback(new BackgroundMail.OnSuccessCallback() {
                                        @Override
                                        public void onSuccess() {
                                            //do some magic
                                            Toast.makeText(ActivityLogin.this, "OTP sent on email ", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(ActivityLogin.this, ForgotPassword.class);
                                            intent.putExtra("otp", otp + "");
                                            Log.i("otp before", otp + "");
                                            startActivity(intent);
                                        }
                                    })
                                    .withOnFailCallback(new BackgroundMail.OnFailCallback() {
                                        @Override
                                        public void onFail() {
                                            //do some magic
                                        }
                                    })
                                    .send();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });
        databaseReference = FirebaseDatabase.getInstance().getReference(MyNode.VEHICLE_ADMIN);

        btlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Query query = databaseReference.orderByChild("contact").equalTo(etcontact.getText().toString());
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        for (DataSnapshot data : dataSnapshot.getChildren()) {
                            Personal_Info pi = data.getValue(Personal_Info.class);
                            if (pi.getPassword().equals(etpwd.getText().toString())) {
                                SharedPreferences.Editor prefsEditor = ActivityLogin.this.getSharedPreferences("eyetrack", MODE_PRIVATE).edit();
                                Gson gson = new Gson();
                                String json = gson.toJson(pi);
                                prefsEditor.putString("admin", json);
                                prefsEditor.commit();

                                Toast.makeText(ActivityLogin.this, "Login sucessfull", Toast.LENGTH_SHORT).show();
                                databaseReference.removeEventListener(this);
                                Intent intent = new Intent(ActivityLogin.this, ActivityDashboard.class);
                                startActivity(intent);
                            } else {
                                Personal_Info vi = new Personal_Info();
                                SharedPreferences.Editor prefsEditor = getSharedPreferences("eyetrack", MODE_PRIVATE).edit();
                                Gson gson = new Gson();
                                String json = gson.toJson(vi);
                                prefsEditor.putString("admin", json);
                                prefsEditor.commit();
                                databaseReference.removeEventListener(this);

                                Toast.makeText(ActivityLogin.this, "Invalid user", Toast.LENGTH_SHORT).show();

                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });

        btsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActivityLogin.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }

    private void navigationDrawer() {

        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_login);

        menuIcon.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (drawerLayout.isDrawerVisible(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START);

                } else drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.nav_login:
                Intent intent = new Intent(getApplicationContext(), ActivityLogin.class);
                startActivity(intent);
                break;
            case R.id.nav_signup:
                Intent intent1 = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent1);
                break;
            case R.id.nav_contactus:
                Intent intent2 = new Intent(getApplicationContext(), ContactUs.class);
                startActivity(intent2);
                break;

            case R.id.nav_faq:
                Intent intent3 = new Intent(getApplicationContext(), SmartXportGuide.class);
                startActivity(intent3);
                break;
        }

        return true;
    }
}