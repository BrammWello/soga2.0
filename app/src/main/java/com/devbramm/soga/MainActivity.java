package com.devbramm.soga;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(!isInternetConnected(this)){
            ViewDialog alert = new ViewDialog();
            alert.showDialog(this, "We can't connect to the internet. Kindly check your connection");
        } else if (isFirebaseConnected()) {
            ViewDialog alert = new ViewDialog();
            alert.showDialog(this, "Oops 😟. An error occurred (21). Kindly retry later");
        } else if (!hasUserCompletedOnboarding(this)) {
            startActivity(new Intent(MainActivity.this,OnboardingActivity.class));
            finish();
        } else {
            startActivity(new Intent(MainActivity.this,ProfileSetupActivity.class));
            finish();
        }
    }

    public boolean isInternetConnected(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            return activeNetworkInfo != null && activeNetworkInfo.isConnected();
        }
        return false;
    }

    public boolean isFirebaseConnected() {
        try {
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference(".info/connected");
            databaseReference.keepSynced(true);
            return true;
        }
        catch (Exception e) {
            // Handle the exception, e.g., log an error message.
//            Toast.makeText(this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public boolean hasUserCompletedOnboarding(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean("OnboardingCompleted", false);
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