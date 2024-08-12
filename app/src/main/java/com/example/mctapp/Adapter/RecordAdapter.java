package com.example.mctapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.mctapp.Models.Instruction;
import com.example.mctapp.Models.Record;
import com.example.mctapp.R;
import com.example.mctapp.Tools.SharedPreferenceManager;

import java.util.ArrayList;

public class RecordAdapter extends RecyclerView.Adapter<RecordAdapter.ViewHolder> {
    private final Context mContext;
    private final ArrayList<Record> drugs;
    SharedPreferenceManager sharedPreferenceManager;


    public RecordAdapter(Context mContext, ArrayList<Record> drugs) {
        this.mContext = mContext;
        this.drugs = drugs;
    }


    @Override
    public RecordAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.record_card, null);
        return new RecordAdapter.ViewHolder(view);
    }


    @Override
    public void onBindViewHolder( RecordAdapter.ViewHolder holder, int position) {
        final Record services1 = drugs.get(position);
        sharedPreferenceManager = new SharedPreferenceManager(mContext);

        holder.time.setText(services1.getTime());
        holder.name.setText(services1.getChild_name());
        holder.date.setText(services1.getDate());
        holder.temp.setText(String.valueOf(services1.getDegree()) + " C");



    }

    @Override
    public int getItemCount() {
        return drugs.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView time,date,temp,name;

        public ViewHolder( View itemView) {
            super(itemView);
            time = itemView.findViewById(R.id.time);
            date = itemView.findViewById(R.id.date);
            temp = itemView.findViewById(R.id.temp);
            name = itemView.findViewById(R.id.name);


        }
    }
}
