package com.example.mctapp.Adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mctapp.Admin.EditDoctorAccount;
import com.example.mctapp.Admin.EditMotherAccount;
import com.example.mctapp.Admin.ManageDoctors;
import com.example.mctapp.Admin.ManageMother;
import com.example.mctapp.Models.Doctor;
import com.example.mctapp.Models.Mother;
import com.example.mctapp.R;
import com.example.mctapp.Tools.SharedPreferenceManager;
import com.example.mctapp.Tools.URLs;

import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ManageDoctorAdapter extends RecyclerView.Adapter<ManageDoctorAdapter.ViewHolder> {
    private final Context mContext;
    private final ArrayList<Doctor> drugs;
    SharedPreferenceManager sharedPreferenceManager;


    public ManageDoctorAdapter(Context mContext, ArrayList<Doctor> drugs) {
        this.mContext = mContext;
        this.drugs = drugs;
    }


    @Override
    public ManageDoctorAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.mother_account_card, null);
        return new ManageDoctorAdapter.ViewHolder(view);
    }


    @Override
    public void onBindViewHolder( ManageDoctorAdapter.ViewHolder holder, int position) {
        final Doctor services1 = drugs.get(position);
        sharedPreferenceManager = new SharedPreferenceManager(mContext);

        holder.textView4.setText(services1.getFull_name());
        holder.type.setText("Doctor");



        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                delete_request(position);
            }
        });
        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sharedPreferenceManager.store_doctor(services1);
                Intent intent=new Intent(mContext, EditDoctorAccount.class);
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return drugs.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView4,type;
        ImageView edit,delete;


        public ViewHolder( View itemView) {
            super(itemView);
            textView4 = itemView.findViewById(R.id.name);
            type = itemView.findViewById(R.id.type);
            edit = itemView.findViewById(R.id.edit);
            delete = itemView.findViewById(R.id.delete);

        }
    }
    private Bitmap decodeBase64AndSetImage(String completeImageData) {

        // Incase you're storing into aws or other places where we have extension stored in the starting.
        String imageDataBytes = completeImageData.substring(completeImageData.indexOf(",")+1);

        InputStream stream = new ByteArrayInputStream(Base64.decode(imageDataBytes.getBytes(), Base64.DEFAULT));

        Bitmap bitmap = BitmapFactory.decodeStream(stream);

        return  bitmap;

    }
    public void delete_request(int position) {


        ProgressDialog loading = ProgressDialog.show(mContext, "Delete Data", "please wait....", false, false);

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

                                Toast.makeText(mContext, "Delete Success", Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(mContext, ManageDoctors.class);

                                mContext.startActivity(intent);
                            }
                            else {
                                loading.dismiss();
                                Toast.makeText(mContext, "Connection Error",
                                        Toast.LENGTH_LONG).show();}



                        } catch (Exception e) {
                            loading.dismiss();
                            e.printStackTrace();
                            Toast.makeText(mContext,
                                    " Error !1" + e, Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loading.dismiss();
                Toast.makeText(mContext,
                        " Error !2" + error, Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                SimpleDateFormat timeStampFormat = new SimpleDateFormat("yyyy/MM/dd");
                Date myDate = new Date();
                String filename = timeStampFormat.format(myDate);
                params.put("query", "delete_doctor");
                params.put("doctor_id", String.valueOf(drugs.get(position).getDoctor_id()));

                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(mContext);
        requestQueue.add(stringRequest);
    }
}