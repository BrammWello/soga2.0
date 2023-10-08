package com.devbramm.soga;

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
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.devbramm.soga.utils.MyUtils;

public class OTPVerificationActivity extends AppCompatActivity {

    private EditText etPhone, etOTP;
    private TextView otpMessage, resendOTPBtn;
    private Button verifyBtn, getOTPBtn;
    ConstraintLayout progressBarLayout, progressBarLayoutContainer, otpTextViewLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otpverification);

        etPhone = findViewById(R.id.editTextPhone);
        etOTP = findViewById(R.id.editTextTextOTP);
        progressBarLayout = findViewById(R.id.progressBarLayout);
        progressBarLayoutContainer = findViewById(R.id.constraintLayoutProgress);
        otpMessage = findViewById(R.id.resendOTPBtnText);
        resendOTPBtn = findViewById(R.id.resendOTPBtn);
        otpTextViewLayout = findViewById(R.id.constraintLayoutOTP);
        verifyBtn = findViewById(R.id.verifyBtn);
        getOTPBtn = findViewById(R.id.getOtpBtn);

        //set up text input layout
        setUpPhoneInputEffects();
        setUpOTPInputEffects();

        //set
        getOTPBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phone = etPhone.getText().toString().trim();
                if (phone.length() < 9){
                    MyUtils.ViewDialog dialogHelper = new MyUtils.ViewDialog();
                    dialogHelper.showDialog(OTPVerificationActivity.this, "Sorry, Kindly check the phone number you provided"); // 'this' refers to the current activity
                } else if (phone.length() >= 9){
                    MyUtils.ViewDialog dialogHelper = new MyUtils.ViewDialog();
                    dialogHelper.showDialog(OTPVerificationActivity.this, "Kindly wait for the OTP message"); // 'this' refers to the current activity
                }
            }
        });

        verifyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String otpCode = etOTP.getText().toString().trim();
                if (otpCode.length() < 4){
                    MyUtils.ViewDialog dialogHelper = new MyUtils.ViewDialog();
                    dialogHelper.showDialog(OTPVerificationActivity.this, "Sorry, the OTP code you entered is wrong"); // 'this' refers to the current activity
                } else if (otpCode.length() == 4){
                    MyUtils.ViewDialog dialogHelper = new MyUtils.ViewDialog();
                    dialogHelper.showDialog(OTPVerificationActivity.this, "Nice. Now let's give your account a character"); // 'this' refers to the current activity
                    startActivity(new Intent(OTPVerificationActivity.this,ProfileSetupActivity.class));
                    finish();
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

    public class ViewDialog {
        public void showDialog(Activity activity, String msg){
            final Dialog dialog = new Dialog(activity);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.util_dialog_layout);

            TextView text = (TextView) dialog.findViewById(R.id.dialog_message);
            text.setText(msg);

            dialog.show();

        }
    }
}