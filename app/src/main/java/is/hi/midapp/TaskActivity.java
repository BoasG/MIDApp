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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.content.Intent;
import android.widget.Button;

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
    boolean[] selectedFilters;
    boolean fPriority = false;
    String fCategory = null;
    String fStatus = null;

    List<String> allTaskNames = new ArrayList<>();
    ArrayList<Integer> langList = new ArrayList<>();
    //TODO: Breyta þannig að filterar noti enum strengi
    //String[] langArray = {"Priority", "Household chores", "Training and Competition", "Schoolwork", "Work", "Hobbies","Self Care","Family","Friends","Not Started","In progress", "Completed"};
    String[] langArray = {"High Priority", "Schoolwork","Not Started"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        // assign variable
        textView = findViewById(R.id.filter);

        NetworkCallback networkCallback = NetworkManager.getService().create(NetworkCallback.class);
        Call<List<Task>> apiCall = networkCallback.getTasks();
        callNetworkList(apiCall);

        // initialize selected language array
        selectedFilters = new boolean[langArray.length];
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
                        //TODO: Láta filtera virka bæði fyrir fáa og alla
                        if(selectedFilters[0] == true){
                            fPriority = true;
                        }
                        if(selectedFilters[1] == true){
                            fCategory = "SCHOOL";
                        }
                        if(selectedFilters[2] == true){
                            fStatus = "NOT_STARTED";
                        }
                        Call<List<Task>> apiCall = networkCallback.getTasksWFilters(fPriority, fCategory, fStatus);
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

    private void goToCreateTask() {
        Intent i = new Intent(TaskActivity.this, CreateTaskActivity.class);
        startActivity(i);

    }

    private void goToMain() {
        Intent i = new Intent(TaskActivity.this, MainActivity.class);
        startActivity(i);
    }

    private void loadTasks(List<Task> listOfTasks){
        allTaskNames.clear();
        ListView listView = (ListView) findViewById(R.id.list_task);

        for(int i = 0; i < listOfTasks.size(); i++){
            allTaskNames.add(listOfTasks.get(i).getName());
        }
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, allTaskNames);
        listView.setAdapter(adapter);
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
}
