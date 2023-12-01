package com.devbramm.soga;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.devbramm.soga.adapters.ChatItemAdapter;
import com.devbramm.soga.adapters.ChatMessagesAdapter;
import com.devbramm.soga.models.ChatMessage;

import java.util.ArrayList;
import java.util.List;

public class ChatMessageActivity extends AppCompatActivity {

    private EditText edtChatMessageContent;
    private ImageView btnSendNewMessage;
    private RecyclerView chatAreaRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_message);

        btnSendNewMessage = findViewById((R.id.btn_send_new_message));
        edtChatMessageContent = findViewById(R.id.edt_chat_message_content);
        chatAreaRecyclerView = findViewById(R.id.chat_area_recycler_view);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        chatAreaRecyclerView.setLayoutManager(layoutManager);

        List<ChatMessage> messagesList = new ArrayList<>();
        ChatItemAdapter adapter = new ChatItemAdapter(messagesList);
        chatAreaRecyclerView.setAdapter(adapter);

        btnSendNewMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String messageText = edtChatMessageContent.getText().toString().trim();

                if (!messageText.isEmpty()) {
                    // Create a new message
                    ChatMessage message = new ChatMessage(messageText, "user_id"); // Replace "user_id" with the actual user ID

                    // Add the message to the RecyclerView
                    adapter.addMessage(message);

                    // Scroll to the last item with smooth scrolling
                    chatAreaRecyclerView.smoothScrollToPosition(adapter.getItemCount() - 1);

                    // Clear the input field
                    edtChatMessageContent.getText().clear();
                }
            }
        });

    }
}