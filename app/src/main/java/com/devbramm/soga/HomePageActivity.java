package com.devbramm.soga;

import android.Manifest;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.devbramm.soga.adapters.ChatMessagesAdapter;
import com.devbramm.soga.adapters.ContactListAdapter;
import com.devbramm.soga.adapters.ImageStatusTopAdapter;
import com.devbramm.soga.models.ChatItemList;
import com.devbramm.soga.models.ContactItemList;
import com.devbramm.soga.utils.ContactHelperUtils;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.carousel.CarouselLayoutManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Objects;

public class HomePageActivity extends AppCompatActivity implements ContactListAdapter.OnContactSelectedListener {

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    RecyclerView carouselRecyclerView, chatsMessageRecyclerView, frequentlyContactedRecyclerview;

    private ConstraintLayout mBottomSheetLayout;
    private BottomSheetBehavior sheetBehavior;
    private ImageView header_Arrow_Image;
    private static final int REQUEST_READ_CONTACTS_PERMISSION = 123;


    @Override
    protected void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null){
            startActivity(new Intent(this, OTPVerificationActivity.class));
            finish();
        } else if (Objects.equals(currentUser.getDisplayName(), "")) {
            startActivity(new Intent(this, ProfileSetupActivity.class));
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        //new contact recycler views
        frequentlyContactedRecyclerview = findViewById(R.id.frequently_contacted_recycler_view);
        LinearLayoutManager contactsFrequentlyLayoutManager = new LinearLayoutManager(this);
        frequentlyContactedRecyclerview.setLayoutManager(contactsFrequentlyLayoutManager);

        //check permissions first
        // Check the SDK version and whether the permission is already granted or not.
        // Check if the READ_CONTACTS permission is granted
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted, request it
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_CONTACTS},
                    REQUEST_READ_CONTACTS_PERMISSION);
        } else {
            // Permission is already granted, proceed with loading contacts
            loadContacts();
        }

        try {
            // Beginning of bottom sheet configs
            mBottomSheetLayout = findViewById(R.id.bottom_sheet_new_contacts_layout);
            sheetBehavior = BottomSheetBehavior.from(mBottomSheetLayout);
            header_Arrow_Image = findViewById(R.id.new_message_contacts_btn);

            header_Arrow_Image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (sheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED) {
                        sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                    } else {
                        sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                    }
                }
            });

            sheetBehavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
                @Override
                public void onStateChanged(@NonNull View bottomSheet, int newState) {
                }

                @Override
                public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                    header_Arrow_Image.setRotation(slideOffset * 180);
                }
            });
        } catch (Exception e) {
            Toast.makeText(this, "" + e.getMessage(), Toast.LENGTH_LONG).show();
        }


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

    private void loadContacts() {
        //get the adapter for contacts section
        try {
            // Create a list of data items
            ArrayList<ContactItemList> contactsFrequentlyMessagesList = ContactHelperUtils.getContacts(this);
            ContactListAdapter contactListAdapter = new ContactListAdapter(this, contactsFrequentlyMessagesList);
            contactListAdapter.setOnContactSelectedListener(this);
            RecyclerView frequentlyContactedRecyclerview = findViewById(R.id.frequently_contacted_recycler_view);
            frequentlyContactedRecyclerview.setLayoutManager(new LinearLayoutManager(this));
            frequentlyContactedRecyclerview.setAdapter(contactListAdapter);
        } catch (Exception e) {
            Toast.makeText(this, "" + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onContactSelected() {
        // Close the Bottom Sheet when a contact is selected
        if (sheetBehavior.getState() != BottomSheetBehavior.STATE_COLLAPSED) {
            sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_READ_CONTACTS_PERMISSION) {
            // Check if the permission is granted
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, proceed with loading contacts
                loadContacts();
            } else {
                // Permission denied, show a message or handle accordingly
                Toast.makeText(this, "Permission denied. Cannot load contacts.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}