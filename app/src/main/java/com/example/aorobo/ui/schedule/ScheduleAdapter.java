package com.example.aorobo.ui.schedule;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aorobo.R;
import com.example.aorobo.databinding.FragmentScheduleBinding;
import com.example.aorobo.db.ScheduleDataBase;
import com.example.aorobo.db.ScheduleDataBaseSingleton;
import com.example.aorobo.db.schedule.ScheduleDB;
import com.example.aorobo.db.schedule.ScheduleDBDao;
import com.example.aorobo.db.time.TimeDB;
import com.example.aorobo.ui.slideshow.SlideshowViewModel;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;



public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.ViewHolder> {

    private final List<String> iNames;
    private final List<String> iDate;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    static class ViewHolder extends RecyclerView.ViewHolder {

        // each data item is just a string in this case
        //ImageView imageView;
        TextView textView;
        TextView dateView;

        ViewHolder(View v) {
            super(v);
            textView = v.findViewById(R.id.schedule_name);
            dateView = v.findViewById(R.id.schedule_limit);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    ScheduleAdapter(List<String> itemNames, List<String> itemDate) {
        this.iNames = itemNames;
        this.iDate = itemDate;
    }

    // Create new views (invoked by the layout manager)
    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // create a new view
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row, parent, false);

        // set the view's size, margins, paddings and layout parameters

        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.textView.setText(iNames.get(position));
        holder.dateView.setText(iDate.get(position));    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return iNames.size();
    }
}
