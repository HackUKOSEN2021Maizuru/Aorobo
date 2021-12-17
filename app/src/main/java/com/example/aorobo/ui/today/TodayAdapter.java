package com.example.aorobo.ui.today;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aorobo.R;

import java.util.List;

public class TodayAdapter  extends RecyclerView.Adapter<TodayAdapter.ViewHolder> {

    private final List<String> iNames;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    static class ViewHolder extends RecyclerView.ViewHolder {

        // each data item is just a string in this case
        //ImageView imageView;
        TextView textView;


        ViewHolder(View v) {
            super(v);
            textView = v.findViewById(R.id.today_task);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    TodayAdapter(List<String> itemNames) {
        this.iNames = itemNames;
    }

    // Create new views (invoked by the layout manager)
    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // create a new view
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row2, parent, false);

        // set the view's size, margins, paddings and layout parameters

        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        System.out.println("fsa");
        holder.textView.setText(iNames.get(position));
        //holder.textView.setTextColor(
        }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return iNames.size();
    }
}