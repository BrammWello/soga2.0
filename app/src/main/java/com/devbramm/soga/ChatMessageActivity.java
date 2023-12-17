package com.devbramm.soga;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.devbramm.soga.adapters.ChatItemAdapter;
import com.devbramm.soga.adapters.ChatMessagesAdapter;
import com.devbramm.soga.models.ChatMessage;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ChatMessageActivity extends AppCompatActivity {

    private DatabaseReference mDatabase, mSendDatabase;
    private FirebaseAuth mAuth;

    private EditText edtChatMessageContent;
    private ImageView btnSendNewMessage;
    private RecyclerView chatAreaRecyclerView;
    private boolean chatExists = false;
    private String chatUID = "";
    private String senderUID = "";
    String contactPhone = null;
    private static final String DATE_FORMAT_PATTERN = "yyyyMMddHHmmssSSS";
    private static final int RANDOM_PART_LENGTH = 12;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_message);

        btnSendNewMessage = findViewById((R.id.btn_send_new_message));
        edtChatMessageContent = findViewById(R.id.edt_chat_message_content);
        chatAreaRecyclerView = findViewById(R.id.chat_area_recycler_view);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        chatAreaRecyclerView.setLayoutManager(layoutManager);

        //check if the conversation exists already
        mAuth = FirebaseAuth.getInstance();
        senderUID = mAuth.getCurrentUser().getUid();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("users").child(mAuth.getCurrentUser().getUid()).child("chats");
        mSendDatabase = FirebaseDatabase.getInstance().getReference().child("chats");

        try {
            contactPhone = getIntent().getStringExtra("CONTACT_PHONE");
            // Your logic to handle the received contactName
        } catch (NullPointerException e) {
            // Handle the case where the intent is null or the extra is not present
            e.printStackTrace(); // Log the exception if needed
            finish(); // Finish the activity if the extra is not present
        }

        // TODO this should be made as a function such that if sending a new message and there is no chatid, it can be rerun to get the ID
        mDatabase.orderByChild("phoneNumber").equalTo(contactPhone).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Chat already exists
                    chatExists = true;

                    for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                        chatUID = childSnapshot.getKey().toString();
                    }

                    //now run the get messages function

                } else {
                    // Chat doesn't exist, create a new chat
                    String newChatId = generateRandomSequentialId(); // Implement your own method
                    DatabaseReference newChatRef = mDatabase.child(newChatId);
                    newChatRef.child("phoneNumber").setValue(contactPhone);
                    newChatRef.child("dateStarted").setValue(ServerValue.TIMESTAMP);
                    chatUID = newChatRef.toString();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle errors
            }
        });

        List<ChatMessage> messagesList = new ArrayList<>();
        ChatItemAdapter adapter = new ChatItemAdapter(messagesList);
        chatAreaRecyclerView.setAdapter(adapter);

        btnSendNewMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String messageText = edtChatMessageContent.getText().toString().trim();

                if (!messageText.isEmpty()) {
                    // Get the current timestamp
                    long currentTimestamp = System.currentTimeMillis();
                    Date currentDate = new Date(currentTimestamp);

                    // Create a new message
                    ChatMessage message = new ChatMessage(messageText, senderUID, currentDate.toString(), "sent"); // Replace "user_id" with the actual user ID
                    playSuccessSound();

                    // Add the message to the RecyclerView
                    adapter.addMessage(message);

                    // Scroll to the last item with smooth scrolling
                    chatAreaRecyclerView.smoothScrollToPosition(adapter.getItemCount() - 1);

                    // Clear the input field
                    edtChatMessageContent.getText().clear();

                    //attempt to send message now
                    //chat message id
                    // TODO Find a way to generate the message id as one can lack internet or use persistence to late get the key
                    String messageKey = mSendDatabase.child(chatUID).push().getKey();

                    DatabaseReference messageReference = mSendDatabase.child(chatUID).child(messageKey);
                    messageReference.setValue(message);

                }
            }
        });

    }

    // Method to play a success sound
    private void playSuccessSound() {
        // Use MediaPlayer or any other sound library to play the sound
        MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.funny_pop);
        mediaPlayer.start();
    }

    public static String generateRandomSequentialId() {
        // Get current timestamp
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT_PATTERN, Locale.US);
        String timestamp = dateFormat.format(new Date());

        // Generate a random part
        String randomPart = generateRandomPart();

        // Combine timestamp and random part
        return timestamp + randomPart;
    }

    private static String generateRandomPart() {
        // Define characters for the random part
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

        // Generate a random part of the specified length
        StringBuilder randomPart = new StringBuilder();
        SecureRandom random = new SecureRandom();
        for (int i = 0; i < RANDOM_PART_LENGTH; i++) {
            int index = random.nextInt(characters.length());
            randomPart.append(characters.charAt(index));
        }

        return randomPart.toString();
    }
}