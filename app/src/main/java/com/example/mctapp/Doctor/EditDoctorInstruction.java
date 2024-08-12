package com.example.mctapp.Doctor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mctapp.MainActivity;
import com.example.mctapp.R;
import com.example.mctapp.Tools.SharedPreferenceManager;
import com.example.mctapp.Tools.URLs;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class EditDoctorInstruction extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    ImageView back;
    EditText age,desc;
    Button reset,edit;
    SharedPreferenceManager sharedPreferenceManager;
    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;
    NavigationView nav;

    TextView navUsername,navUserEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_doctor_instruction);
        back=findViewById(R.id.back);
        age=findViewById(R.id.age);
        desc=findViewById(R.id.desc);
        reset=findViewById(R.id.reset);
        edit=findViewById(R.id.edit);
        sharedPreferenceManager=new SharedPreferenceManager(this);

        age.setText(sharedPreferenceManager.get_Instruction_age());
        desc.setText(sharedPreferenceManager.get_Instruction_desc());
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
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(EditDoctorInstruction.this, DoctorInstructionActivity.class);
                startActivity(intent);
            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                age.setText("");
                desc.setText("");
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String i_age=age.getText().toString();
                String i_desc=desc.getText().toString();
                if(i_desc.isEmpty() || i_age.isEmpty()){
                    Toast.makeText(EditDoctorInstruction.this, "Please Enter All Fields", Toast.LENGTH_SHORT).show();
                }else{
                    add_instruction();
                }
            }
        });
    }
    public void add_instruction() {


        ProgressDialog loading = ProgressDialog.show(this, "Sending Data", "please wait....", false, false);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.ROOT_URL,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        loading.dismiss();
                        Log.e("anyText", response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            String state = jsonObject.getString("state");
                            String message = jsonObject.getString("message");
                            if (jsonObject.getBoolean("state")) {
                                Toast.makeText(getApplicationContext(), message,
                                        Toast.LENGTH_LONG).show();
                                Intent intent=new Intent(EditDoctorInstruction.this, DoctorInstructionActivity.class);
                                startActivity(intent);

                            }
                            else {
                                loading.dismiss();
                                Toast.makeText(getApplicationContext(), "Connection Error",
                                        Toast.LENGTH_LONG).show();}



                        } catch (Exception e) {
                            loading.dismiss();
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(),
                                    " Error !1" + e, Toast.LENGTH_LONG).show();
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
                params.put("query", "edit_instruction");
                params.put("age", age.getText().toString());
                params.put("description", desc.getText().toString());
                params.put("id", String.valueOf(sharedPreferenceManager.get_Instruction_id()));
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
            Intent intent = new Intent(EditDoctorInstruction.this, DoctorProfile.class);
            startActivity(intent);
        } else if (id == R.id.nav_home) {
            Intent intent = new Intent(EditDoctorInstruction.this, MainDoctorActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_Contact) {
            Intent intent = new Intent(EditDoctorInstruction.this, DoctorContactActivity.class);
            startActivity(intent);
        }  else if (id == R.id.nav_logout) {
            Intent intent = new Intent(EditDoctorInstruction.this, MainActivity.class);
            startActivity(intent);
        }
        DrawerLayout drawer = findViewById(R.id.my_drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}