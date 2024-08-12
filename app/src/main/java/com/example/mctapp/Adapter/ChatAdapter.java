package com.example.mctapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mctapp.R;
import com.example.mctapp.Models.Chat;
import com.example.mctapp.Tools.SharedPreferenceManager;

import java.util.ArrayList;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.CitesViewHolder> {
    private final Context mContext;
    private final ArrayList<Chat> chats;
    SharedPreferenceManager sharedPreferenceManager;
    String type;

    public ChatAdapter(Context mContext, ArrayList<Chat> chats, String type) {
        this.mContext = mContext;
        this.chats = chats;
        this.type = type;
    }


    @NonNull
    @Override
    public CitesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.card_list_msg, null);
        return new CitesViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull  CitesViewHolder holder, int position) {
        final Chat h = chats.get(position);
        sharedPreferenceManager=new SharedPreferenceManager(mContext);

        String s=h.getText_sender();
        String r=h.getMsg_r();

        if(type.matches("Mother")){

            if(h.getText_sender().matches("M")){
                holder.text_sent.setText(h.getMsg_r());
                holder.pp.setVisibility(View.INVISIBLE);
            }else  if(h.getMsg_r().matches("D")){
                holder.l1.setVisibility(View.INVISIBLE);
                holder.text_receive.setText(h.getText_sender());
            }else{
                holder.text_receive.setText(h.getText_sender());

            }


        }
        else if(type.matches("Doctor")){
            holder.text_sent.setText(h.getText_sender());
            if(h.getMsg_r().matches("D")){
                holder.pp.setVisibility(View.INVISIBLE);
            }else  if(h.getText_sender().matches("M")){
                holder.l1.setVisibility(View.INVISIBLE);
                holder.text_receive.setText(h.getMsg_r());
            }else{
                holder.text_receive.setText(h.getMsg_r());
            }
        }
    }

    @Override
    public int getItemCount() {
        return chats.size();
    }

    static class CitesViewHolder extends RecyclerView.ViewHolder{
        TextView text_receive,
                text_sent;

        LinearLayout pp;
        LinearLayout l1;

        public CitesViewHolder(@NonNull  View itemView) {
            super(itemView);
            text_receive = itemView.findViewById(R.id.rr);
            text_sent = itemView.findViewById(R.id.ss);
            pp = itemView.findViewById(R.id.pp);
            l1 = itemView.findViewById(R.id.l1);

        }
    }
}

