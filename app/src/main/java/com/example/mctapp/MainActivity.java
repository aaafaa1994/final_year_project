package com.example.mctapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.mctapp.Doctor.DoctorRegister;
import com.example.mctapp.Doctor.MainDoctorActivity;
import com.example.mctapp.Mother.MainMotherActivity;
import com.example.mctapp.Mother.MotherRegister;

public class MainActivity extends AppCompatActivity {
    Button register,login;
    Spinner spinner;

    String[] type = new String[] {"Select type of account","Mother","Doctor","Admin"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        register=findViewById(R.id.register);
        login=findViewById(R.id.login);
        spinner=findViewById(R.id.spinner);
        ArrayAdapter<String> arrayAdapter1 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, type);
        arrayAdapter1.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter1);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(spinner.getSelectedItem().toString().matches("Select type of account")){
                    Toast.makeText(MainActivity.this, "Please Select type of account", Toast.LENGTH_SHORT).show();
                }else{
                    Intent intent=new Intent(MainActivity.this, LoginActivity.class);
                    intent.putExtra("type",spinner.getSelectedItem().toString());
                    startActivity(intent);
                }

            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(spinner.getSelectedItem().toString().matches("Select type of account")){
                    Toast.makeText(MainActivity.this, "Please Select type of account", Toast.LENGTH_SHORT).show();
                }else if(spinner.getSelectedItem().toString().matches("Mother")){
                    Intent intent=new Intent(MainActivity.this, MotherRegister.class);
                    startActivity(intent);
                }else if(spinner.getSelectedItem().toString().matches("Doctor")){
                    Intent intent=new Intent(MainActivity.this, DoctorRegister.class);
                    startActivity(intent);
                }else if(spinner.getSelectedItem().toString().matches("Admin")){
                    Toast.makeText(MainActivity.this, "Please Select type of account another admin", Toast.LENGTH_SHORT).show();

                }
            }
        });

    }
}