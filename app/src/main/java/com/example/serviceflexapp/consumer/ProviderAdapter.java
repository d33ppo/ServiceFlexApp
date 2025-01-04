package com.example.serviceflexapp.consumer;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.serviceflexapp.R;
import com.example.serviceflexapp.database.Provider;

import java.util.List;

public class ProviderAdapter extends RecyclerView.Adapter<ProviderAdapter.ViewHolder> {

    Activity activity;
    //private final NavController navController; // NavController for navigation
    private final List<Provider> providers;


    public ProviderAdapter(Activity activity, List<Provider> providers /*, NavController navController*/) {
        this.providers = providers;
        //this.navController = navController;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(activity);
        View view = inflater.inflate(R.layout.provider_mini_info, parent, false);
        return new ProviderAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Provider provider = providers.get(position);

            // Load image using Glide
            Glide.with(holder.itemView.getContext())
                    .load(provider.getImageURL())
                    .placeholder(R.drawable.brad_pitt) // Optional placeholder
                    .into(holder.imageView);


        holder.nameTextView.setText(provider.getFirstName());
        holder.priceRangeTextView.setText("Price Range: RM " + provider.getPriceRange());
        holder.yearsOfExperienceTextView.setText("Experience: " + provider.getYearsOfExperience() + " years");
        holder.starRatingTextView.setText("Rating: " + provider.getRating() + " stars");

        // Set up item click listener for navigation
        holder.itemView.setOnClickListener(view -> {
            Bundle bundle = new Bundle();
            bundle.putString("providerId", provider.getProviderId()); // Pass provider ID to fetch detailed data later
            bundle.putString("name", provider.getFirstName());
            bundle.putString("yearsOfExperience", provider.getYearsOfExperience());
            bundle.putString("rating", provider.getRating());
            bundle.putString("priceRange", provider.getPriceRange());
            bundle.putString("imageURL", provider.getImageURL());

            // Trigger navigation to ConsumerBookingsFragment1 with the bundle
            //navController.navigate(R.id.action_consumerHomeFragment2_to_consumerBookingsFragment, bundle);
        });
    }

    @Override
    public int getItemCount() {
        return providers.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView nameTextView, yearsOfExperienceTextView, starRatingTextView, priceRangeTextView;

        ConstraintLayout constraintLayout;
        LinearLayout layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            constraintLayout = itemView.findViewById(R.id.layout_providerInfo);
            layout = itemView.findViewById(R.id.LL_Provider1);
            imageView = itemView.findViewById(R.id.IV_Provider1);
            nameTextView = itemView.findViewById(R.id.TV_ProviderName1);
            priceRangeTextView = itemView.findViewById(R.id.TV_ProviderPriceRange1);
            yearsOfExperienceTextView = itemView.findViewById(R.id.TV_ProviderYearsOfExperience1);
            starRatingTextView = itemView.findViewById(R.id.TV_ProviderStar1);

        }
    }
}
