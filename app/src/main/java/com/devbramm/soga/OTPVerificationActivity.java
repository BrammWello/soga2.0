package com.devbramm.soga;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.devbramm.soga.utils.MyUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.hbb20.CountryCodePicker;

import java.util.concurrent.TimeUnit;

public class OTPVerificationActivity extends AppCompatActivity {

    private static final String TAG = "OTPVerificationActivity";

    //Firebase Auth
    private FirebaseAuth mAuth;

    //variables for the phone verification
    private String mVerificationId;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;

    private CountryCodePicker countryCodePicker;
    private EditText etPhone, etOTP;
    private TextView otpMessage, resendOTPBtn;
    private Button verifyBtn, getOTPBtn;
    ConstraintLayout progressBarLayout, progressBarLayoutContainer, otpTextViewLayout;

    private MyUtils.ViewDialog dialogHelper;

    @Override
    protected void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null){
            startActivity(new Intent(this, HomePageActivity.class));
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otpverification);

        //initialize firebase auth
        mAuth = FirebaseAuth.getInstance();

        etPhone = findViewById(R.id.editTextPhone);
        etOTP = findViewById(R.id.editTextTextOTP);
        progressBarLayout = findViewById(R.id.progressBarLayout);
        progressBarLayoutContainer = findViewById(R.id.constraintLayoutProgress);
        otpMessage = findViewById(R.id.resendOTPBtnText);
        resendOTPBtn = findViewById(R.id.resendOTPBtn);
        otpTextViewLayout = findViewById(R.id.constraintLayoutOTP);
        verifyBtn = findViewById(R.id.verifyBtn);
        getOTPBtn = findViewById(R.id.getOtpBtn);
        countryCodePicker = findViewById(R.id.country_code);

        //set up text input layout
        setUpPhoneInputEffects();
        setUpOTPInputEffects();

        //dialog setup
        dialogHelper = new MyUtils.ViewDialog();

        //initialize phone auth callbacks
        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                signInWithPhoneAuthCredential(phoneAuthCredential);
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                Log.e(TAG, "onVerificationFailed", e);

                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    //This is an invalid request
                    dialogHelper.showDialog(OTPVerificationActivity.this, "Sorry, Kindly check the phone number you provided"); // 'this' refers to the current activity
                } else if (e instanceof FirebaseTooManyRequestsException) {
                    // Too many requests
                    dialogHelper.showDialog(OTPVerificationActivity.this, "Sorry, kindly try again later. SMS quota reached."); // 'this' refers to the current activity
                }
            }

            @Override
            public void onCodeSent(@NonNull String idTokenVerify, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(idTokenVerify, forceResendingToken);
                mVerificationId = idTokenVerify;
                mResendToken = forceResendingToken;
                dialogHelper.showDialog(OTPVerificationActivity.this, "Kindly check your messages for OTP code."); // 'this' refers to the current activity
            }
        };

        //set
        getOTPBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phone = etPhone.getText().toString().trim();
                if (phone.length() < 9){
                    dialogHelper.showDialog(OTPVerificationActivity.this, "Sorry, Kindly check the phone number you provided"); // 'this' refers to the current activity
                } else if (phone.length() >= 9){
                    startPhoneNumberVerification(("+"+countryCodePicker.getSelectedCountryCode()+phone));
                }
            }
        });

        verifyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phone = etPhone.getText().toString().trim();
                String otpCode = etOTP.getText().toString().trim();
                if (otpCode.length() != 6){
                    dialogHelper.showDialog(OTPVerificationActivity.this, "Sorry, the OTP code you entered is wrong"); // 'this' refers to the current activity
                } else if (otpCode.length() == 6){
                    verifyPhoneNumberWithCode(mVerificationId,otpCode);
                }
            }
        });
    }

    //phone verification method
    private void startPhoneNumberVerification(String phoneNumber) {
        getOTPBtn.setEnabled(false);
        etPhone.setEnabled(false);
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(phoneNumber)
                        .setTimeout(60L, TimeUnit.SECONDS)
                        .setActivity(this)
                        .setCallbacks(mCallbacks)
                        .build();

        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    //compare the code method
    private void verifyPhoneNumberWithCode(String verificationId, String code) {
        // [START verify_with_code]
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        // [END verify_with_code]
        signInWithPhoneAuthCredential(credential);
    }

    // [START resend_verification]
    private void resendVerificationCode(String phoneNumber, PhoneAuthProvider.ForceResendingToken token) {
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(phoneNumber)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                        .setForceResendingToken(token)     // ForceResendingToken from callbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential phoneAuthCredential) {
        mAuth.signInWithCredential(phoneAuthCredential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithCredential:success");

                    FirebaseUser user = task.getResult().getUser();
                    // Update UI
                    updateUI(user);
                } else {
                    // Sign in failed, display a message and update the UI
                    Log.w(TAG, "signInWithCredential:failure", task.getException());
                    if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                        // The verification code entered was invalid
                    }
                }
            }
        });
    }

    private void setUpPhoneInputEffects() {
        etPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 9) {
                    // Make the view visible
                    fadeInView(otpTextViewLayout, 1000);
                    fadeInView(resendOTPBtn, 1000);
                    fadeInView(otpMessage, 1000);
                    // Get the current height of the constrainProgress container.
                    final int containerHeight = progressBarLayoutContainer.getMeasuredHeight();

                    // Calculate the new height of the constrainProgress container by adding 30 percent of the current height.
                    final int newHeight = (int) (containerHeight * 0.4);

                    // Create a ValueAnimator that animates between the current height and the new height.
                    ValueAnimator animator = ValueAnimator.ofInt(progressBarLayout.getMeasuredHeight(), newHeight);

                    // Set the duration of the animation.
                    animator.setDuration(500); // e.g. 500 milliseconds

                    // Add an update listener to the animator to update the height of the constrainProgress container.
                    animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            Integer animatedValue = (Integer) animation.getAnimatedValue();
                            progressBarLayout.getLayoutParams().height = animatedValue;
                            progressBarLayout.requestLayout();
                        }
                    });

                    // Start the animation.
                    animator.start();

                } else if (s.length() < 9) {
                    // Make the view invisible
                    fadeOutView(otpTextViewLayout, 1000);
                    fadeOutView(resendOTPBtn, 1000);
                    fadeOutView(otpMessage, 1000);

                    // Get the current height of the constrainProgress container.
                    final int containerHeight = progressBarLayoutContainer.getMeasuredHeight();

                    // Calculate the new height of the constrainProgress container by adding 30 percent of the current height.
                    final int newHeight = (int) (((containerHeight * 0.4) * s.length()) / 9);

                    // Create a ValueAnimator that animates between the current height and the new height.
                    ValueAnimator animator = ValueAnimator.ofInt(progressBarLayout.getMeasuredHeight(), newHeight);

                    // Set the duration of the animation.
                    animator.setDuration(500); // e.g. 500 milliseconds

                    // Add an update listener to the animator to update the height of the constrainProgress container.
                    animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            Integer animatedValue = (Integer) animation.getAnimatedValue();
                            progressBarLayout.getLayoutParams().height = animatedValue;
                            progressBarLayout.requestLayout();
                        }
                    });

                    // Start the animation.
                    animator.start();

                }

            }
        });
    }

    private void setUpOTPInputEffects() {
        etOTP.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 4) {
                    // fade in verify btn
                    fadeInView(verifyBtn,1000);
                    // Get the current height of the constrainProgress container.
                    final int containerHeight = progressBarLayoutContainer.getMeasuredHeight();

                    // Calculate the new height of the constrainProgress container by adding 30 percent of the current height.
                    final int newHeight = (int) (containerHeight * 0.8);

                    // Create a ValueAnimator that animates between the current height and the new height.
                    ValueAnimator animator = ValueAnimator.ofInt(progressBarLayout.getMeasuredHeight(), newHeight);

                    // Set the duration of the animation.
                    animator.setDuration(500); // e.g. 500 milliseconds

                    // Add an update listener to the animator to update the height of the constrainProgress container.
                    animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            Integer animatedValue = (Integer) animation.getAnimatedValue();
                            progressBarLayout.getLayoutParams().height = animatedValue;
                            progressBarLayout.requestLayout();
                        }
                    });

                    // Start the animation.
                    animator.start();

                } else if (s.length() < 4) {
                    // Get the current height of the constrainProgress container.
                    final int containerHeight = progressBarLayoutContainer.getMeasuredHeight();

                    // Calculate the new height of the constrainProgress container by adding 30 percent of the current height.
//                    int newHeight = (int) (progressBarLayout.getMeasuredHeight() + ((progressBarLayoutContainer.getMeasuredHeight() * 0.1) * s.length()/4));
                    int newHeight = (int) (progressBarLayoutContainer.getMeasuredHeight() * (0.4 + s.length() * 0.1));

                    // Create a ValueAnimator that animates between the current height and the new height.
                    ValueAnimator animator = ValueAnimator.ofInt(progressBarLayout.getMeasuredHeight(), newHeight);

                    // Set the duration of the animation.
                    animator.setDuration(500); // e.g. 500 milliseconds

                    // Add an update listener to the animator to update the height of the constrainProgress container.
                    animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            Integer animatedValue = (Integer) animation.getAnimatedValue();
                            progressBarLayout.getLayoutParams().height = animatedValue;
                            progressBarLayout.requestLayout();
                        }
                    });

                    // Start the animation.
                    animator.start();

                }

            }
        });
    }

    public static void fadeInView(View view, int duration) {
        view.setVisibility(View.VISIBLE);
        ObjectAnimator fadeAnimator = ObjectAnimator.ofFloat(view, "alpha", 0f, 1f);
        fadeAnimator.setDuration(duration);
        fadeAnimator.start();
    }

    public static void fadeOutView(View view, int duration) {
        view.setVisibility(View.INVISIBLE);
        ObjectAnimator fadeAnimator = ObjectAnimator.ofFloat(view, "alpha", 0f, 1f);
        fadeAnimator.setDuration(duration);
        fadeAnimator.start();
    }

    private void updateUI(FirebaseUser currentUser) {
        dialogHelper.showDialog(OTPVerificationActivity.this, "Nice. Now let's give your account a character"); // 'this' refers to the current activity
        startActivity(new Intent(OTPVerificationActivity.this,ProfileSetupActivity.class));
        finish();
    }
}