
package com.forecasting.regression;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.forecasting.regression.database.AppDatabase;
import com.forecasting.regression.database.TaskEntry;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;


public class AddTaskActivity extends AppCompatActivity {

    // Extra for the task ID to be received in the intent
    public static final String EXTRA_TASK_ID = "extraTaskId";
    // Extra for the task ID to be received after rotation
    public static final String INSTANCE_TASK_ID = "instanceTaskId";

    // Constant for default task id to be used when not in update mode
    private static final int DEFAULT_TASK_ID = -1;
    // Fields for views

    @BindView(R.id.editTextName)
    EditText name;
    @BindView(R.id.editTextAddress)
    EditText address;
    @BindView(R.id.editTextPattern)
    EditText pattern;
    @BindView(R.id.editTextPeriod)
    EditText period;
    @BindView(R.id.editTextTotNoObs)
    EditText totNoObs;
    @BindView(R.id.saveButton)
    Button mButton;
    @BindView(R.id.linearLayout)
    LinearLayout linearLayout;
    private int mTaskId = DEFAULT_TASK_ID;

    // Member variable for the Database
    private AppDatabase mDb;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        ButterKnife.bind(this);

        initViews();

        mDb = AppDatabase.getInstance(getApplicationContext());

        if (savedInstanceState != null && savedInstanceState.containsKey(INSTANCE_TASK_ID)) {
            mTaskId = savedInstanceState.getInt(INSTANCE_TASK_ID, DEFAULT_TASK_ID);
        }

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(EXTRA_TASK_ID)) {
            mButton.setText(R.string.update_button);
            if (mTaskId == DEFAULT_TASK_ID) {
                // populate the UI
                mTaskId = intent.getIntExtra(EXTRA_TASK_ID, DEFAULT_TASK_ID);

                // Declare a AddTaskViewModelFactory using mDb and mTaskId
                AddTaskViewModelFactory factory = new AddTaskViewModelFactory(mDb, mTaskId);
                // for that use the factory created above AddTaskViewModel
                final AddTaskViewModel viewModel
                        = ViewModelProviders.of(this, factory).get(AddTaskViewModel.class);

                // Observe the LiveData object in the ViewModel. Use it also when removing the observer
                viewModel.getTask().observe(this, new Observer<TaskEntry>() {
                    @Override
                    public void onChanged(@Nullable TaskEntry taskEntry) {
                        viewModel.getTask().removeObserver(this);
                        populateUI(taskEntry);
                    }
                });
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(INSTANCE_TASK_ID, mTaskId);
        super.onSaveInstanceState(outState);
    }

    /**
     * initViews is called from onCreate to init the member variable views
     */
    private void initViews() {
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSaveButtonClicked();
                Toast.makeText(getApplicationContext(), "Forecast Data Updated", Toast.LENGTH_LONG).show();
            }
        });
    }

    /**
     * populateUI would be called to populate the UI when in update mode
     *
     * @param task the taskEntry to populate the UI
     */
    private void populateUI(TaskEntry task) {
        if (task == null) {
            return;
        }

        name.setText(task.getNameTask());
        address.setText(task.getAddressTask());
        pattern.setText(task.getPatternTask());
        period.setText(task.getPeriodTask());
        totNoObs.setText(task.getTotNoObsTask());
    }

    /**
     * onSaveButtonClicked is called when the "save" button is clicked.
     * It retrieves user input and inserts that new task data into the underlying database.
     */
    public void onSaveButtonClicked() {
        String nameTask = name.getText().toString();
        String addressTask = address.getText().toString();
        String patternTask = pattern.getText().toString();
        String periodTask = period.getText().toString();
        String totNoObsTask = totNoObs.getText().toString();
        Date date = new Date();

        final TaskEntry task = new TaskEntry(nameTask, addressTask, patternTask, periodTask, totNoObsTask, date);
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                if (mTaskId == DEFAULT_TASK_ID) {
                    // insert new task
                    mDb.taskDao().insertTask(task);
                } else {
                    //update task
                    task.setId(mTaskId);
                    mDb.taskDao().updateTask(task);
                }
                finish();
            }
        });
    }

}
