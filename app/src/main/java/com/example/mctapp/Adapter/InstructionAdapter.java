package com.example.mctapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.mctapp.Models.Drug;
import com.example.mctapp.Models.Instruction;
import com.example.mctapp.Mother.EditDrugActivity;
import com.example.mctapp.R;
import com.example.mctapp.Tools.SharedPreferenceManager;

import java.util.ArrayList;

public class InstructionAdapter extends RecyclerView.Adapter<InstructionAdapter.ViewHolder> {
    private final Context mContext;
    private final ArrayList<Instruction> drugs;
    SharedPreferenceManager sharedPreferenceManager;


    public InstructionAdapter(Context mContext, ArrayList<Instruction> drugs) {
        this.mContext = mContext;
        this.drugs = drugs;
    }


    @Override
    public InstructionAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.instruction_card, null);
        return new InstructionAdapter.ViewHolder(view);
    }


    @Override
    public void onBindViewHolder( InstructionAdapter.ViewHolder holder, int position) {
        final Instruction services1 = drugs.get(position);
        sharedPreferenceManager = new SharedPreferenceManager(mContext);

        holder.age.setText(services1.getAge());
        holder.description.setText(services1.getDescription());



    }

    @Override
    public int getItemCount() {
        return drugs.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView age,description;

        public ViewHolder( View itemView) {
            super(itemView);
            age = itemView.findViewById(R.id.age);
            description = itemView.findViewById(R.id.description);


        }
    }
}
