package com.forecasting.regression;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.forecasting.regression.database.AppDatabase;
import com.forecasting.regression.database.TaskEntry;

// Make this class extend ViewModel
public class AddTaskViewModel extends ViewModel {

    // Add a task member variable for the TaskEntry object wrapped in a LiveData
    private LiveData<TaskEntry> task;

    // Create a constructor where you call loadTaskById of the taskDao to initialize the tasks variable
    // Note: The constructor should receive the database and the taskId
    public AddTaskViewModel(AppDatabase database, int taskId) {
        task = database.taskDao().loadTaskById(taskId);
    }

    // Create a getter for the task variable
    public LiveData<TaskEntry> getTask() {
        return task;
    }
}
