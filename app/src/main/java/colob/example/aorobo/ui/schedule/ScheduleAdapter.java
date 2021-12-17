package colob.example.aorobo.ui.schedule;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import colob.example.aorobo.R;

import java.util.List;


public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.ViewHolder> {

    private final List<String> iNames;
    private final List<String> iDate;
    private final List<Integer> id;
    private final List<String> iPeriod;


    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    static class ViewHolder extends RecyclerView.ViewHolder {

        // each data item is just a string in this case
        //ImageView imageView;schedule_contents
        TextView textView;
        TextView dateView;

        TextView idView;
        TextView periodView;

        ViewHolder(View v) {
            super(v);
            textView = v.findViewById(R.id.schedule_name);
            dateView = v.findViewById(R.id.schedule_limit);
            periodView = v.findViewById(R.id.schedule_contents);
            //schedule_id
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    ScheduleAdapter(List<String> itemNames, List<String> itemDate,List<Integer>id,List<String> itemPeriod) {
        this.iNames = itemNames;
        this.iDate = itemDate;
        this.id=id;
        this.iPeriod=itemPeriod;
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
        holder.dateView.setText(iDate.get(position));
        holder.periodView.setText(iPeriod.get(position));



    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return iNames.size();
    }

    public int getId(int position) {

        return id.get(position);
    }
}
