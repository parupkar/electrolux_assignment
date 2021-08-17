package com.sample.electroluxtest.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.sample.electroluxtest.DetailActivity;
import com.sample.electroluxtest.Model.GetFlickerResponse;
import com.sample.electroluxtest.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class GridImagesAdapter extends RecyclerView.Adapter<GridImagesAdapter.Viewholder> {

    private Context context;
    private ArrayList<GetFlickerResponse.Data.PhotoDataItem> getFlickerResponseArrayList;

    // Constructor
    public GridImagesAdapter(Context context, ArrayList<GetFlickerResponse.Data.PhotoDataItem> GetFlickerResponseArrayList) {
        this.context = context;
        this.getFlickerResponseArrayList = GetFlickerResponseArrayList;
    }

    @NonNull
    @Override
    public GridImagesAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // to inflate the layout for each item of recycler view.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_grid_image, parent, false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GridImagesAdapter.Viewholder holder, int position) {
        // to set data to textview and imageview of each card layout
        Float id = getFlickerResponseArrayList.get(position).getId();
        String title = getFlickerResponseArrayList.get(position).getTitle();
        String photo = getFlickerResponseArrayList.get(position).getPhotoUrl();

        Picasso.get().load(photo).into(holder.image);

        holder.lyt_parent.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                //Toast.makeText(context, title, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("title", title);
                intent.putExtra("photo", photo);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        // this method is used for showing number
        // of card items in recycler view.
        return getFlickerResponseArrayList.size();
    }

    // View holder class for initializing of
    // your views such as TextView and Imageview.
    public class Viewholder extends RecyclerView.ViewHolder {
        private ImageView image;
        private ConstraintLayout lyt_parent;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            lyt_parent = itemView.findViewById(R.id.lyt_parent);
        }
    }

}