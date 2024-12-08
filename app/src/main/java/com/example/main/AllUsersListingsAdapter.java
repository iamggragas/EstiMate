package com.example.main;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.main.Utilities.Listings;

import java.util.ArrayList;
import java.util.List;

public class AllUsersListingsAdapter extends RecyclerView.Adapter<AllUsersViewHolder> {
    private Context context;
    private List<Listings> myListings;

    private String name, phone, email, password;

    public AllUsersListingsAdapter(Context context, List<Listings> myListings, String name, String phone, String email, String password) {
        this.context = context;
        this.myListings = myListings;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.password = password;
    }

    @NonNull
    @Override
    public AllUsersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.listings_recycler_item, parent, false);
        return new AllUsersViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AllUsersViewHolder holder, int position) {
        holder.houseName.setText(myListings.get(position).getHouseName());
        holder.houseAddress.setText(myListings.get(position).getHouseAddress());
        holder.price.setText("Php " + myListings.get(position).getPrice() + "0");

        holder.recCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AllUsersListingsDetail.class);
                intent.putExtra("houseName", myListings.get(position).getHouseName());
                intent.putExtra("houseAddress", myListings.get(position).getHouseAddress());
                intent.putExtra("price", Double.toString(myListings.get(position).getPrice()));
                intent.putExtra("area", Double.toString(myListings.get(position).getSize()));
                intent.putExtra("bedroom", Integer.toString(myListings.get(position).getBedrooms()));
                intent.putExtra("quality", Integer.toString(myListings.get(position).getQuality()));
                intent.putExtra("age", Double.toString(myListings.get(position).getAge()));

                intent.putExtra("name", myListings.get(holder.getAdapterPosition()).getOwnerListing());
                intent.putExtra("phone", myListings.get(holder.getAdapterPosition()).getOwnerPhoneNumber());
                intent.putExtra("email", myListings.get(holder.getAdapterPosition()).getOwnerEmail());
                intent.putExtra("password", password);

                intent.putExtra("key", myListings.get(holder.getAdapterPosition()).getKey());

                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return myListings.size();
    }

    public void searchListings(ArrayList<Listings> searchList) {
        myListings = searchList;
        notifyDataSetChanged();
    }
}

class AllUsersViewHolder extends RecyclerView.ViewHolder {

    public TextView houseName, houseAddress, price;
    public CardView recCard;

    public AllUsersViewHolder(@NonNull View itemView) {
        super(itemView);

        recCard = itemView.findViewById(R.id.recCard);
        houseName = itemView.findViewById(R.id.houseName);
        houseAddress = itemView.findViewById(R.id.houseAddress);
        price = itemView.findViewById(R.id.price);

    }
}