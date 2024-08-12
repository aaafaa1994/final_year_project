package com.example.mctapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.mctapp.Admin.MainAdminActivity;
import com.example.mctapp.Doctor.MainDoctorActivity;
import com.example.mctapp.Models.Doctor;
import com.example.mctapp.Models.Mother;
import com.example.mctapp.Mother.MainMotherActivity;
import com.example.mctapp.Tools.SharedPreferenceManager;
import com.example.mctapp.Tools.URLs;
import com.example.mctapp.Tools.VolleySingleton;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    ImageView back;
    EditText email,password;
    TextView forget,register;
    Button login;
    String type;

    SharedPreferenceManager sharedPreferenceManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        back=findViewById(R.id.back);
        email =findViewById(R.id.user_name);
        password=findViewById(R.id.password);
        forget=findViewById(R.id.forget);
        register=findViewById(R.id.register);
        login=findViewById(R.id.login);
        type=getIntent().getStringExtra("type");
        sharedPreferenceManager=new SharedPreferenceManager(this);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(LoginActivity.this, ForgetPassword.class);
                intent.putExtra("type",type);
                startActivity(intent);
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s_username = email.getText().toString();
                String S_password = password.getText().toString();
                if (s_username.matches("") || S_password.matches("")) {
                    Toast.makeText(getApplicationContext(), "Fill enter all fields", Toast.LENGTH_SHORT).show();
                } else if(type.matches("Admin")) {
                   login_admin();
                }else if(type.matches("Mother")){
                    login_mother();
                }else if(type.matches("Doctor")){
                    login_doctor();
                }else{
                    Toast.makeText(LoginActivity.this, "Wrong Information", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    void login_mother(){
        ProgressDialog loading = ProgressDialog.show(this, "Sending Data", "please wait....", false, false);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.ROOT_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {

                            JSONObject obj = new JSONObject(response);
                            loading.dismiss();

                            if (obj.getBoolean("state")) {
                                JSONArray userArray = obj.getJSONArray("content");
                                Mother user=  new Gson().fromJson(userArray.get(0).toString(), Mother.class);
                                sharedPreferenceManager.store_user(user);
                                Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), MainMotherActivity.class);
                                startActivity(intent);
                            } else {
                                loading.dismiss();
                                Toast.makeText(LoginActivity.this, "Wrong Information", Toast.LENGTH_SHORT).show();

                            }
                        } catch (JSONException e) {
                            loading.dismiss();
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        loading.dismiss();
                        Toast.makeText(getApplicationContext(),"error", Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("query", "mother_login");
                params.put("email", email.getText().toString());
                params.put("password", password.getText().toString());
                return params;
            }
        };
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }
    void login_doctor(){
        ProgressDialog loading = ProgressDialog.show(this, "Sending Data", "please wait....", false, false);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.ROOT_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {

                            JSONObject obj = new JSONObject(response);
                            loading.dismiss();

                            if (obj.getBoolean("state")) {
                                JSONArray userArray = obj.getJSONArray("content");
                                Doctor user=  new Gson().fromJson(userArray.get(0).toString(), Doctor.class);
                                sharedPreferenceManager.store_doctor(user);
                                Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), MainDoctorActivity.class);
                                startActivity(intent);
                            } else {
                                loading.dismiss();
                                Toast.makeText(LoginActivity.this, "Wrong Information", Toast.LENGTH_SHORT).show();

                            }
                        } catch (JSONException e) {
                            loading.dismiss();
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        loading.dismiss();
                        Toast.makeText(getApplicationContext(),"error", Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("query", "doctor_login");
                params.put("email", email.getText().toString());
                params.put("password", password.getText().toString());
                return params;
            }
        };
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }
    void login_admin(){
        ProgressDialog loading = ProgressDialog.show(this, "Sending Data", "please wait....", false, false);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.ROOT_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {

                            JSONObject obj = new JSONObject(response);
                            loading.dismiss();

                            if (obj.getBoolean("state")) {
                                JSONArray userArray = obj.getJSONArray("content");
                                Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), MainAdminActivity.class);
                                startActivity(intent);
                            } else {
                                loading.dismiss();
                                Toast.makeText(LoginActivity.this, "Wrong Information", Toast.LENGTH_SHORT).show();

                            }
                        } catch (JSONException e) {
                            loading.dismiss();
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        loading.dismiss();
                        Toast.makeText(getApplicationContext(),"error", Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("query", "admin_login");
                params.put("email", email.getText().toString());
                params.put("password", password.getText().toString());
                return params;
            }
        };
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }
}