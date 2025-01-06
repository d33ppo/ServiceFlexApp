package com.example.serviceflexapp.consumer;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.serviceflexapp.R;
import com.example.serviceflexapp.database.Provider;

import java.util.List;

public class ProviderAdapter extends RecyclerView.Adapter<ProviderAdapter.ViewHolder> {

    Activity activity;
    private NavController navController = null; // NavController for navigation
    private final List<Provider> providers;

    private String selectedCategory;

    private String consumerId;

    public ProviderAdapter(Activity activity, List<Provider> providers /*, NavController navController*/, String selectedCategory, String consumerId) {
        this.providers = providers;
        //this.navController = navController;
        this.selectedCategory = selectedCategory;
        this.activity = activity;
        this.consumerId = consumerId;
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
                    .placeholder(R.drawable.profile_circle_icon) // Optional placeholder
                    .into(holder.imageView);


        holder.nameTextView.setText(provider.getFirstName());
        holder.priceRangeTextView.setText("Price Range: RM " + provider.getPriceRange());
        holder.ageTextView.setText("Age: " + provider.getAge() + " years old");
        holder.emailTextView.setText("Email: " + provider.getEmail());

        // Set up item click listener for navigation
        holder.itemView.setOnClickListener(view -> {
            Bundle bundle = new Bundle();
            bundle.putString("consumerId", consumerId);
            bundle.putString("providerId", provider.getProviderId());
            bundle.putString("name", provider.getFirstName());
            bundle.putInt("age", provider.getAge());
            bundle.putString("email", provider.getEmail());
            bundle.putString("priceRange", provider.getPriceRange());
            bundle.putString("imageURL", provider.getImageURL());
            bundle.putString("category", selectedCategory);
            Log.d("ProviderAdapter", "Navigating to ConsumerBookingsFragment with data: " + bundle);


            // Trigger navigation to ConsumerBookingsFragment
            navController.navigate(R.id.action_consumerHomeFragment2_to_consumerBookingsFragment, bundle);
        });
    }


    @Override
    public int getItemCount() {
        return providers.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView nameTextView, ageTextView, emailTextView, priceRangeTextView;

        ConstraintLayout constraintLayout;
        LinearLayout layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            constraintLayout = itemView.findViewById(R.id.layout_providerInfo);
            layout = itemView.findViewById(R.id.LL_Provider1);
            imageView = itemView.findViewById(R.id.IV_Provider1);
            nameTextView = itemView.findViewById(R.id.TV_ProviderName1);
            priceRangeTextView = itemView.findViewById(R.id.TV_ProviderPriceRange1);
            ageTextView = itemView.findViewById(R.id.TV_ProviderAge1);
            emailTextView = itemView.findViewById(R.id.TV_ProviderEmail1);

        }
    }

    public void setNavController(NavController navController){
        this.navController = navController;
    }
}
