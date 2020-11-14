package com.bancomext.fiducia.views;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.bancomext.fiducia.bancomextfiducia.R;
import com.bancomext.fiducia.tools.Constants;
import com.google.firebase.analytics.FirebaseAnalytics;

public class OnboardingActivity extends AppCompatActivity implements View.OnClickListener{

    private Button siguiente;
    private FirebaseAnalytics mFirebaseAnalytics;
    private boolean flagOnboarding = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding);

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        mFirebaseAnalytics.logEvent(Constants.introduccion, null);

        setView();
    }

    private void setView() {
        siguiente = findViewById(R.id.siguiente);
        siguiente.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == siguiente.getId()){
            flagOnboarding = true;
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
            prefs.edit().putBoolean(Constants.KEY_CHECKED, flagOnboarding).commit();
            Intent intent = new Intent(OnboardingActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onBackPressed() {

    }
}
