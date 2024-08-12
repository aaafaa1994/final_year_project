package com.example.mctapp.Mother;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mctapp.Adapter.ContactAdapter;
import com.example.mctapp.Adapter.DrugAdapter;
import com.example.mctapp.Admin.ManageDoctors;
import com.example.mctapp.MainActivity;
import com.example.mctapp.Models.Doctor;
import com.example.mctapp.Models.Drug;
import com.example.mctapp.R;
import com.example.mctapp.Tools.SharedPreferenceManager;
import com.example.mctapp.Tools.URLs;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ContactActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    ImageView back;
    RecyclerView r1;


    ContactAdapter adapter;
    ArrayList<Doctor> drugs= new ArrayList<>();
    SharedPreferenceManager sharedPreferenceManager;
    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;
    NavigationView nav;

    TextView navUsername,navUserEmail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        back=findViewById(R.id.back);
        r1=findViewById(R.id.r1);

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
        navUsername.setText(sharedPreferenceManager.get_user_f());
        navUserEmail.setText(sharedPreferenceManager.get_user_email());

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ContactActivity.this,MainMotherActivity.class);
                startActivity(intent);
            }
        });


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        r1.setLayoutManager(linearLayoutManager);
        adapter = new ContactAdapter(this, drugs);
        r1.setAdapter(adapter);
        getData();
    }
    private void getData() {
        StringRequest stringRequest = new StringRequest(com.android.volley.Request.Method.GET, URLs.GET_doctor,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        drugs.clear();

                        r1.setVisibility(View.VISIBLE);
                        try {

                            JSONArray data = new JSONArray(response);
                            for(int i=0;i<data.length();i++){
                                Doctor s = new Gson().fromJson(data.get(i).toString(), Doctor.class);
                                drugs.add(s);
                            }
                            adapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            Toast.makeText(ContactActivity.this, "error", Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(ContactActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

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
            Intent intent = new Intent(ContactActivity.this, MotherProfile.class);
            startActivity(intent);
        } else if (id == R.id.nav_home) {
            Intent intent = new Intent(ContactActivity.this, MainMotherActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_Contact) {
            Intent intent = new Intent(ContactActivity.this, ContactActivity.class);
            startActivity(intent);
        }  else if (id == R.id.nav_logout) {
            Intent intent = new Intent(ContactActivity.this, MainActivity.class);
            startActivity(intent);
        }
        DrawerLayout drawer = findViewById(R.id.my_drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}