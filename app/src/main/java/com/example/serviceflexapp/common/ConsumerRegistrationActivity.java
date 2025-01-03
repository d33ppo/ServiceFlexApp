package com.example.serviceflexapp.common;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.serviceflexapp.R;
import com.example.serviceflexapp.consumer.ConsumerMainActivity;

public class ConsumerRegistrationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_consumer_registration);

        findViewById(R.id.Button_Next).setOnClickListener(v -> {
            // Navigate to HomeActivity with Consumer HomeFragment
            Intent intent = new Intent(ConsumerRegistrationActivity.this, ConsumerMainActivity.class);
            intent.putExtra("userRole", "Consumer");
            startActivity(intent);
            finish();
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}