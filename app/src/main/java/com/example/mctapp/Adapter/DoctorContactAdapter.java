package com.example.mctapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.mctapp.Admin.EditMotherAccount;
import com.example.mctapp.ChatActivity;
import com.example.mctapp.Models.Mother;
import com.example.mctapp.R;
import com.example.mctapp.Tools.SharedPreferenceManager;

import java.util.ArrayList;

public class DoctorContactAdapter extends RecyclerView.Adapter<DoctorContactAdapter.ViewHolder> {
    private final Context mContext;
    private final ArrayList<Mother> drugs;
    SharedPreferenceManager sharedPreferenceManager;


    public DoctorContactAdapter(Context mContext, ArrayList<Mother> drugs) {
        this.mContext = mContext;
        this.drugs = drugs;
    }


    @Override
    public DoctorContactAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.contact_card, null);
        return new DoctorContactAdapter.ViewHolder(view);
    }


    @Override
    public void onBindViewHolder( DoctorContactAdapter.ViewHolder holder, int position) {
        final Mother services1 = drugs.get(position);
        sharedPreferenceManager = new SharedPreferenceManager(mContext);

        holder.name.setText(services1.getFull_name());



        holder.call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + String.valueOf(services1.getMobile_number())));
                mContext.startActivity(intent);
            }
        });
        holder.chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sharedPreferenceManager.store_user(services1);
                Intent intent=new Intent(mContext, ChatActivity.class);
                intent.putExtra("type","Mother");
                intent.putExtra("mother_id", String.valueOf(services1.getMother_id()));
                intent.putExtra("name", services1.getFull_name());
                intent.putExtra("doctor_id", String.valueOf(sharedPreferenceManager.get_doctor_id()));
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return drugs.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        ImageView chat, call;


        public ViewHolder( View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            chat = itemView.findViewById(R.id.chat);
            call = itemView.findViewById(R.id.call);

        }
    }
}
