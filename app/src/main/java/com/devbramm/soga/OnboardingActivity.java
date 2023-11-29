package com.devbramm.soga;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.devbramm.soga.adapters.OnboardingAdapter;

public class OnboardingActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private OnboardingAdapter adapter;
    private LinearLayout layout_dots;
    private TextView[] tv_dots;
    private Button startButton;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding);

        viewPager = findViewById(R.id.forth_view_pager);
        layout_dots = findViewById(R.id.layout_dots);
        startButton = findViewById(R.id.btn_onboarding);

        adapter = new OnboardingAdapter(OnboardingActivity.this);
        viewPager.setAdapter(adapter);

        // Initialize SharedPreferences
        sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);

        addDots(0);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                addDots(position);
                updateButtonText(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Check if the current page is not the last page
                if (viewPager.getCurrentItem() < adapter.getCount() - 1) {
                    // Move to the next page
                    viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
                } else {
                    // Handle the case when the user has reached the last page
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("OnboardingCompleted", true);
                    editor.apply();
                    // You can start the main part of your app or perform any desired action
                    // For now, we'll just show a toast message
                    startActivity(new Intent(OnboardingActivity.this, OTPVerificationActivity.class));
                    finish();
                }
            }
        });
    }

    private void addDots(int position) {
        int dotSizeActive = 45; // Set the size of the active dot
        int dotSizeInactive = 35; // Set the size of the inactive dots

        tv_dots = new TextView[4];
        layout_dots.removeAllViews();

        for (int i = 0; i < tv_dots.length; i++) {
            tv_dots[i] = new TextView(OnboardingActivity.this);

            // Use a different character (e.g., • for active dot, ◦ for inactive dots)
            if (i == position) {
                tv_dots[i].setText("•"); // Active dot
                tv_dots[i].setTextSize(dotSizeActive);
                tv_dots[i].setTextColor(getResources().getColor(R.color.colorSecondary));
            } else {
                tv_dots[i].setText("◦"); // Inactive dots
                tv_dots[i].setTextSize(dotSizeInactive);
                tv_dots[i].setTextColor(getResources().getColor(R.color.black));
            }

            layout_dots.addView(tv_dots[i]);
        }
    }

    private void updateButtonText(int position) {
        if (position == adapter.getCount() - 1) {
            // If at the last page, change the button text to "Started"
            startButton.setText("Start Soga");
        } else {
            // Otherwise, set it to your initial text
            startButton.setText("Next"); // Replace with your initial text
        }
    }

}