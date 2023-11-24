package com.devbramm.soga.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.devbramm.soga.R;
import com.devbramm.soga.models.ChatItemList;

import java.util.ArrayList;

public class ChatMessagesAdapter extends RecyclerView.Adapter<ChatMessagesAdapter.ViewHolder>{

    Context context;
    ArrayList<ChatItemList> chatItemLists;

    public ChatMessagesAdapter(Context context, ArrayList<ChatItemList> chatItemLists) {
        this.context = context;
        this.chatItemLists = chatItemLists;
    }

    @NonNull
    @Override
    public ChatMessagesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.chat_message_item_layout, parent, false);
        return new ChatMessagesAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatMessagesAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return chatItemLists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
