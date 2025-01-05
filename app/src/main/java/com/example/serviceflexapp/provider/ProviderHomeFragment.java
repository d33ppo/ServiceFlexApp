package com.example.serviceflexapp.provider;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.serviceflexapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ProviderHomeFragment extends Fragment {
    private TextView username;
    private TextView balance;
    private TextView work;
    private TextView rating;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference db;

    public ProviderHomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_provider_home, container, false);
        username=view.findViewById(R.id.TV_NameWalletBalance);
        balance=view.findViewById(R.id.TV_WalletBalance);
        work=view.findViewById(R.id.TV_WorkWalletBalance);
        rating=view.findViewById(R.id.TV_Rating);
        firebaseAuth=FirebaseAuth.getInstance();
        db= FirebaseDatabase.getInstance().getReference("Provider");
        getUserData("firstName", "lastName", username);
        return view;
    }
    private void getUserData(String field, String field2, TextView text){
        FirebaseUser user = firebaseAuth.getCurrentUser();
        assert user != null;
        db.child(user.getUid()).get()
                .addOnSuccessListener(dataSnapshot -> {
                    if (dataSnapshot.exists()) {
                        String data = dataSnapshot.child(field).getValue(String.class);
                        String data1 =dataSnapshot.child(field2).getValue(String.class);
                        text.setText(data+" "+data1);
                    } else {
                        Toast.makeText(getActivity(), "User role not found", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(getActivity(), "Failed to fetch role: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }
    private void getUserData(String field, TextView text){
        FirebaseUser user = firebaseAuth.getCurrentUser();
        assert user != null;
        db.child(user.getUid()).get()
                .addOnSuccessListener(dataSnapshot -> {
                    if (dataSnapshot.exists()) {
                        String data = dataSnapshot.child(field).getValue(String.class);
                        text.setText(data);
                    } else {
                        Toast.makeText(getActivity(), "User role not found", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(getActivity(), "Failed to fetch role: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }
}
