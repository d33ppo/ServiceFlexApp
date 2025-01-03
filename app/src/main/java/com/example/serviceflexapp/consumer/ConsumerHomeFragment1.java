package com.example.serviceflexapp.consumer;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import android.widget.LinearLayout;

import com.example.serviceflexapp.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ConsumerHomeFragment1#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ConsumerHomeFragment1 extends Fragment {

    LinearLayout LL_CategoryPlumber, LL_CategoryElectrician, LL_CategoryBarber, LL_CategoryMaid;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_consumer_home1, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button categoryButton = view.findViewById(R.id.IB_Category);
        categoryButton.setOnClickListener(v -> {
            NavController navController = Navigation.findNavController(view);
            navController.navigate(R.id.action_consumerHomeFragment1_to_consumerHomeFragment2);
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize views
        LL_CategoryPlumber = view.findViewById(R.id.LL_CategoryPlumber);
        LL_CategoryElectrician = view.findViewById(R.id.LL_CategoryElectrician);
        LL_CategoryBarber = view.findViewById(R.id.LL_CategoryBarber);
        LL_CategoryMaid = view.findViewById(R.id.LL_CategoryMaid);

        // Set up click listeners with category passing
        LL_CategoryPlumber.setOnClickListener(v -> navigateToCategory("plumber"));
        LL_CategoryElectrician.setOnClickListener(v -> navigateToCategory("electrician"));
        LL_CategoryBarber.setOnClickListener(v -> navigateToCategory("barber"));
        LL_CategoryMaid.setOnClickListener(v -> navigateToCategory("maid"));
    }

    private void navigateToCategory(String category) {
        // Create a new instance of Fragment 2
        ConsumerHomeFragment2 consumerHomeFragment2 = new ConsumerHomeFragment2();

        // Pass the category to Fragment 2 via Bundle
        Bundle bundleToFragment2 = new Bundle();
        bundleToFragment2.putString("category", category);
        consumerHomeFragment2.setArguments(bundleToFragment2);

        // Load Fragment2 into the same activity
        requireActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.FL_FragmentContainer, consumerHomeFragment2)
                .addToBackStack(null) // Enable back navigation
                .commit();
    }
}