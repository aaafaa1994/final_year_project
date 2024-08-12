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

import com.example.mctapp.Models.Child;
import com.example.mctapp.Mother.EditChild;
import com.example.mctapp.Mother.TemperatureActivity;
import com.example.mctapp.R;
import com.example.mctapp.Tools.SharedPreferenceManager;

import java.util.ArrayList;

public class ChooseChildAdapter extends RecyclerView.Adapter<ChooseChildAdapter.ViewHolder> {
    private final Context mContext;
    private final ArrayList<Child> drugs;
    SharedPreferenceManager sharedPreferenceManager;


    public ChooseChildAdapter(Context mContext, ArrayList<Child> drugs) {
        this.mContext = mContext;
        this.drugs = drugs;
    }


    @Override
    public ChooseChildAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.child_card, null);
        return new ChooseChildAdapter.ViewHolder(view);
    }


    @Override
    public void onBindViewHolder( ChooseChildAdapter.ViewHolder holder, int position) {
        final Child services1 = drugs.get(position);
        sharedPreferenceManager = new SharedPreferenceManager(mContext);

        holder.textView4.setText(services1.getName());
        holder.age.setText(String.valueOf(services1.getAge()));
        holder.textView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(mContext, TemperatureActivity.class);
                intent.putExtra("name",services1.getName());
                mContext.startActivity(intent);
            }
        });



        holder.delete.setVisibility(View.INVISIBLE);
        holder.edit.setVisibility(View.INVISIBLE);

    }

    @Override
    public int getItemCount() {
        return drugs.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView4,age;

        LinearLayout click;
        ImageView edit,delete;


        public ViewHolder( View itemView) {
            super(itemView);
            textView4 = itemView.findViewById(R.id.name);
            click = itemView.findViewById(R.id.click);
            age = itemView.findViewById(R.id.age);
            edit = itemView.findViewById(R.id.edit);
            delete = itemView.findViewById(R.id.delete);

        }
    }
}
