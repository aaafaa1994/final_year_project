package com.example.mctapp.Doctor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.mctapp.MainActivity;
import com.example.mctapp.R;
import com.example.mctapp.Tools.SharedPreferenceManager;
import com.google.android.material.navigation.NavigationView;

public class MainDoctorActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    CardView card2,card3,card4;
    SharedPreferenceManager sharedPreferenceManager;
    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;
    NavigationView nav;

    TextView navUsername,navUserEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_doctor);
        card2=findViewById(R.id.card2);
        card3=findViewById(R.id.card3);
        card4=findViewById(R.id.card4);
        sharedPreferenceManager=new SharedPreferenceManager(this);
        drawerLayout = findViewById(R.id.my_drawer_layout);
        nav = findViewById(R.id.nav);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        nav.bringToFront();
        nav.setNavigationItemSelectedListener(this);
        View headerView = nav.getHeaderView(0);
        navUsername = headerView.findViewById(R.id.nav_name);
        navUserEmail =headerView.findViewById(R.id.nav_email);
        navUsername.setText(sharedPreferenceManager.get_doctor_user_u_name());
        navUserEmail.setText(sharedPreferenceManager.get_doctor_user_email());
        card2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(MainDoctorActivity.this,DoctorDrugActivity.class);
                startActivity(intent);
            }
        });

        card3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(MainDoctorActivity.this,DoctorContactActivity.class);
                startActivity(intent);
            }
        });

        card4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(MainDoctorActivity.this, DoctorInstructionActivity.class);
                startActivity(intent);
            }
        });

    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_account) {
            Intent intent = new Intent(MainDoctorActivity.this, DoctorProfile.class);
            startActivity(intent);
        } else if (id == R.id.nav_home) {
            Intent intent = new Intent(MainDoctorActivity.this, MainDoctorActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_Contact) {
            Intent intent = new Intent(MainDoctorActivity.this, DoctorContactActivity.class);
            startActivity(intent);
        }  else if (id == R.id.nav_logout) {
            Intent intent = new Intent(MainDoctorActivity.this, MainActivity.class);
            startActivity(intent);
        }
        DrawerLayout drawer = findViewById(R.id.my_drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}