package is.hi.midapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import java.util.Calendar;

import is.hi.midapp.Persistance.Entities.PostTask;
import is.hi.midapp.Persistance.Entities.Task;
import is.hi.midapp.Persistance.Entities.TaskCategory;
import is.hi.midapp.Persistance.Entities.TaskStatus;
import is.hi.midapp.networking.NetworkCallback;
import is.hi.midapp.networking.NetworkManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateTaskActivity extends AppCompatActivity {

    private Button mCreateTaskButton;
    private Button mBack;
    private TextView mTaskNameText;
    private Switch mTaskPrioritySwitch;
    private Spinner mTaskCategorySpinner;
    private CalendarView mTaskDueDateCalendarView;

    // creating constant keys for shared preferences.
    public static final String SHARED_PREFS = "shared_prefs";

    // key for storing email.
    public static final String USERNAME_KEY  = "username_key";

    // variable for shared preferences.
    SharedPreferences sharedpreferences;
    String username;
    long eventOccursOn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);

        // initializing our shared preferences.
        sharedpreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);

        // getting data from shared prefs and
        // storing it in our string variable.
        username = sharedpreferences.getString(USERNAME_KEY, null);
        Log.d("TAG",  username);

        mCreateTaskButton = (Button) findViewById(R.id.create_task_button);
        mCreateTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createTask();
                goToViewTask();
            }
        });
        mBack = (Button) findViewById(R.id.back_view);
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToViewTask();
            }
        });

        mTaskNameText = (TextView) findViewById(R.id.task_name_plainText);
        mTaskPrioritySwitch = (Switch) findViewById(R.id.task_priority_switch);
        mTaskCategorySpinner = (Spinner) findViewById(R.id.task_category_spinner);
        mTaskCategorySpinner.setAdapter(new ArrayAdapter<TaskCategory>
                (this, android.R.layout.simple_spinner_item, TaskCategory.values()));
        mTaskDueDateCalendarView = (CalendarView) findViewById(R.id.task_due_date_calendarView);

        mTaskDueDateCalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            //show the selected date as a toast
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int day) {
                Toast.makeText(getApplicationContext(), day + "/" + month + "/" + year, Toast.LENGTH_LONG).show();
                Calendar c = Calendar.getInstance();
                c.set(year, month, day);
                eventOccursOn = c.getTimeInMillis(); //this is what you want to use later
            }
        });

    }


    private void createTask() {
        Task task = new Task(mTaskNameText.getText().toString(), mTaskPrioritySwitch.isChecked(),
                new Date(mTaskDueDateCalendarView.getDate()), new Date(mTaskDueDateCalendarView.getDate()), new Date(mTaskDueDateCalendarView.getDate()),
                (TaskCategory) mTaskCategorySpinner.getSelectedItem(), TaskStatus.NOT_STARTED);
        PostTask postTask = new PostTask(mTaskNameText.getText().toString(), String.valueOf(mTaskPrioritySwitch.isChecked()),
                String.valueOf(new Date(mTaskDueDateCalendarView.getDate())) , String.valueOf(new Date(mTaskDueDateCalendarView.getDate())),
                String.valueOf(new Date(eventOccursOn)),
                ((TaskCategory) ((TaskCategory) mTaskCategorySpinner.getSelectedItem())).getEnumValue(),
                 "NOT_STARTED", username);
        NetworkCallback networkCallback = NetworkManager.getService().create(NetworkCallback.class);
        Call<Task> apiCall = networkCallback.addATask(postTask);
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
                    if(listOfTasks != null){
                        String responseString = "Response Code : " + response.code() + "\nName : " + listOfTasks.getName();
                        Log.d("", responseString);
                    }
                } else{ Log.d("", "No success but no failure "); }
            }

            @Override
            public void onFailure(Call<Task> apicall, Throwable t) {
                Log.e("", "Failed to get tasks: ");
            }
        });
    }

    private void goToViewTask() {
        Intent i = new Intent(CreateTaskActivity.this, TaskActivity.class);
        startActivity(i);

    }

}