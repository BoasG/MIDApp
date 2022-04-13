package is.hi.midapp;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.SearchEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.content.Intent;
import android.widget.Button;
import android.widget.Toast;

import is.hi.midapp.Persistance.Entities.Task;
import is.hi.midapp.Persistance.Entities.TaskCategory;
import is.hi.midapp.Persistance.Entities.TaskStatus;
import is.hi.midapp.networking.NetworkCallback;
import is.hi.midapp.networking.NetworkManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TaskActivity extends AppCompatActivity {

    private Button mGoToCreateTaskButton;
    private Button mGoToMainButton;
    private SearchView mSearch;

    // initialize variables
    TextView textView;
    ArrayList<TaskListView> arrayList;
    boolean[] selectedFilters;
    String[] allFilters;
    //TODO: Setja í fylki
    boolean fPriority1, fPriority2;
    String fCategory1, fCategory2, fCategory3,
            fCategory4, fCategory5, fCategory6,
            fCategory7, fCategory8,
            fStatus1, fStatus2, fStatus3;

    //List<String> allTaskNames = new ArrayList<>();
    ArrayList<Integer> langList = new ArrayList<>();
    //TODO: Breyta þannig að filterar noti enum strengi
    String[] langArray = {"High Priority", "Low Priority", "Household chores", "Training and Competition", "Schoolwork", "Work", "Hobbies","Self Care","Family","Friends","Not Started","In progress", "Completed"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        // assign variable
        textView = findViewById(R.id.filter);
        resetFilters();

        NetworkCallback networkCallback = NetworkManager.getService().create(NetworkCallback.class);
        Call<List<Task>> apiCall = networkCallback.getTasks();
        callNetworkList(apiCall);

        // initialize selected language array
        selectedFilters = new boolean[langArray.length];
        allFilters = new String[langArray.length];
        SearchView mSearch = (SearchView) findViewById(R.id.search); //Initiate a search view
        mSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Call<List<Task>> apiCall = networkCallback.getTaskByName(query.toString());
                callNetworkList(apiCall);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

        mGoToCreateTaskButton = (Button) findViewById(R.id.new_task);
        mGoToCreateTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToCreateTask();
            }
        });
        mGoToMainButton = (Button) findViewById(R.id.back_home);
        mGoToMainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToMain();
            }
        });

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetFilters();
                // Initialize alert dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(TaskActivity.this);

                // set title
                builder.setTitle("Select Filters");

                // set dialog non cancelable
                builder.setCancelable(false);

                builder.setMultiChoiceItems(langArray, selectedFilters, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i, boolean b) {
                        // check condition
                        if (b) {
                            // when checkbox selected
                            // Add position in lang list
                            langList.add(i);
                            // Sort array list
                            Collections.sort(langList);
                        } else {
                            // when checkbox unselected
                            // Remove position from langList
                            langList.remove(Integer.valueOf(i));
                        }
                    }
                });

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Initialize string builder
                        StringBuilder stringBuilder = new StringBuilder();
                        // use for loop
                        for (int j = 0; j < langList.size(); j++) {
                            // concat array value
                            stringBuilder.append(langArray[langList.get(j)]);
                            // check condition
                            if (j != langList.size() - 1) {
                                // When j value not equal
                                // to lang list size - 1
                                // add comma
                                stringBuilder.append(", ");
                            }
                        }
                        getFilters(selectedFilters);
                        //TODO: Setja í fylki
                        //Call<List<Task>> apiCall = networkCallback.getTasksWFilters(fPriority, fCategory, fStatus);
                        Call<List<Task>> apiCall = networkCallback.findTasks(fPriority1, fPriority2,
                                fCategory1, fCategory2, fCategory3, fCategory4,
                                fCategory5, fCategory6, fCategory7, fCategory8,
                                fStatus1, fStatus2, fStatus3);
                        //Call<List<Task>> apiCall = networkCallback.findTasks(allFilters);
                        callNetworkList(apiCall);
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // dismiss dialog
                        dialogInterface.dismiss();
                    }
                });
                builder.setNeutralButton("Clear All", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // use for loop
                        for (int j = 0; j < selectedFilters.length; j++) {
                            // remove all selection
                            selectedFilters[j] = false;
                            // clear language list
                            langList.clear();
                            // clear text view value
                            textView.setText("");
                        }
                    }
                });
                // show dialog
                builder.show();
            }
        });
    }

    public void goToNewTask(long ID){
        Log.d("TAG", "goToNewTask: ");
        NetworkCallback networkCallback = NetworkManager.getService().create(NetworkCallback.class);
        Call<Task> call = networkCallback.deleteTask(ID);
        callNetworkTask(call);
        //Fa uppfaerdan lista
        Toast.makeText(TaskActivity.this, "Task deleted",
                Toast.LENGTH_SHORT).show();
        Intent i = new Intent(TaskActivity.this, TaskActivity.class);
        startActivity(i);
    }

    public void goToCreateTask() {
        Intent i = new Intent(TaskActivity.this, CreateTaskActivity.class);
        startActivity(i);

    }

    private void goToMain() {
        Intent i = new Intent(TaskActivity.this, MainActivity.class);
        startActivity(i);
    }

    private void loadTasks(List<Task> listOfTasks){
        // create a arraylist of the type NumbersView
        final ArrayList<TaskListView> arrayList = new ArrayList<TaskListView>();

        for(int i = 0; i < listOfTasks.size(); i++){
            String name = listOfTasks.get(i).getName();
            String status;
            String date;
            if(listOfTasks.get(i).getDueDate() == null){
                date = "";
            } else {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
                date = simpleDateFormat.format(listOfTasks.get(i).getDueDate());
            }
            if(listOfTasks.get(i).getDueDate() == null){
                status = "";
            } else {
                status = listOfTasks.get(i).getStatus().getDisplayValue();
            }

            arrayList.add(new TaskListView(listOfTasks.get(i).getID(),name, status, date));
        }

        // Now create the instance of the NumebrsViewAdapter and pass
        // the context and arrayList created above
        TaskListViewAdapter taskListViewAdapter = new TaskListViewAdapter(this, arrayList);

        // create the instance of the ListView to set the numbersViewAdapter
        ListView numbersListView = findViewById(R.id.list_task);

        // set the numbersViewAdapter for ListView
        numbersListView.setAdapter(taskListViewAdapter);
    }

    private void getFilters(boolean[] selectedFilters){
        //TODO: Setja í fylki
        if(selectedFilters[0] == true){
            //allFilters[0] = "true";
            fPriority1 = true;
        }
        if(selectedFilters[1] == true){
            //allFilters[1] = "true";
            fPriority2 = true;
        }
        if(selectedFilters[2] == true){
            //allFilters[2] = "HOUSEHOLD";
            fCategory1 = "HOUSEHOLD";
        }
        if(selectedFilters[3] == true){
            //allFilters[3] = "SPORTS";
            fCategory2 = "SPORTS";
        }
        if(selectedFilters[4] == true){
            //allFilters[4] = "SCHOOL";
            fCategory3 = "SCHOOL";
        }
        if(selectedFilters[5] == true){
            //allFilters[5] = "WORK";
            fCategory4 = "WORK";
        }
        if(selectedFilters[6] == true){
            //allFilters[6] = "HOBBIES";
            fCategory5 = "HOBBIES";
        }
        if(selectedFilters[7] == true){
            //allFilters[7] = "SELF_CARE";
            fCategory6 = "SELF_CARE";
        }
        if(selectedFilters[8] == true){
            //allFilters[8] = "FAMILY";
            fCategory7 = "FAMILY";
        }
        if(selectedFilters[9] == true){
            //allFilters[9] = "FRIENDS";
            fCategory8 = "FRIENDS";
        }
        if(selectedFilters[10] == true){
            //allFilters[10] = "NOT_STARTED";
            fStatus1 = "NOT_STARTED";
        }
        if(selectedFilters[11] == true){
            //allFilters[11] = "IN_PROGRESS";
            fStatus2 = "IN_PROGRESS";
        }
        if(selectedFilters[12] == true){
            //allFilters[12] = "COMPLETED";
            fStatus3 = "COMPLETED";
        }
    }

    private void resetFilters(){
        fPriority1 = false;
        fPriority2 = false;
        fCategory1 = "";
        fCategory2 = "";
        fCategory3 = "";
        fCategory4 = "";
        fCategory5 = "";
        fCategory6 = "";
        fCategory7 = "";
        fCategory8 = "";
        fStatus1 = "";
        fStatus2 = "";
        fStatus3 = "";
    }

    private void callNetworkList(Call<List<Task>> apiCall){
        apiCall.enqueue(new Callback<List<Task>>() {
            @Override
            public void onResponse(Call<List<Task>> apicall, Response<List<Task>> response) {
                /// Once we get response, it can be success or failure
                if (response.isSuccessful()) {
                    /// If successful
                    List<Task> listOfTasks = response.body();
                    loadTasks(listOfTasks);
                } else {
                    Log.d("", "No success but no failure "); }
            }

            @Override
            public void onFailure(Call<List<Task>> apicall, Throwable t) {
                Log.e("", "Failed to get tasks: ");
            }
        });
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
