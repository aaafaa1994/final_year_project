package com.example.mctapp.Admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.mctapp.MainActivity;
import com.example.mctapp.R;

public class MainAdminActivity extends AppCompatActivity {
    CardView card1,card2,card4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_admin);
        card1=findViewById(R.id.card1);
        card2=findViewById(R.id.card2);
        card4=findViewById(R.id.card4);


        card1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainAdminActivity.this, ManageMother.class);
                startActivity(intent);
            }
        });
         card2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainAdminActivity.this, ManageDoctors.class);
                startActivity(intent);
            }
        });
        card4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(MainAdminActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            });


    }
}