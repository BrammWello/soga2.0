package com.devbramm.soga.adapters;

import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.devbramm.soga.R;
import com.devbramm.soga.models.ChatMessage;

import java.util.List;

public class ChatItemAdapter extends RecyclerView.Adapter<ChatItemAdapter.ViewHolder> {

    private List<ChatMessage> messages;

    public ChatItemAdapter(List<ChatMessage> messages) {
        this.messages = messages;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Implement this method to inflate your item view layout
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_item_sender_item_layout, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Implement this method to bind data to your ViewHolder
        ChatMessage message = messages.get(position);

        // Bind data to your ViewHolder views
        holder.tvChatMessageContent.setText(message.getText());
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        // Implement this class to hold your item views
        private TextView tvChatMessageContent;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // Initialize your item views here
            tvChatMessageContent = itemView.findViewById(R.id.tv_message_item_content);
        }
    }

    // Constructor and other methods

    public void addMessage(ChatMessage message) {
        if (messages != null) {
            messages.add(message);
            notifyItemInserted(messages.size() - 1);
        }
    }
}
