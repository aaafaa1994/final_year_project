package com.example.mctapp.Adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
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
import com.example.mctapp.Doctor.DoctorInstructionActivity;
import com.example.mctapp.Doctor.EditDoctorInstruction;
import com.example.mctapp.Models.Instruction;
import com.example.mctapp.Mother.DrugActivity;
import com.example.mctapp.Mother.EditDrugActivity;
import com.example.mctapp.R;
import com.example.mctapp.Tools.SharedPreferenceManager;
import com.example.mctapp.Tools.URLs;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class DoctorInstructionAdapter extends RecyclerView.Adapter<DoctorInstructionAdapter.ViewHolder> {
    private final Context mContext;
    private final ArrayList<Instruction> drugs;
    SharedPreferenceManager sharedPreferenceManager;


    public DoctorInstructionAdapter(Context mContext, ArrayList<Instruction> drugs) {
        this.mContext = mContext;
        this.drugs = drugs;
    }


    @Override
    public DoctorInstructionAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.doctor_instruction_card, null);
        return new DoctorInstructionAdapter.ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(DoctorInstructionAdapter.ViewHolder holder, int position) {
        final Instruction services1 = drugs.get(position);
        sharedPreferenceManager = new SharedPreferenceManager(mContext);

        holder.age.setText(services1.getAge());
        holder.description.setText(services1.getDescription());

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                delete_request(position);
            }
        });
        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sharedPreferenceManager.store_instruction(services1);
                Intent intent=new Intent(mContext, EditDoctorInstruction.class);
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return drugs.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView age,description;
        ImageView edit,delete;

        public ViewHolder( View itemView) {
            super(itemView);
            edit = itemView.findViewById(R.id.edit);
            delete = itemView.findViewById(R.id.delete);
            age = itemView.findViewById(R.id.age);
            description = itemView.findViewById(R.id.description);


        }
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
                                Intent intent=new Intent(mContext, DoctorInstructionActivity.class);

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
                params.put("query", "delete_instruction");
                params.put("id", String.valueOf(drugs.get(position).getId()));

                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(mContext);
        requestQueue.add(stringRequest);
    }
}
