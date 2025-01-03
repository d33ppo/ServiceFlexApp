package com.example.serviceflexapp.provider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import com.example.serviceflexapp.R;
import com.example.serviceflexapp.auth.FirebaseUtil;
import com.example.serviceflexapp.consumer.ConsumerMainActivity;
import com.example.serviceflexapp.database.Provider;
import com.example.serviceflexapp.model.ProviderModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;

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
    private String firstName, lastName;


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        profileImage=view.findViewById(R.id.ProfilePicture);
        profileName=view.findViewById(R.id.TV_Name);
        username=view.findViewById(R.id.TV_Username);
        eWalletbalance=view.findViewById(R.id.TV_Ewallet);
        db= FirebaseDatabase.getInstance().getReference("Provider");
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
        getUserData("firstName", username);

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
}