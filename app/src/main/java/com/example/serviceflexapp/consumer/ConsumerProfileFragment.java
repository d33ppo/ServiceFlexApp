package com.example.serviceflexapp.consumer;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.serviceflexapp.R;
import com.example.serviceflexapp.common.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class ConsumerProfileFragment extends Fragment {
    private TextView Name;
    private TextView Username;
    private DatabaseReference db;
    private FirebaseAuth Auth;
    private Button logout;


    public ConsumerProfileFragment() {
        super(R.layout.fragment_consumer_profile);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        NavController navController = Navigation.findNavController(view);
        view.findViewById(R.id.BTN_AccountInfo).setOnClickListener(v ->
                navController.navigate(R.id.action_consumerProfileFragment2_to_consumerProfileAccountInfo));
        view.findViewById(R.id.BTN_Ewallet).setOnClickListener(v ->
                navController.navigate(R.id.action_consumerProfileFragment2_to_consumerProfileEWallet));
        view.findViewById(R.id.BTN_UpdateWorkProfile).setOnClickListener(v ->
                navController.navigate(R.id.action_consumerProfileFragment2_to_consumerProfilePaymentHistory));
        db= FirebaseDatabase.getInstance().getReference("Consumer");
        Auth = FirebaseAuth.getInstance();
        Name = view.findViewById(R.id.TV_Name);
        Username = view.findViewById(R.id.TV_Username);
        logout = view.findViewById(R.id.BTN_Logout);
        setUserData("firstName","lastName",Name);
        setUserData("lastName",Username);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Auth.signOut();
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                Toast.makeText(getActivity(), "Logout Successful !", Toast.LENGTH_SHORT).show();

            }
        });
    }
    public void setUserData(String field1, String field2, TextView text){
        FirebaseUser user = Auth.getCurrentUser();
        String Uid = user.getUid();
        db.child(Uid).get()
                .addOnSuccessListener(dataSnapshot -> {
                    if (dataSnapshot.exists()) {
                        String data1 = dataSnapshot.child(field1).getValue(String.class);
                        String data2 =dataSnapshot.child(field2).getValue(String.class);
                        text.setText(data1+" "+data2);
                    } else {
                        Toast.makeText(getActivity(), "User role not found", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(getActivity(), "Failed to fetch role: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    public void setUserData(String field1, TextView text){
        FirebaseUser user = Auth.getCurrentUser();
        String Uid = user.getUid();
        db.child(Uid).get()
                .addOnSuccessListener(dataSnapshot -> {
                    if (dataSnapshot.exists()) {
                        String data1 = dataSnapshot.child(field1).getValue(String.class);
                        text.setText(data1);
                    } else {
                        Toast.makeText(getActivity(), "User role not found", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(getActivity(), "Failed to fetch role: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }
}