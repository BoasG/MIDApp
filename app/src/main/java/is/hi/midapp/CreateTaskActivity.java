package is.hi.midapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;

import is.hi.midapp.Persistance.Entities.Task;
import is.hi.midapp.Persistance.Entities.TaskCategory;
import is.hi.midapp.Persistance.Entities.TaskStatus;
import is.hi.midapp.networking.NetworkCallback;
import is.hi.midapp.networking.NetworkManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Body;

public class CreateTaskActivity extends AppCompatActivity {

    private Button mCreateTaskButton;
    private Button mGoToMainButton;
    private TextView mTaskNameText;
    private Switch mTaskPrioritySwitch;
    private Spinner mTaskCategorySpinner;
    private CalendarView mTaskDueDateCalendarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);
        mCreateTaskButton = (Button) findViewById(R.id.create_task_button);
        mCreateTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createTask();
            }
        });
        mGoToMainButton = (Button) findViewById(R.id.back_main);
        mGoToMainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToMain();
            }
        });

        mTaskNameText = (TextView) findViewById(R.id.task_name_plainText);
        mTaskPrioritySwitch = (Switch) findViewById(R.id.task_priority_switch);
        mTaskCategorySpinner = (Spinner) findViewById(R.id.task_category_spinner);
        mTaskCategorySpinner.setAdapter(new ArrayAdapter<TaskCategory>
                (this, android.R.layout.simple_spinner_item, TaskCategory.values()));
        mTaskDueDateCalendarView = (CalendarView) findViewById(R.id.task_due_date_calendarView);
    }


    private void createTask() {
        Task task = new Task(mTaskNameText.getText().toString(), mTaskPrioritySwitch.isChecked(),
                null, null, new Date(mTaskDueDateCalendarView.getDate()),
                (TaskCategory) mTaskCategorySpinner.getSelectedItem(), TaskStatus.NOT_STARTED);

        //TODO: Finish implementation
        NetworkCallback networkCallback = NetworkManager.getService().create(NetworkCallback.class);
        Call<Task> apiCall = networkCallback.addTask(task);
        callNetwork(apiCall);
        toastTask(task);
    }

    private void toastTask(Task task) {
        //TODO: Get rid of debug function
        Toast.makeText(CreateTaskActivity.this, task.getName(), Toast.LENGTH_SHORT).show();
        Toast.makeText(CreateTaskActivity.this, task.getPriority().toString(),
                Toast.LENGTH_SHORT).show();
        Toast.makeText(CreateTaskActivity.this, task.getDueDate().toString(),
                Toast.LENGTH_SHORT).show();
        Toast.makeText(CreateTaskActivity.this, task.getCategory().toString(),
                Toast.LENGTH_SHORT).show();

    }

    private void callNetwork(Call<Task> apiCall){
        apiCall.enqueue(new Callback<Task>() {
            @Override
            public void onResponse(Call<Task> apicall, Response<Task> response) {
                /// Once we get response, it can be success or failure
                if (response.isSuccessful()) {
                    /// If successful
                    Task listOfTasks = response.body();
                } else{ Log.d("", "No success but no failure ");
                }
            }

            @Override
            public void onFailure(Call<Task> apicall, Throwable t) {
                Log.e("", "Failed to get tasks: ");
            }
        });
    }
    private void goToMain() {
        Intent i = new Intent(CreateTaskActivity.this, MainActivity.class);
        startActivity(i);
    }
}