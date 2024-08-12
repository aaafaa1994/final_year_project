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
import com.example.mctapp.Adapter.DrugAdapter;
import com.example.mctapp.MainActivity;
import com.example.mctapp.Models.Drug;
import com.example.mctapp.R;
import com.example.mctapp.Tools.SharedPreferenceManager;
import com.example.mctapp.Tools.URLs;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DrugActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    ImageView back;
    RecyclerView r1;
    FloatingActionButton add;

    DrugAdapter adapter;
    ArrayList<Drug> drugs= new ArrayList<>();
    SharedPreferenceManager sharedPreferenceManager;
    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;
    NavigationView nav;

    TextView navUsername,navUserEmail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drug);
        back=findViewById(R.id.back);
        r1=findViewById(R.id.r1);
        add=findViewById(R.id.add);
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
                Intent intent=new Intent(DrugActivity.this,MainMotherActivity.class);
                startActivity(intent);
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(DrugActivity.this,AddDrug.class);
                startActivity(intent);
            }
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        r1.setLayoutManager(linearLayoutManager);
        adapter = new DrugAdapter(this, drugs);
        r1.setAdapter(adapter);
        getData(String.valueOf(sharedPreferenceManager.get_user_id()));
    }
    public void getData(String rr){


        ProgressDialog loading = ProgressDialog.show(this, "Searching Data", "please wait....", false, false);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.ROOT_URL,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        Log.e("anyText", response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            loading.dismiss();
                            r1.setVisibility(View.VISIBLE);
                            drugs.clear();
                            String state = jsonObject.getString("state");
                            String message = jsonObject.getString("message");
                            if (jsonObject.getBoolean("state")) {

                                JSONArray data = jsonObject.getJSONArray("content");
                                for (int i = 0; i < data.length(); i++) {
                                    JSONObject data1 = data.getJSONObject(i);
                                    String name = data1.getString("name");
                                    String does = data1.getString("dose");
                                    String time = data1.getString("time");
                                    int id=data1.getInt("drug_id");
                                    int mother_id=data1.getInt("mother_id");
                                    int doctor_id=data1.getInt("doctor_id");
                                    drugs.add(new Drug(id, name, does,time,mother_id,doctor_id));
                                }
                                adapter.notifyDataSetChanged();
                            }
                            else{
                                loading.dismiss();
                                Toast.makeText(DrugActivity.this,"No Drugs",Toast.LENGTH_LONG).show();
                            }
                        } catch (Exception ex) {
                            ex.printStackTrace();
                            loading.dismiss();
                            Toast.makeText(DrugActivity.this,"Error",Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loading.dismiss();
                Toast.makeText(getApplicationContext(),
                        " Error !2" + error, Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("query", "get_mother_drug");
                params.put("mother_id",rr);
                return params;
            }
        };
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
            Intent intent = new Intent(DrugActivity.this, MotherProfile.class);
            startActivity(intent);
        } else if (id == R.id.nav_home) {
            Intent intent = new Intent(DrugActivity.this, MainMotherActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_Contact) {
            Intent intent = new Intent(DrugActivity.this, ContactActivity.class);
            startActivity(intent);
        }  else if (id == R.id.nav_logout) {
            Intent intent = new Intent(DrugActivity.this, MainActivity.class);
            startActivity(intent);
        }
        DrawerLayout drawer = findViewById(R.id.my_drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}