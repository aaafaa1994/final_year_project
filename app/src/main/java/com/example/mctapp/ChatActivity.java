package com.example.mctapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mctapp.Adapter.ChatAdapter;
import com.example.mctapp.Models.Chat;
import com.example.mctapp.Tools.SharedPreferenceManager;
import com.example.mctapp.Tools.URLs;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ChatActivity extends AppCompatActivity {
    TextView user_name;
    ImageView back;
    RecyclerView messages;
    ChatAdapter chatAdapter;
    ArrayList<Chat> chats=new ArrayList<>();
    EditText message;
    ImageButton send;
    String mother_id, doctor_id,name,type;
    SharedPreferenceManager sharedPreferenceManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        sharedPreferenceManager=new SharedPreferenceManager(this);
        mother_id =getIntent().getStringExtra("mother_id");
        doctor_id =getIntent().getStringExtra("doctor_id");
        name=getIntent().getStringExtra("name");
        type=getIntent().getStringExtra("type");
        user_name=findViewById(R.id.user_name);
        messages=findViewById(R.id.messages);
        message=findViewById(R.id.message);
        send=findViewById(R.id.send);
        user_name.setText(name);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        messages.setLayoutManager(layoutManager);
        chatAdapter = new ChatAdapter(this,chats,type);
        messages.setAdapter(chatAdapter);
        if(type.matches("Mother")){
            getChildrenData();
        }
        else if(type.matches("Doctor")){
            getData();
        }

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String msg=message.getText().toString();
                if (!msg.isEmpty()) {
                    if(type.matches("Mother")){
                        getChildrenData();
                        addChildrenData();
                        getChildrenData();}



                    if(type.matches("Doctor")){
                        getData();
                        addData();
                        getData();
                    }

                }else {
                    Toast.makeText(ChatActivity.this, "Please Enter Text MSG", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void getData(){

        StringRequest request = new StringRequest(Request.Method.POST, URLs.ROOT_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                chats.clear();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getBoolean("state")) {
                        chats.clear();
                        JSONArray data = jsonObject.getJSONArray("content");
                        for (int i = 0; i < data.length(); i++) {
                            JSONObject data1 = data.getJSONObject(i);
                            String text_sender = data1.getString("text_sender");
                            String msg_r = data1.getString("msg_r");
                            int 	receiver = data1.getInt("receiver");
                            int id=data1.getInt("id");

                            chats.add(new Chat(id,text_sender,msg_r));
                        }
                        chatAdapter.notifyDataSetChanged();
                    }
                } catch (JSONException e) {

                    Toast.makeText(ChatActivity.this, "error", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getApplicationContext(), "Connection error", Toast.LENGTH_LONG).show();
            }

        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("query", "get_msg");
                params.put("mother_id", mother_id);
                params.put("doctor_id", doctor_id);
                return params;
            }
        };

        Volley.newRequestQueue(getApplicationContext()).add(request);
    }

    private void getChildrenData(){

        StringRequest request = new StringRequest(Request.Method.POST, URLs.ROOT_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                chats.clear();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getBoolean("state")) {
                        chats.clear();
                        JSONArray data = jsonObject.getJSONArray("content");
                        for (int i = 0; i < data.length(); i++) {
                            JSONObject data1 = data.getJSONObject(i);
                            String text_sender = data1.getString("text_sender");
                            int 	receiver = data1.getInt("receiver");
                            int id=data1.getInt("id");
                            String msg_r = data1.getString("msg_r");
                            chats.add(new Chat(id,text_sender,msg_r));
                        }
                        chatAdapter.notifyDataSetChanged();
                    }
                } catch (JSONException e) {

                    Toast.makeText(ChatActivity.this, "error", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getApplicationContext(), "Connection error", Toast.LENGTH_LONG).show();
            }

        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("query", "get_users_msg");
                params.put("mother_id", mother_id);
                params.put("doctor_id", doctor_id);
                return params;
            }
        };

        Volley.newRequestQueue(getApplicationContext()).add(request);
    }

    private void addData() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST,URLs.ROOT_URL,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {

                        if (response == null || response.length() <= 0) {

                        } else {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                String state = jsonObject.getString("state");
                                String message11 = jsonObject.getString("message");
                                if (jsonObject.getBoolean("state")) {
                                    chatAdapter.notifyDataSetChanged();
                                    message.setText(" ");
                                }
                                else {
                                    Toast.makeText(getApplicationContext(), "No Data",
                                            Toast.LENGTH_LONG).show();}
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Connection error", Toast.LENGTH_LONG).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<>();
                params.put("query", "add_msg_doctor_chat");
                params.put("mother_id", mother_id);
                params.put("doctor_id", doctor_id);
                params.put("text_sender_from", message.getText().toString());
                params.put("receiver_to", mother_id);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void addChildrenData() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST,URLs.ROOT_URL,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {

                        if (response == null || response.length() <= 0) {

                        } else {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                String state = jsonObject.getString("state");
                                String message11 = jsonObject.getString("message");
                                if (jsonObject.getBoolean("state")) {



                                    chatAdapter.notifyDataSetChanged();

                                    message.setText(" ");

                                }
                                else {
                                    Toast.makeText(getApplicationContext(), "No Data",
                                            Toast.LENGTH_LONG).show();}


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Connection error", Toast.LENGTH_LONG).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<>();
                params.put("query", "add_mother_msg");
                params.put("mother_id", mother_id);
                params.put("doctor_id", doctor_id);
                params.put("msg_r", message.getText().toString());
                params.put("receiver_to", mother_id);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}