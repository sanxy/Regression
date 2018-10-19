
package com.forecasting.regression;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.forecasting.regression.database.TaskEntry;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

/**
 * This TaskAdapter creates and binds ViewHolders, that hold the description and priority of a task,
 * to a RecyclerView to efficiently display data.
 */
public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    // Constant for date format
    private static final String DATE_FORMAT = "yyyy/MM/dd,  h:mm a";

    // Member variable to handle item clicks
    final private ItemClickListener mItemClickListener;
    // Class variables for the List that holds task data and the Context
    private List<TaskEntry> mTaskEntries;
    private Context mContext;
    // Date formatter
    private SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT, Locale.getDefault());

    /**
     * Constructor for the TaskAdapter that initializes the Context.
     *
     * @param context  the current Context
     * @param listener the ItemClickListener
     */
    public TaskAdapter(Context context, ItemClickListener listener) {
        mContext = context;
        mItemClickListener = listener;
    }

    /**
     * Called when ViewHolders are created to fill a RecyclerView.
     *
     * @return A new TaskViewHolder that holds the view for each task
     */
    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the task_layout to a view
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.task_layout, parent, false);

        return new TaskViewHolder(view);
    }

    /**
     * Called by the RecyclerView to display data at a specified position in the Cursor.
     *
     * @param holder   The ViewHolder to bind Cursor data to
     * @param position The position of the data in the Cursor
     */
    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        // Determine the values of the wanted data
        TaskEntry taskEntry = mTaskEntries.get(position);
        String nameTask = taskEntry.getNameTask();
        String addressTask = taskEntry.getAddressTask();
        String patternTask = taskEntry.getPatternTask();
        String periodTask = taskEntry.getPeriodTask();
        String totNoObsTask = taskEntry.getTotNoObsTask();
        String updatedAt = dateFormat.format(taskEntry.getUpdatedAt());

        //Set values
        holder.taskNameView.setText(nameTask);
        holder.taskAddressView.setText(addressTask);
        holder.taskPatternView.setText(patternTask);
        holder.taskPeriodView.setText(periodTask);
        holder.taskTotNoObsView.setText(totNoObsTask);
        holder.updatedAtView.setText(updatedAt);

    }


    /**
     * Returns the number of items to display.
     */
    @Override
    public int getItemCount() {
        if (mTaskEntries == null) {
            return 0;
        }
        return mTaskEntries.size();
    }

    public List<TaskEntry> getTasks() {
        return mTaskEntries;
    }

    /**
     * When data changes, this method updates the list of taskEntries
     * and notifies the adapter to use the new values on it
     */
    public void setTasks(List<TaskEntry> taskEntries) {
        mTaskEntries = taskEntries;
        notifyDataSetChanged();
    }

    public interface ItemClickListener {
        void onItemClickListener(int itemId);
    }

    // Inner class for creating ViewHolders
    class TaskViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        // Class variables for the task description and priority TextViews
        TextView taskNameView;
        TextView taskAddressView;
        TextView taskPatternView;
        TextView taskPeriodView;
        TextView taskTotNoObsView;
        TextView updatedAtView;

        /**
         * Constructor for the TaskViewHolders.
         *
         * @param itemView The view inflated in onCreateViewHolder
         */
        public TaskViewHolder(View itemView) {
            super(itemView);

            taskNameView = itemView.findViewById(R.id.taskName);
            taskAddressView = itemView.findViewById(R.id.taskAddress);
            taskPatternView = itemView.findViewById(R.id.taskPattern);
            taskPeriodView = itemView.findViewById(R.id.taskPeriod);
            taskTotNoObsView = itemView.findViewById(R.id.taskTotNoObsTask);
            updatedAtView = itemView.findViewById(R.id.taskUpdatedAt);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int elementId = mTaskEntries.get(getAdapterPosition()).getId();
            mItemClickListener.onItemClickListener(elementId);
        }
    }
}