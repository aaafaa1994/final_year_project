package com.example.mctapp.Doctor;

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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mctapp.Admin.EditDoctorAccount;
import com.example.mctapp.Admin.ManageDoctors;
import com.example.mctapp.Models.Doctor;
import com.example.mctapp.R;
import com.example.mctapp.Tools.SharedPreferenceManager;
import com.example.mctapp.Tools.URLs;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class DoctorProfile extends AppCompatActivity {
    ImageView back;
    EditText email,full_name,user_name,password,phone;
    Button edit;
    SharedPreferenceManager sharedPreferenceManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_profile);
        back=findViewById(R.id.back);
        email=findViewById(R.id.email);
        full_name=findViewById(R.id.full_name);
        user_name=findViewById(R.id.user_name);
        password=findViewById(R.id.password);
        phone=findViewById(R.id.phone);
        edit=findViewById(R.id.edit);

        sharedPreferenceManager=new SharedPreferenceManager(this);

        full_name.setText(sharedPreferenceManager.get_doctor_user_u_name());
        user_name.setText(String.valueOf(sharedPreferenceManager.get_doctor_job_id()));
        email.setText(sharedPreferenceManager.get_doctor_user_email());
        password.setText(sharedPreferenceManager.get_doctor_user_password());
        phone.setText(String.valueOf(sharedPreferenceManager.get_doctor_mobile_number()));
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(DoctorProfile.this, MainDoctorActivity.class);
                startActivity(intent);
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fullname=full_name.getText().toString();
                String username=user_name.getText().toString();
                String pass=password.getText().toString();
                String useremail=email.getText().toString();
                String phonenumber=phone.getText().toString();

                if(username.isEmpty()|| useremail.isEmpty()||pass.isEmpty()||fullname.isEmpty()
                        || phonenumber.isEmpty() )
                {
                    Toast.makeText(DoctorProfile.this,"There are empty fields",Toast.LENGTH_LONG).show();

                }else {
                    register_mother();
                }

            }
        });
    }
    public void register_mother() {


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

                                Doctor user=  new Doctor(sharedPreferenceManager.get_doctor_id(),email.getText().toString(),Integer.parseInt(user_name.getText().toString()),password.getText().toString(),
                                        full_name.getText().toString(),Integer.parseInt(phone.getText().toString()));
                                sharedPreferenceManager.store_doctor(user);
                                Intent intent=new Intent(DoctorProfile.this, MainDoctorActivity.class);

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
                params.put("query", "edit_doctor");
                params.put("full_name", full_name.getText().toString());
                params.put("job_id", user_name.getText().toString());
                params.put("email", email.getText().toString());
                params.put("password", password.getText().toString());
                params.put("mobile_number", phone.getText().toString());
                params.put("doctor_id",String.valueOf(sharedPreferenceManager.get_doctor_id()));
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}