package com.example.mctapp.Mother;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.example.mctapp.LoginActivity;
import com.example.mctapp.MainActivity;
import com.example.mctapp.R;
import com.example.mctapp.Tools.URLs;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MotherRegister extends AppCompatActivity {
    ImageView back;
    EditText email,full_name,user_name,password,confirm,phone;
    Button register;
    TextView login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mother_register);
        back=findViewById(R.id.back);
        email=findViewById(R.id.email);
        full_name=findViewById(R.id.full_name);
        user_name=findViewById(R.id.user_name);
        password=findViewById(R.id.password);
        confirm=findViewById(R.id.confirm);
        phone=findViewById(R.id.phone);
        register=findViewById(R.id.register);
        login=findViewById(R.id.login);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MotherRegister.this, MainActivity.class);
                startActivity(intent);
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MotherRegister.this, LoginActivity.class);
                intent.putExtra("type","Mother");
                startActivity(intent);
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fullname=full_name.getText().toString();
                String username=user_name.getText().toString();
                String pass=password.getText().toString();
                String useremail=email.getText().toString();
                String confirmpass=confirm.getText().toString();
                String phonenumber=phone.getText().toString();



                //check if variables are empty
                if(username.isEmpty()|| useremail.isEmpty()||pass.isEmpty()||confirmpass.isEmpty()
                        || phonenumber.isEmpty() )
                {
                    Toast.makeText(MotherRegister.this,"There are empty fields",Toast.LENGTH_LONG).show();
                    return;
                }

                //check passwords
                if(!pass.equals(confirmpass))
                {
                    Toast.makeText(MotherRegister.this,"Passwords don't match",Toast.LENGTH_LONG).show();
                    return;
                }

                if(!isValidEmail(useremail)){
                    String errorString="Email is badly formatted \n should be like example@example.com";


                    email.setError(errorString);
                    return;
                }

                if(!isValidPassword(pass)){
                    String errorString="Password should be at least 8 characters";
                    errorString+="\n Contains upper and lower case letters and numbers";

                    password.setError(errorString);
                    return;
                }

                //free to signup

                register_mother();
            }
        });
    }
    public  boolean isValidEmail(final String email) {
        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+.[a-zA-Z]{2,6}$";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(email);

        return matcher.matches();

    }
    public  boolean isValidPassword(final String password) {
        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = "^(?=.*[A-Z])(?=.*[0-9])(?=.*[a-z]).{8,}$";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);

        return matcher.matches();

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
                                Intent intent=new Intent(MotherRegister.this, LoginActivity.class);
                                intent.putExtra("type","Mother");
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
                                    "Registration Error !1" + e, Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loading.dismiss();
                Toast.makeText(getApplicationContext(),
                        "Registration Error !2" + error, Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("query", "add_mother");
                params.put("full_name", full_name.getText().toString());
                params.put("user_name", user_name.getText().toString());
                params.put("email", email.getText().toString());
                params.put("password", password.getText().toString());
                params.put("mobile_number", phone.getText().toString());
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}