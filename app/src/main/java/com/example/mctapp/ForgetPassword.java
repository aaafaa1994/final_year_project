package com.example.mctapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mctapp.Models.Doctor;
import com.example.mctapp.Models.Mother;
import com.example.mctapp.Mother.MainMotherActivity;
import com.example.mctapp.Mother.MotherProfile;
import com.example.mctapp.Tools.SharedPreferenceManager;
import com.example.mctapp.Tools.URLs;
import com.example.mctapp.Tools.VolleySingleton;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ForgetPassword extends AppCompatActivity {
    ImageView back;
    Button check,reset;
    EditText user_name,password;
    SharedPreferenceManager sharedPreferenceManager;
    String type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        type=getIntent().getStringExtra("type");
        back=findViewById(R.id.back);
        check=findViewById(R.id.check);
        reset=findViewById(R.id.reset);
        user_name=findViewById(R.id.user_name);
        password=findViewById(R.id.password);

        sharedPreferenceManager=new SharedPreferenceManager(this);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ForgetPassword.this, MainActivity.class);
                startActivity(intent);
            }
        });


        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s_username = user_name.getText().toString();

                if (s_username.matches("") ) {
                    Toast.makeText(getApplicationContext(), "Please Enter Email", Toast.LENGTH_SHORT).show();
                }else if(type.matches("Mother")){
                    login_mother();
                }else if(type.matches("Doctor")){

                }else{
                    Toast.makeText(ForgetPassword.this, "Wrong Information", Toast.LENGTH_SHORT).show();
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
                                if(userArray != null){
                                    Mother user=  new Gson().fromJson(userArray.get(0).toString(), Mother.class);
                                    sharedPreferenceManager.store_user(user);
                                    password.setVisibility(View.VISIBLE);
                                    reset.setVisibility(View.VISIBLE);
                                    reset.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {

                                            String p=password.getText().toString();
                                            if(p.isEmpty()){
                                                Toast.makeText(ForgetPassword.this, "Please Enter New Password", Toast.LENGTH_SHORT).show();
                                            }else
                                                sendData(p,user.getMother_id());
                                                                                    }
                                    });
                                }else {
                                    Toast.makeText(ForgetPassword.this, "Email not correct", Toast.LENGTH_SHORT).show();

                                }
                            } else {
                                loading.dismiss();
                                Toast.makeText(ForgetPassword.this, "Email not correct", Toast.LENGTH_SHORT).show();

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
                params.put("query", "mother_email");
                params.put("email", user_name.getText().toString());

                return params;
            }
        };
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }
    public void sendData(String e,int id) {


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
                                Intent intent=new Intent(ForgetPassword.this, MainActivity.class);

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
                params.put("query", "edit_Mpassword");

                params.put("password", e);

                params.put("mother_id",String.valueOf(id));
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
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
                                if(userArray != null){
                                    Doctor user=  new Gson().fromJson(userArray.get(0).toString(), Doctor.class);
                                    sharedPreferenceManager.store_doctor(user);
                                    password.setVisibility(View.VISIBLE);
                                    reset.setVisibility(View.VISIBLE);
                                    reset.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {

                                            String p=password.getText().toString();
                                            if(p.isEmpty()){
                                                Toast.makeText(ForgetPassword.this, "Please Enter New Password", Toast.LENGTH_SHORT).show();
                                            }else if(p.length() == 10){
                                                sendData(p,user.getDoctor_id());
                                            }else {
                                                Toast.makeText(ForgetPassword.this, "Password must be 10 characters", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                }else {
                                    Toast.makeText(ForgetPassword.this, "Email not correct", Toast.LENGTH_SHORT).show();

                                }
                            } else {
                                loading.dismiss();
                                Toast.makeText(ForgetPassword.this, "Email not correct", Toast.LENGTH_SHORT).show();

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
                params.put("query", "doctor_email");
                params.put("email", user_name.getText().toString());

                return params;
            }
        };
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }
    public void sendDataDoctor(String e,int id) {


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
                                Intent intent=new Intent(ForgetPassword.this, MainActivity.class);

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
                params.put("query", "edit_Dpassword");

                params.put("password", e);

                params.put("doctor_id",String.valueOf(id));
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}