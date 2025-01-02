package com.example.serviceflexapp.common;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.serviceflexapp.R;
import com.example.serviceflexapp.consumer.ConsumerMainActivity;
import com.example.serviceflexapp.provider.ProviderMainActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterAsActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_as);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        // Consumer Button
        findViewById(R.id.BTN_Consumer).setOnClickListener(v -> {
            // Navigate to Consumer Registration Activity
            Intent intent = new Intent(RegisterAsActivity.this, ConsumerRegistrationActivity.class);
            startActivity(intent);

        });

        Button providerButton = findViewById(R.id.BTN_Provider);
        providerButton.setOnClickListener(v -> {
            // Navigate to ProviderRegistrationActivity
            Intent intent = new Intent(RegisterAsActivity.this, ProviderRegistrationActivity.class);
            startActivity(intent);
        });
    }

    // Navigate to Consumer Main Activity after Consumer Registration
    public void navigateToConsumerMain() {
        Intent intent = new Intent(RegisterAsActivity.this, ConsumerMainActivity.class);
        startActivity(intent);
        finish();
    }

    // Navigate to Provider Main Activity after Provider Registration
    public void navigateToProviderMain() {
        Intent intent = new Intent(RegisterAsActivity.this, ProviderMainActivity.class);
        startActivity(intent);
        finish();
    }
}