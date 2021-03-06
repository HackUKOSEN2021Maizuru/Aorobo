package com.example.aorobo.ui.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aorobo.R;

import java.util.List;

public class HomeStudyIconAdapter extends RecyclerView.Adapter<HomeStudyIconAdapter.ViewHolder>{

    private final List<String> iv;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    static class ViewHolder extends RecyclerView.ViewHolder {

        // each data item is just a string in this case
        //ImageView imageView;
        ImageView imageView;

        ViewHolder(View v) {
            super(v);
            imageView = v.findViewById(R.id.imageViewhomeicon);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    HomeStudyIconAdapter(List<String> iv) {
        System.out.println("ivivivi!!!");
        System.out.println(iv.size());
        this.iv=iv;
    }

    // Create new views (invoked by the layout manager)
    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // create a new view
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.raw_icon, parent, false);

        // set the view's size, margins, paddings and layout parameters

        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        //holder.textView.setText(iNames.get(position));
        //holder.dateView.setText(iDate.get(position));
        System.out.println("iconnnnnnn!!!");
        holder.imageView.setImageResource(R.drawable.colobo_studygraph);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return iv.size();
    }
}
