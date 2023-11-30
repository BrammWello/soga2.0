package com.devbramm.soga;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.devbramm.soga.models.UserProfile;
import com.devbramm.soga.utils.MyUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class ProfileSetupActivity extends AppCompatActivity {

    //Firebase Auth
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    private TextInputLayout etProfileName, etProfileAbout, etProfileStatus;

    private MyUtils.ViewDialog dialogHelper;

    @Override
    protected void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null){
            startActivity(new Intent(this, OTPVerificationActivity.class));
            finish();
        } else if (!Objects.equals(currentUser.getDisplayName(), "")) {
            startActivity(new Intent(this, HomePageActivity.class));
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_setup);

        //firebase settings
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("users");

        //initialize the UI
        etProfileAbout = findViewById(R.id.et_profile_about);
        etProfileName = findViewById(R.id.et_profile_name);
        etProfileStatus = findViewById(R.id.et_profile_status);

        //dialog setup
        dialogHelper = new MyUtils.ViewDialog();

        findViewById(R.id.finish_setup_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etProfileName.getEditText().getText().toString().trim();
                String about = etProfileAbout.getEditText().getText().toString().trim();
                String status = etProfileStatus.getEditText().getText().toString().trim();
                if (Objects.equals(name,"")){
                    dialogHelper.showDialog(ProfileSetupActivity.this, "Sorry, Kindly check the name that you provided");
                } else {

                    Log.d("Setup Errors", "Attempting save to DB");
                    FirebaseUser firebaseUser = mAuth.getCurrentUser();
                    UserProfile newUserProfile = new UserProfile();
                    newUserProfile.setUid(firebaseUser.getUid());
                    newUserProfile.setuPhoneNumber(firebaseUser.getPhoneNumber());
                    newUserProfile.setuDisplayName(name);
                    newUserProfile.setuProfileAbout(about);
                    newUserProfile.setuProfileStatus(status);

                    dialogHelper.showDialog(ProfileSetupActivity.this, "Hold on tight");

                    mDatabase.child(newUserProfile.getUid()).setValue(newUserProfile).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                        .setDisplayName(name)
                                        .build();

                                firebaseUser.updateProfile(profileUpdates)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    startActivity(new Intent(ProfileSetupActivity.this, HomePageActivity.class));
                                                } else {
                                                    dialogHelper.showDialog(ProfileSetupActivity.this, "Sorry. " + task.getException().getMessage());
                                                }
                                            }
                                        });
                            } else {
                                dialogHelper.showDialog(ProfileSetupActivity.this, "Sorry. " + task.getException().getMessage());
                            }
                        }
                    });

                }

            }
        });
    }
}