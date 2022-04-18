package is.hi.midapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import is.hi.midapp.Persistance.Entities.Task;
import is.hi.midapp.Persistance.Entities.TaskStatus;
import is.hi.midapp.adapters.KanbanView;
import is.hi.midapp.adapters.KanbanViewAdapter;
import is.hi.midapp.adapters.TaskListView;
import is.hi.midapp.networking.NetworkCallback;
import is.hi.midapp.networking.NetworkManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KanbanActivity extends AppCompatActivity {

    private Button mGoToCreateTaskButton;
    private Button mLogOut;
    private Button mGoToManageAccount;
    private Button mGoToViewTaskButton;

    // creating constant keys for shared preferences.
    public static final String SHARED_PREFS = "shared_prefs";

    // key for storing email.
    public static final String USERNAME_KEY  = "username_key";


    // variable for shared preferences.
    SharedPreferences sharedpreferences;
    String username;

    // initialize variables
    TextView textView;
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
        setContentView(R.layout.activity_kanban);

        // initializing our shared preferences.
        sharedpreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);

        // getting data from shared prefs and
        // storing it in our string variable.
        username = sharedpreferences.getString(USERNAME_KEY, null);
        Log.d("TAG",  username);

        // assign variable
        textView = findViewById(R.id.filter);
        resetFilters();

        NetworkCallback networkCallback = NetworkManager.getService().create(NetworkCallback.class);
        Call<List<Task>> apiCall = networkCallback.getTasksByOwner(username);
        callNetworkList(apiCall);

        // initialize selected language array
        selectedFilters = new boolean[langArray.length];
        allFilters = new String[langArray.length];
        SearchView mSearch = (SearchView) findViewById(R.id.search); //Initiate a search view
        mSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Call<List<Task>> apiCall = networkCallback.getTaskByName(query, username);
                callNetworkList(apiCall);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

        mGoToViewTaskButton = (Button) findViewById(R.id.go_to_view_task_button);
        mGoToViewTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToViewTask();
            }
        });

        mGoToCreateTaskButton = (Button) findViewById(R.id.new_task);
        mGoToCreateTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToCreateTask();
            }
        });
        mGoToManageAccount = (Button) findViewById(R.id.manage_account);
        mGoToManageAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mGoToManageAccount();
            }
        });
        mLogOut = (Button) findViewById(R.id.log_out);
        mLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // calling method to edit values in shared prefs.
                SharedPreferences.Editor editor = sharedpreferences.edit();

                // below line will clear
                // the data in shared prefs.
                editor.clear();

                // below line will apply empty
                // data to shared prefs.
                editor.apply();

                // starting mainactivity after
                // clearing values in shared preferences.
                Intent i = new Intent(KanbanActivity.this, LoginActivity.class);
                startActivity(i);
                finish();
            }
        });

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetFilters();
                // Initialize alert dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(KanbanActivity.this);

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
                                fStatus1, fStatus2, fStatus3, username);
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
        Log.d("TAG",  username);
        Log.d("TAG", "goToNewTask: ");
        NetworkCallback networkCallback = NetworkManager.getService().create(NetworkCallback.class);
        Call<Task> call = networkCallback.deleteTask(ID);
        callNetworkTask(call);
        //Fa uppfaerdan lista
        Toast.makeText(KanbanActivity.this, "Task deleted",
                Toast.LENGTH_SHORT).show();
        Intent i = new Intent(KanbanActivity.this, KanbanActivity.class);
        startActivity(i);
    }

    public void goToCreateTask() {
        Intent i = new Intent(KanbanActivity.this, CreateTaskActivity.class);
        startActivity(i);

    }

    public void goToViewTask() {
        Intent i = new Intent(KanbanActivity.this, TaskActivity.class);
        startActivity(i);

    }

    public void mGoToManageAccount() {
        Intent i = new Intent(KanbanActivity.this, ManageUserActivity.class);
        startActivity(i);
    }

    //TODO implement loadTasks for kanban
    private void loadTasks(List<Task> listOfTasks){
        // create a arraylist of the type KanbanView
        //Status not started
        final ArrayList<KanbanView> arrayList1 = new ArrayList<KanbanView>();
        //Status in progress
        final ArrayList<KanbanView> arrayList2 = new ArrayList<KanbanView>();
        //Status done
        final ArrayList<KanbanView> arrayList3 = new ArrayList<KanbanView>();

        for(int i = 0; i < listOfTasks.size(); i++){
            String name = listOfTasks.get(i).getName();
            if(listOfTasks.get(i).getStatus().equals(TaskStatus.NOT_STARTED)){
                arrayList1.add(new KanbanView(listOfTasks.get(i).getID(),name));
            } else if(listOfTasks.get(i).getStatus().equals(TaskStatus.IN_PROGRESS)){
                arrayList2.add(new KanbanView(listOfTasks.get(i).getID(),name));
            } else {
                arrayList3.add(new KanbanView(listOfTasks.get(i).getID(),name));
            }

        }



        KanbanViewAdapter kanbanViewAdapter1 = new KanbanViewAdapter(this, arrayList1);
        KanbanViewAdapter kanbanViewAdapter2 = new KanbanViewAdapter(this, arrayList2);
        KanbanViewAdapter kanbanViewAdapter3 = new KanbanViewAdapter(this, arrayList3);
        // create the instance of the ListView to set the numbersViewAdapter
        ListView kanbanView1 = findViewById(R.id.list_task_ns);
        //kanbanView1.setBackgroundColor(Color.RED);

        // create the instance of the ListView to set the numbersViewAdapter
        ListView kanbanView2 = findViewById(R.id.list_task_ip);
        //kanbanView2.setBackgroundColor(Color.YELLOW);

        // create the instance of the ListView to set the numbersViewAdapter
        ListView kanbanView3 = findViewById(R.id.list_task_d);
        //kanbanView3.setBackgroundColor(Color.GREEN);

        // set the numbersViewAdapter for ListView
        kanbanView1.setAdapter(kanbanViewAdapter1);
        // set the numbersViewAdapter for ListView
        kanbanView2.setAdapter(kanbanViewAdapter2);
        // set the numbersViewAdapter for ListView
        kanbanView3.setAdapter(kanbanViewAdapter3);

    }

    private void getFilters(boolean[] selectedFilters){
        //TODO: Setja í fylki
        if(selectedFilters[0]){
            //allFilters[0] = "true";
            fPriority1 = true;
        }
        if(selectedFilters[1]){
            //allFilters[1] = "true";
            fPriority2 = true;
        }
        if(selectedFilters[2]){
            //allFilters[2] = "HOUSEHOLD";
            fCategory1 = "HOUSEHOLD";
        }
        if(selectedFilters[3]){
            //allFilters[3] = "SPORTS";
            fCategory2 = "SPORTS";
        }
        if(selectedFilters[4]){
            //allFilters[4] = "SCHOOL";
            fCategory3 = "SCHOOL";
        }
        if(selectedFilters[5]){
            //allFilters[5] = "WORK";
            fCategory4 = "WORK";
        }
        if(selectedFilters[6]){
            //allFilters[6] = "HOBBIES";
            fCategory5 = "HOBBIES";
        }
        if(selectedFilters[7]){
            //allFilters[7] = "SELF_CARE";
            fCategory6 = "SELF_CARE";
        }
        if(selectedFilters[8]){
            //allFilters[8] = "FAMILY";
            fCategory7 = "FAMILY";
        }
        if(selectedFilters[9]){
            //allFilters[9] = "FRIENDS";
            fCategory8 = "FRIENDS";
        }
        if(selectedFilters[10]){
            //allFilters[10] = "NOT_STARTED";
            fStatus1 = "NOT_STARTED";
        }
        if(selectedFilters[11]){
            //allFilters[11] = "IN_PROGRESS";
            fStatus2 = "IN_PROGRESS";
        }
        if(selectedFilters[12]){
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
