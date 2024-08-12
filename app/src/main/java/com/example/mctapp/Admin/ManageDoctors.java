package com.example.mctapp.Admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mctapp.Adapter.ManageDoctorAdapter;
import com.example.mctapp.Adapter.ManageMotherAdapter;
import com.example.mctapp.Models.Doctor;
import com.example.mctapp.Models.Mother;
import com.example.mctapp.R;
import com.example.mctapp.Tools.URLs;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class ManageDoctors extends AppCompatActivity {
    ImageView back;
    RecyclerView r1;
    ArrayList<Doctor> mothers=new ArrayList<>();

    ManageDoctorAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_doctors);
        back=findViewById(R.id.back);
        r1=findViewById(R.id.r1);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ManageDoctors.this, MainAdminActivity.class);
                startActivity(intent);
            }
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        r1.setLayoutManager(linearLayoutManager);
        adapter = new ManageDoctorAdapter(this, mothers);
        r1.setAdapter(adapter);
        getData();
    }
    private void getData() {
        StringRequest stringRequest = new StringRequest(com.android.volley.Request.Method.GET, URLs.GET_doctor,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        mothers.clear();

                        r1.setVisibility(View.VISIBLE);
                        try {

                            JSONArray data = new JSONArray(response);
                            for(int i=0;i<data.length();i++){
                                Doctor s = new Gson().fromJson(data.get(i).toString(), Doctor.class);
                                mothers.add(s);
                            }
                            adapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            Toast.makeText(ManageDoctors.this, "error", Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(ManageDoctors.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }
}