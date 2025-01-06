package com.example.serviceflexapp.provider;

import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.serviceflexapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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
        db= FirebaseDatabase.getInstance().getReference();
        getUserData("firstName", "lastName", username);
        getUserData("categories", work);
        return view;
    }
    private void getUserData(String field1, TextView text){
        FirebaseUser user = firebaseAuth.getCurrentUser();
        assert user != null;
        String Uid = user.getUid();
        db.child("Provider").child("Barber").child(Uid)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            // Provider found in this group
                            String data1 = dataSnapshot.child(field1).getValue(String.class);
                            // Do something with attribute1
                            text.setText(data1);
                        } else {
                            // Provider not found in this group
                            Log.d("ProviderInfo", "Provider not found in " + "Barber");
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        // Handle possible errors
                        Log.e("ProviderInfo", "Database error: " + databaseError.getMessage());
                    }
                });
        db.child("Provider").child("Electrician").child(Uid)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            // Provider found in this group
                            String data1 = dataSnapshot.child(field1).getValue(String.class);
                            // Do something with attribute1
                            text.setText(data1);
                        } else {
                            // Provider not found in this group
                            Log.d("ProviderInfo", "Provider not found in " + "Electrician");
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        // Handle possible errors
                        Log.e("ProviderInfo", "Database error: " + databaseError.getMessage());
                    }
                });
        db.child("Provider").child("Plumber").child(Uid)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            // Provider found in this group
                            String data1 = dataSnapshot.child(field1).getValue(String.class);
                            // Do something with attribute1
                            text.setText(data1);
                        } else {
                            // Provider not found in this group
                            Log.d("ProviderInfo", "Provider not found in " + "Plumber");
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        // Handle possible errors
                        Log.e("ProviderInfo", "Database error: " + databaseError.getMessage());
                    }
                });

        db.child("Provider").child("Maid").child(Uid)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            // Provider found in this group
                            String data1 = dataSnapshot.child(field1).getValue(String.class);
                            // Do something with attribute1
                            text.setText(data1);
                        } else {
                            // Provider not found in this group
                            Log.d("ProviderInfo", "Provider not found in " + "Maid");
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        // Handle possible errors
                        Log.e("ProviderInfo", "Database error: " + databaseError.getMessage());
                    }
                });

    }

    private void getUserData(String field1, String field2, TextView text) {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        assert user != null;
        String Uid = user.getUid();
        db.child("Provider").child("Barber").child(Uid)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            // Provider found in this group
                            String data1 = dataSnapshot.child(field1).getValue(String.class);
                            // Do something with attribute1
                            String data2 = dataSnapshot.child(field2).getValue(String.class);
                            text.setText(data1 + " " + data2);
                        } else {
                            // Provider not found in this group
                            Log.d("ProviderInfo", "Provider not found in " + "Barber");
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        // Handle possible errors
                        Log.e("ProviderInfo", "Database error: " + databaseError.getMessage());
                    }
                });

        db.child("Provider").child("Electrician").child(Uid)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            // Provider found in this group
                            String data1 = dataSnapshot.child(field1).getValue(String.class);
                            // Do something with attribute1
                            String data2 = dataSnapshot.child(field2).getValue(String.class);
                            text.setText(data1 + " " + data2);
                        } else {
                            // Provider not found in this group
                            Log.d("ProviderInfo", "Provider not found in " + "Electrician");
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        // Handle possible errors
                        Log.e("ProviderInfo", "Database error: " + databaseError.getMessage());
                    }
                });

        db.child("Provider").child("Plumber").child(Uid)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            // Provider found in this group
                            String data1 = dataSnapshot.child(field1).getValue(String.class);
                            // Do something with attribute1
                            String data2 = dataSnapshot.child(field2).getValue(String.class);
                            text.setText(data1 + " " + data2);
                        } else {
                            // Provider not found in this group
                            Log.d("ProviderInfo", "Provider not found in " + "Electrician");
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        // Handle possible errors
                        Log.e("ProviderInfo", "Database error: " + databaseError.getMessage());
                    }
                });

        db.child("Provider").child("Maid").child(Uid)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            // Provider found in this group
                            String data1 = dataSnapshot.child(field1).getValue(String.class);
                            // Do something with attribute1
                            String data2 = dataSnapshot.child(field2).getValue(String.class);
                            text.setText(data1 + " " + data2);
                        } else {
                            // Provider not found in this group
                            Log.d("ProviderInfo", "Provider not found in " + "Electrician");
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        // Handle possible errors
                        Log.e("ProviderInfo", "Database error: " + databaseError.getMessage());
                    }
                });
    }
}
