package com.example.serviceflexapp.provider;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import com.example.serviceflexapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ProviderProfileAccountInfo extends Fragment {

    public ProviderProfileAccountInfo() {
        super(R.layout.fragment_provider_profile_account_info);
    }
    private EditText firstname;
    private EditText lastname;
    private EditText phonenumber;
    private EditText email;
    private EditText password;
    private EditText address;
    private Button update;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference db;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        NavController navController = Navigation.findNavController(view);

        view.findViewById(R.id.IB_Previous).setOnClickListener(v ->
                navController.navigate(R.id.action_providerProfileAccountInfo_to_providerProfileFragment));
        firstname=view.findViewById(R.id.ETV_FirstName);
        lastname=view.findViewById(R.id.ETV_LastName);
        phonenumber=view.findViewById(R.id.ETV_PhoneNumber);
        email=view.findViewById(R.id.ETV_Email);
        password=view.findViewById(R.id.ETV_Password);
        address=view.findViewById(R.id.ETV_Address);
        update=view.findViewById(R.id.Button_Update);
        firebaseAuth=FirebaseAuth.getInstance();
        db= FirebaseDatabase.getInstance().getReference("Provider");
        getUserData("firstName", firstname);
        getUserData("lastName", lastname);
        getUserData("phoneNumber", phonenumber);
        getUserData("email", email);
        getUserData("address",address);
    }
    void updateButton(){
        String newFirstName = firstname.getText().toString();
        String newLastName = lastname.getText().toString();
        String newPhoneNumber = phonenumber.getText().toString();
        String newEmail = email.getText().toString();
        String newAddress = address.getText().toString();
    }

    private void getUserData(String field, EditText text){
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