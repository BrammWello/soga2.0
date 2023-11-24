package com.devbramm.soga;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.devbramm.soga.adapters.ChatMessagesAdapter;
import com.devbramm.soga.adapters.ImageStatusTopAdapter;
import com.devbramm.soga.models.ChatItemList;
import com.google.android.material.carousel.CarouselLayoutManager;

import java.util.ArrayList;

public class HomePageActivity extends AppCompatActivity {

    RecyclerView carouselRecyclerView, chatsMessageRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        //chats section messages recycler configs
        chatsMessageRecyclerView = findViewById(R.id.chats_message_recyclerview);
        // Set up the RecyclerView with layout manager and adapter
        LinearLayoutManager chatsLayoutManager = new LinearLayoutManager(this);
        chatsMessageRecyclerView.setLayoutManager(chatsLayoutManager);

        carouselRecyclerView = findViewById(R.id.recycler);
        final CarouselLayoutManager layoutManager = new CarouselLayoutManager();
        carouselRecyclerView.setLayoutManager(layoutManager);
        carouselRecyclerView.setHorizontalScrollBarEnabled(false);
        ArrayList<String> arrayList = new ArrayList<>();

        //Add multiple images to arraylist.
        arrayList.add("https://images.unsplash.com/photo-1692528131755-d4e366b2adf0?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxlZGl0b3JpYWwtZmVlZHwzNXx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=500&q=60");
        arrayList.add("https://images.unsplash.com/photo-1692862582645-3b6fd47b7513?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxlZGl0b3JpYWwtZmVlZHw0MXx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=500&q=60");
        arrayList.add("https://images.unsplash.com/photo-1692584927805-d4096552a5ba?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxlZGl0b3JpYWwtZmVlZHw0Nnx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=500&q=60");
        arrayList.add("https://images.unsplash.com/photo-1692854236272-cc49076a2629?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxlZGl0b3JpYWwtZmVlZHw1MXx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=500&q=60");
        arrayList.add("https://images.unsplash.com/photo-1681207751526-a091f2c6a538?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxlZGl0b3JpYWwtZmVlZHwyODF8fHxlbnwwfHx8fHw%3D&auto=format&fit=crop&w=500&q=60");
        arrayList.add("https://images.unsplash.com/photo-1692610365998-c628604f5d9f?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxlZGl0b3JpYWwtZmVlZHwyODZ8fHxlbnwwfHx8fHw%3D&auto=format&fit=crop&w=500&q=60");

        ImageStatusTopAdapter adapter = new ImageStatusTopAdapter(HomePageActivity.this, arrayList);
        carouselRecyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new ImageStatusTopAdapter.OnItemClickListener() {
            @Override
            public void onClick(ImageView imageView, String path) {
                //Do something like opening the image in new activity or showing it in full screen or something else.
                Toast.makeText(HomePageActivity.this, "CLicked Toast", Toast.LENGTH_SHORT).show();
            }
        });

        // Create a list of data items
        ArrayList<ChatItemList> chatMessagesList = new ArrayList<>();
        chatMessagesList.add(new ChatItemList("Alice", "What time is the meeting?", "3:15 PM"));
        chatMessagesList.add(new ChatItemList("John Doe", "I'll be late for the party", "6:30 PM"));
        chatMessagesList.add(new ChatItemList("Emma", "Can you send me the report?", "11:05 AM"));
        chatMessagesList.add(new ChatItemList("Sarah", "Let's go for a walk", "5:50 PM"));
        chatMessagesList.add(new ChatItemList("Michael", "How was your day?", "7:20 AM"));
        chatMessagesList.add(new ChatItemList("Olivia", "Do you have any plans for the weekend?", "2:55 PM"));
        chatMessagesList.add(new ChatItemList("David", "See you later!", "8:10 PM"));
        chatMessagesList.add(new ChatItemList("Sophie", "I can't wait to see you!", "10:30 AM"));
        chatMessagesList.add(new ChatItemList("Max", "Let's grab lunch together", "1:20 PM"));
        chatMessagesList.add(new ChatItemList("Emily", "Did you watch the latest episode?", "4:45 PM"));
        chatMessagesList.add(new ChatItemList("Olivia", "Do you have any plans for the weekend?", "2:55 PM"));
        chatMessagesList.add(new ChatItemList("David", "See you later!", "8:10 PM"));
        chatMessagesList.add(new ChatItemList("Sophie", "I can't wait to see you!", "10:30 AM"));
        chatMessagesList.add(new ChatItemList("Max", "Let's grab lunch together", "1:20 PM"));
        chatMessagesList.add(new ChatItemList("Emily", "Did you watch the latest episode?", "4:45 PM"));

        //get the adapter for chats section
        ChatMessagesAdapter chatMessagesAdapter = new ChatMessagesAdapter(this, chatMessagesList);
        chatsMessageRecyclerView.setAdapter(chatMessagesAdapter);
    }
}