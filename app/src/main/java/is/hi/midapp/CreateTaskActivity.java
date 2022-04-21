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
import android.widget.LinearLayout;
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

    private static final String EXTRA_EDIT_MODE = "is.hi.midapp.edit_mode";
    private static final String EXTRA_TASK_ID = "is.hi.midapp.task_ID";
    private static final String EXTRA_TASK_NAME = "is.hi.midapp.task_name";
    private static final String EXTRA_TASK_PRIORITY = "is.hi.midapp.task_priority";
    private static final String EXTRA_TASK_STATUS = "is.hi.midapp.task_status";
    private static final String EXTRA_TASK_CATEGORY = "is.hi.midapp.task_category";
    private static final String EXTRA_TASK_DUE_DATE = "is.hi.midapp.task_due_date";

    private Button mCreateTaskButton;
    private Button mChangeTaskButton;
    private Button mBack;
    private LinearLayout mCreateTaskButtonLayout;
    private LinearLayout mChangeTaskButtonLayout;
    private Button mDeleteTaskButton;
    private TextView mTaskNameText;
    private Switch mTaskPrioritySwitch;
    private Spinner mTaskStatusSpinner;
    private Spinner mTaskCategorySpinner;
    private CalendarView mTaskDueDateCalendarView;


    private int mTaskToEditID;

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


        // initialize common variables and event listeners

        mTaskNameText = (TextView) findViewById(R.id.task_name_plainText);
        mTaskPrioritySwitch = (Switch) findViewById(R.id.task_priority_switch);
        mTaskCategorySpinner = (Spinner) findViewById(R.id.task_category_spinner);
        mTaskStatusSpinner = (Spinner) findViewById(R.id.task_status_spinner);
        mTaskStatusSpinner.setAdapter(new ArrayAdapter<TaskStatus>
                (this, android.R.layout.simple_spinner_item, TaskStatus.values()));
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

        // initialize variables and event listeners for create/edit task
        boolean inEditMode = getIntent().getBooleanExtra(EXTRA_EDIT_MODE, false);

        if(!inEditMode) {
            //Creating a task
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
        } else {
            //Editing a task
            mCreateTaskButtonLayout = (LinearLayout) findViewById(R.id.create_task_button_layout);
            mCreateTaskButtonLayout.setVisibility(View.GONE);

            mChangeTaskButtonLayout = (LinearLayout)  findViewById(R.id.change_task_button_layout);
            mChangeTaskButtonLayout.setVisibility(View.VISIBLE);

            mChangeTaskButton = (Button) findViewById(R.id.change_task_button);
            mChangeTaskButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    changeTask();
                    goToViewTask();
                }
            });

            mBack = (Button) findViewById(R.id.cancel_change_task_button);
            mBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    goToViewTask();
                }
            });

            mDeleteTaskButton = (Button) findViewById(R.id.delete_task_button);
            mDeleteTaskButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    deleteTask();
                }
            });
            mDeleteTaskButton.setVisibility(View.VISIBLE);

            fillFields();
        }
    }

    private void fillFields() {
        mTaskNameText.setText(getIntent().getStringExtra(EXTRA_TASK_NAME));
        mTaskPrioritySwitch.setChecked(getIntent().getBooleanExtra(EXTRA_TASK_PRIORITY, false));
        mTaskStatusSpinner.setSelection(getIntent().getIntExtra(EXTRA_TASK_STATUS, 0));
        mTaskCategorySpinner.setSelection(getIntent().getIntExtra(EXTRA_TASK_CATEGORY, 0));
        mTaskDueDateCalendarView.setDate(getIntent().getLongExtra(EXTRA_TASK_DUE_DATE,0));
    }

    private void changeTask() {
        //TODO: Implement for real
        Long ID = getIntent().getLongExtra(CreateTaskActivity.EXTRA_TASK_ID, -1);
        if(ID == -1) {
            Toast.makeText(CreateTaskActivity.this, "Error Changing Task!",
                    Toast.LENGTH_SHORT).show();
        } else {
            PostTask postTask = new PostTask(mTaskNameText.getText().toString(), String.valueOf(mTaskPrioritySwitch.isChecked()),
                    String.valueOf(new Date(mTaskDueDateCalendarView.getDate())), String.valueOf(new Date(mTaskDueDateCalendarView.getDate())),
                    String.valueOf(new Date(eventOccursOn)),
                    ((TaskCategory) ((TaskCategory) mTaskCategorySpinner.getSelectedItem())).getEnumValue(),
                    /*"NOT_STARTED"*/
                    ((TaskStatus) ((TaskStatus) mTaskStatusSpinner.getSelectedItem())).getEnumValue()
                    , username);
            NetworkCallback networkCallback = NetworkManager.getService().create(NetworkCallback.class);
            Call<Task> apiCall = networkCallback.updateATask(ID, postTask);
            callNetwork(apiCall);
        }
    }


    private void createTask() {
        /*
        Task task = new Task(mTaskNameText.getText().toString(), mTaskPrioritySwitch.isChecked(),
                new Date(mTaskDueDateCalendarView.getDate()),
                new Date(mTaskDueDateCalendarView.getDate()),
                new Date(mTaskDueDateCalendarView.getDate()),
                (TaskCategory) mTaskCategorySpinner.getSelectedItem(),
                (TaskStatus) mTaskStatusSpinner.getSelectedItem());
        */
        PostTask postTask = new PostTask(mTaskNameText.getText().toString(), String.valueOf(mTaskPrioritySwitch.isChecked()),
                String.valueOf(new Date(mTaskDueDateCalendarView.getDate())) , String.valueOf(new Date(mTaskDueDateCalendarView.getDate())),
                String.valueOf(new Date(eventOccursOn)),
                ((TaskCategory) ((TaskCategory) mTaskCategorySpinner.getSelectedItem())).getEnumValue(),
                 /*"NOT_STARTED"*/
                 ((TaskStatus) ((TaskStatus) mTaskStatusSpinner.getSelectedItem())).getEnumValue()
                , username);
        NetworkCallback networkCallback = NetworkManager.getService().create(NetworkCallback.class);
        Call<Task> apiCall = networkCallback.addATask(postTask);
        callNetwork(apiCall);
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

    private void deleteTask() {
        Long ID = getIntent().getLongExtra(CreateTaskActivity.EXTRA_TASK_ID, -1);
        if(ID == -1) {
            Toast.makeText(CreateTaskActivity.this, "Error Deleting Task!",
                    Toast.LENGTH_SHORT).show();
        } else {
            Log.d("TAG",  username);
            Log.d("TAG", "goToNewTask: ");
            NetworkCallback networkCallback = NetworkManager.getService().create(NetworkCallback.class);
            Call<Task> call = networkCallback.deleteTask(ID);
            callNetworkTask(call);
            //echo
            Toast.makeText(CreateTaskActivity.this, "Task deleted",
                    Toast.LENGTH_SHORT).show();
            Intent i = new Intent(CreateTaskActivity.this, TaskActivity.class);
            startActivity(i);
        }
    }

    private void goToViewTask() {
        Intent i = new Intent(CreateTaskActivity.this, TaskActivity.class);
        startActivity(i);
    }

    private void callNetworkTask(Call<Task> apiCall){
        apiCall.enqueue(new Callback<Task>() {
            @Override
            public void onResponse(Call<Task> apicall, Response<Task> response) {
                /// Once we get response, it can be success or failure
                if (response.isSuccessful()) {
                    /// If successful
                    Task task = response.body();
                } else {
                    Log.d("", "No success but no failure "); }
            }

            @Override
            public void onFailure(Call<Task> apicall, Throwable t) {
                Log.e("", "Failed to get task: ");
            }
        });
    }

}