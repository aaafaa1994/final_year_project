package com.example.mctapp.Doctor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.AlarmClock;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mctapp.MainActivity;
import com.example.mctapp.Mother.ContactActivity;
import com.example.mctapp.Mother.DrugActivity;
import com.example.mctapp.Mother.EditDrugActivity;
import com.example.mctapp.Mother.MainMotherActivity;
import com.example.mctapp.Mother.MotherProfile;
import com.example.mctapp.R;
import com.example.mctapp.Tools.SharedPreferenceManager;
import com.example.mctapp.Tools.URLs;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class EditDrugDoctorActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    ImageView back;
    EditText name,does,time;

    Button reset,add;
    int hour,minute;
    SharedPreferenceManager sharedPreferenceManager;
    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;
    NavigationView nav;

    TextView navUsername,navUserEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_drug_doctor);
        back=findViewById(R.id.back);
        name=findViewById(R.id.name);
        does=findViewById(R.id.does);
        time=findViewById(R.id.time);
        reset=findViewById(R.id.reset);
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
        navUsername.setText(sharedPreferenceManager.get_doctor_user_u_name());
        navUserEmail.setText(sharedPreferenceManager.get_doctor_user_email());

        name.setText(sharedPreferenceManager.get_drug_name());
        does.setText(sharedPreferenceManager.get_drug_does());
        time.setText(sharedPreferenceManager.get_drug_time());

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(EditDrugDoctorActivity.this, DoctorDrugActivity.class);
                startActivity(intent);
            }
        });
        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(EditDrugDoctorActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int i, int i1) {
                        hour = i;
                        minute = i1;
                        Calendar c = Calendar.getInstance();
                        c.set(0, 0, 0, hour, minute);
                        android.text.format.DateFormat df = new android.text.format.DateFormat();
                        time.setText(df.format("hh:mm aa", c));
                    }
                }, 12, 0, false);
                timePickerDialog.updateTime(hour, minute);
                timePickerDialog.show();
            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name.setText("");
                does.setText("");
                time.setText("");
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String d_name=name.getText().toString();
                String d_does=does.getText().toString();
                String d_time=time.getText().toString();

                if(d_name.isEmpty() || d_does.isEmpty()  || d_time.isEmpty()){
                    Toast.makeText(EditDrugDoctorActivity.this, "Please Enter All Fields", Toast.LENGTH_SHORT).show();
                }else{

                    add_drug();
                }
            }
        });
    }
    public void add_drug() {


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
                                Intent intent=new Intent(EditDrugDoctorActivity.this, DoctorDrugActivity.class);
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
                params.put("query", "edit_mother_drug");
                params.put("name", name.getText().toString());
                params.put("dose", does.getText().toString());
                params.put("time", time.getText().toString());
                params.put("drug_id", String.valueOf(sharedPreferenceManager.get_drug_id()));
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
            Intent intent = new Intent(EditDrugDoctorActivity.this, DoctorProfile.class);
            startActivity(intent);
        } else if (id == R.id.nav_home) {
            Intent intent = new Intent(EditDrugDoctorActivity.this, MainDoctorActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_Contact) {
            Intent intent = new Intent(EditDrugDoctorActivity.this, DoctorContactActivity.class);
            startActivity(intent);
        }  else if (id == R.id.nav_logout) {
            Intent intent = new Intent(EditDrugDoctorActivity.this, MainActivity.class);
            startActivity(intent);
        }
        DrawerLayout drawer = findViewById(R.id.my_drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}