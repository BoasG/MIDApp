package is.hi.midapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import is.hi.midapp.Persistance.Entities.Task;
import is.hi.midapp.Persistance.Entities.User;
import is.hi.midapp.networking.NetworkCallback;
import is.hi.midapp.networking.NetworkManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//TODO implement autofill
public class ManageUserActivity extends AppCompatActivity {
    //declare attributes
    EditText mName;
    EditText mPassword;
    EditText mRepeatPassword;
    EditText mEmail;
    Button mSubscribe_button;

    // creating constant keys for shared preferences.
    public static final String SHARED_PREFS = "shared_prefs";

    // key for storing username.
    public static final String USERNAME_KEY  = "username_key";

    // key for storing password.
    public static final String PASSWORD_KEY = "password_key";


    // variable for shared preferences.
    SharedPreferences sharedpreferences;
    String username;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_user);

        // initializing our shared preferences.
        sharedpreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);

        // getting data from shared prefs and
        // storing it in our string variable.
        username = sharedpreferences.getString(USERNAME_KEY, null);


        //associate attributes with Viewids
        mName = findViewById(R.id.editName);
        mPassword = findViewById(R.id.Password);
        mRepeatPassword = findViewById(R.id.Repeatpassword);
        mEmail = findViewById(R.id.EmailField);
        mSubscribe_button = findViewById(R.id.Subscribe_button);

        getUserByUsername();


        mSubscribe_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkDataEntered();

            }
        });

    }

    //check entered data for the name
    void checkDataEntered() {
        if (isEmpty(mPassword)) {
            Toast t = Toast.makeText(this, "You must enter a password to register!", Toast.LENGTH_SHORT);
            t.show();
        }
        if (isEmail(mEmail) == false) {
            mEmail.setError("Enter valid email!");
        }
        if((mPassword.getText().toString()).equals(mRepeatPassword.getText().toString()) == false){
            Toast t = Toast.makeText(this, "Passwords do not match!", Toast.LENGTH_SHORT);
            t.show();
        } else if(isEmail(mEmail) && isEmpty(mPassword) == false){
            User user = new User(mName.getText().toString(), mPassword.getText().toString(), mEmail.getText().toString());
            Log.d("TAG", mName.getText().toString());
            Log.d("TAG", mPassword.getText().toString());
            Log.d("TAG", mEmail.getText().toString());
            Log.d("TAG", user.getUsername());
            Log.d("TAG", user.getEmail());
            Log.d("TAG", user.getPassword());

            NetworkCallback networkCallback = NetworkManager.getService().create(NetworkCallback.class);
            //PUT á Userinn
            Call<User> apiCall = networkCallback.changeUser(user);
            callNetwork(apiCall);
            Toast t = Toast.makeText(this, "Successfully changed user", Toast.LENGTH_SHORT);
            t.show();

            SharedPreferences.Editor editor = sharedpreferences.edit();

            // below two lines will put values for
            // email and password in shared preferences.
            //editor.putString(USERNAME_KEY, mUsername.getText().toString());
            editor.putString(PASSWORD_KEY, mPassword.getText().toString());

            // to save our data with key and value.
            editor.apply();

            goToViewTask();
        }
    }

    //check if a text is empty
    boolean isEmpty(EditText text) {
        CharSequence str = text.getText().toString();
        return TextUtils.isEmpty(str);
    }

    //function to check on email field
    boolean isEmail(EditText text) {
        CharSequence email = text.getText().toString();
        return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }

    private void getUserByUsername(){
        NetworkCallback networkCallback = NetworkManager.getService().create(NetworkCallback.class);
        Call<User> apiCall = networkCallback.getUserByName(username);
        callNetwork(apiCall);
        Log.d("TAG", "getUserByUsername: ");
    }

    private void autofill(User user){
        Log.d("TAG", "autofill: ");
        mName.setText(user.getUsername());
        mPassword.setText(user.getPassword());
        mRepeatPassword.setText(user.getPassword());
        mEmail.setText(user.getEmail());
    }

    private void callNetwork(Call<User> apiCall){
        apiCall.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> apicall, Response<User> response) {
                /// Once we get response, it can be success or failure
                if (response.isSuccessful()) {
                    /// If successful
                    User user = response.body();
                    if(user.getUsername() == null){
                        String responseString = "Response Code : " + response.code() + "no user with that username";
                        Log.d("", responseString);
                    } else {
                        //Komin með Userinn með name, pw og email
                        String responseString = "Response Code : " + response.code() + "\nUsername : " + user.getPassword();
                        Log.d("", responseString);
                        autofill(user);
                    }
                } else{ Log.d("", "No success but no failure "); }
            }

            @Override
            public void onFailure(Call<User> apicall, Throwable t) {
                Log.e("", "Failed to get tasks: ");
            }
        });
    }

    private void goToViewTask() {
        Intent i = new Intent(ManageUserActivity.this, TaskActivity.class);
        startActivity(i);

    }
}