package com.example.serviceflexapp.consumer;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.serviceflexapp.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ConsumerBookingsFragment1#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ConsumerBookingsFragment1 extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ConsumerBookingsFragment1() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ConsumerBookingsFragment1.
     */
    // TODO: Rename and change types and number of parameters
    public static ConsumerBookingsFragment1 newInstance(String param1, String param2) {
        ConsumerBookingsFragment1 fragment = new ConsumerBookingsFragment1();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_consumer_bookings1, container, false);

        // Initialize UI components
        TextView providerNameTextView = rootView.findViewById(R.id.TV_Name);
        TextView providerExperienceTextView = rootView.findViewById(R.id.TV_YearsOfExperience);
        TextView providerQualificationsTextView = rootView.findViewById(R.id.TV_Education);
        TextView providerRatingTextView = rootView.findViewById(R.id.TV_ProviderRating);
        ImageView providerImageView = rootView.findViewById(R.id.imageView);

        // Retrieve data from the fragment's arguments
        Bundle args = getArguments();
        if (args != null) {
            String providerName = args.getString("name");
            String providerYearsOfExperience = args.getString("yearsOfExperience");
            String providerQualifications = args.getString("qualifications");
            String providerRating = args.getString("rating");
            String providerImageUrl = args.getString("imageUrl");

            // Set the data in the UI components
            providerNameTextView.setText(providerName);
            providerExperienceTextView.setText(providerYearsOfExperience);
            providerQualificationsTextView.setText(providerQualifications);
            providerRatingTextView.setText(providerRating);

            // Load the provider image using Glide
            Glide.with(requireContext())
                    .load(providerImageUrl)
                    .into(providerImageView);
        }

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button bookNowButton = view.findViewById(R.id.button2);
        bookNowButton.setOnClickListener(v -> {
            NavController navController = Navigation.findNavController(view);
            navController.navigate(R.id.action_consumerBookingsFragment1_to_consumerBookingsFragment2);
        });
    }
}