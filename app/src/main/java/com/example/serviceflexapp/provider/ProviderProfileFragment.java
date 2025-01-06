package com.example.serviceflexapp.provider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import com.example.serviceflexapp.R;
import com.example.serviceflexapp.common.LoginActivity;
import com.example.serviceflexapp.consumer.ConsumerMainActivity;
import com.example.serviceflexapp.database.Provider;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class ProviderProfileFragment extends Fragment {

    public ProviderProfileFragment() {
        super(R.layout.fragment_provider_profile);
    }
    private ImageView profileImage;
    private TextView profileName;
    private TextView username;
    private TextView eWalletbalance;

    private DatabaseReference db;
    private FirebaseAuth firebaseAuth;
    private Button logout;
    private String providerType;


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        profileImage=view.findViewById(R.id.ProfilePicture);
        profileName=view.findViewById(R.id.TV_Name);
        username=view.findViewById(R.id.TV_Username);
        eWalletbalance=view.findViewById(R.id.TV_Ewallet);
        logout=view.findViewById(R.id.btnLogout);
        db= FirebaseDatabase.getInstance().getReference();
        firebaseAuth=FirebaseAuth.getInstance();

        NavController navController = Navigation.findNavController(view);
        view.findViewById(R.id.BTN_AccountInfo).setOnClickListener(v ->
                navController.navigate(R.id.action_providerProfileFragment_to_providerProfileAccountInfo));
        view.findViewById(R.id.BTN_Ewallet).setOnClickListener(v ->
                navController.navigate(R.id.action_providerProfileFragment_to_providerProfileEwallet));
        view.findViewById(R.id.BTN_Transfer).setOnClickListener(v ->
                navController.navigate(R.id.action_providerProfileFragment_to_providerProfileTransferToBankAccount));
        view.findViewById(R.id.BTN_UpdateWorkProfile).setOnClickListener(v ->
                navController.navigate(R.id.action_providerProfileFragment_to_providerProfileUpdateWorkProfile));
        getUserData("firstName", "lastName", profileName);
        getUserData("lastName", username);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                firebaseAuth.signOut();
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                Toast.makeText(getActivity(), "Logout Successful !", Toast.LENGTH_SHORT).show();

            }
        });
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
    private void getUserData(String field1, String field2, TextView text){
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
                            String data2 =dataSnapshot.child(field2).getValue(String.class);
                            text.setText(data1+" "+data2);
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
                            String data2 =dataSnapshot.child(field2).getValue(String.class);
                            text.setText(data1+" "+data2);
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
                            String data2 =dataSnapshot.child(field2).getValue(String.class);
                            text.setText(data1+" "+data2);
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
                            String data2 =dataSnapshot.child(field2).getValue(String.class);
                            text.setText(data1+" "+data2);
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

        /*db.child(providerType).get()
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
                });*/
    }

    /*public interface ProviderTypeCallback {
        void onCallback(String providerType);
    }*/

    /*private void findProviderTypeByUserId(String userId, ProviderTypeCallback callback) {
        db.child("Provider").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean found = false;

                for (DataSnapshot providerTypeSnapshot : dataSnapshot.getChildren()) {
                    for (DataSnapshot providerSnapshot : providerTypeSnapshot.getChildren()) {
                        Provider provider = providerSnapshot.getValue(Provider.class);
                        if (provider != null && provider.getProviderId().equals(userId)) {
                            String providerType = providerTypeSnapshot.getKey(); // Get the provider type (barber, electrician, maid)
                            callback.onCallback(providerType); // Return the provider type via callback
                            found = true;
                            break; // Exit the loop if found
                        }
                    }
                    if (found) {
                        break; // Exit the outer loop if found
                    }
                }

                if (!found) {
                    callback.onCallback(null); // Return null if not found
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle possible errors.
                Log.e("FirebaseError", databaseError.getMessage());
                callback.onCallback(null); // Return null on error
            }
        });
    }*/
    /*private void checkProviderInGroup(String group, String providerId) {
        db.child("Provider").child(group).child(providerId)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            // Provider found in this group
                            String attribute1 = dataSnapshot.child("attribute1").getValue(String.class);
                            // Do something with attribute1
                            Log.d("ProviderInfo", "Attribute 1 in " + group + ": " + attribute1);
                        } else {
                            // Provider not found in this group
                            Log.d("ProviderInfo", "Provider not found in " + group);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        // Handle possible errors
                        Log.e("ProviderInfo", "Database error: " + databaseError.getMessage());
                    }
                });
    }*/
}