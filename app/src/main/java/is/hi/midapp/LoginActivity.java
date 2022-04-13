package is.hi.midapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import is.hi.midapp.Persistance.Entities.User;
import is.hi.midapp.networking.NetworkCallback;
import is.hi.midapp.networking.NetworkManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginActivity extends AppCompatActivity {

    //declare attributes
    EditText mPassword;
    EditText mUsername;
    Button mLogin;
    Button mSignUp;

    // creating constant keys for shared preferences.
    public static final String SHARED_PREFS = "shared_prefs";

    // key for storing username.
    public static final String USERNAME_KEY = "username_key";

    // key for storing password.
    public static final String PASSWORD_KEY = "password_key";

    // variable for shared preferences.
    SharedPreferences sharedpreferences;
    String username, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // getting the data which is stored in shared preferences.
        sharedpreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);

        // in shared prefs inside het string method
        // we are passing key value as EMAIL_KEY and
        // default value is
        // set to null if not present.
        username = sharedpreferences.getString(USERNAME_KEY, null);
        password = sharedpreferences.getString(PASSWORD_KEY, null);

        //associate attributes with ViewIds
        mUsername = findViewById(R.id.username);
        mPassword = findViewById(R.id.pw);
        mLogin = (Button) findViewById(R.id.login_button);
        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Check if valid form of username and password
                checkDataEntered();
            }
        });
        mSignUp = (Button) findViewById(R.id.signup_button);
        mSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToSignUp();
            }
        });
    }

    //check entered data for the name
    void checkDataEntered() {
        if (isEmpty(mPassword)) {
            Toast t = Toast.makeText(this, "You must enter a password!", Toast.LENGTH_SHORT);
            t.show();
        }
        if (isEmpty(mUsername)) {
            Toast t = Toast.makeText(this, "You must enter a username!", Toast.LENGTH_SHORT);
            t.show();
        }
        if(isEmpty(mUsername) == false && isEmpty(mPassword) == false){
            User user = new User(mUsername.getText().toString(), mPassword.getText().toString(), null);
            //Senda fyrirspurn á bakendann um að logga inn
            NetworkCallback networkCallback = NetworkManager.getService().create(NetworkCallback.class);
            Call<User> apiCall = networkCallback.login(user);
            callNetwork(apiCall);
        }
    }

    //check if a text is empty
    boolean isEmpty(EditText text) {
        CharSequence str = text.getText().toString();
        return TextUtils.isEmpty(str);
    }

    private void goToSignUp() {
        Intent i = new Intent(LoginActivity.this, SignUpActivity.class);
        startActivity(i);
    }

    private void callNetwork(Call<User> apiCall){
        apiCall.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> apicall, Response<User> response) {
                /// Once we get response, it can be success or failure
                if (response.isSuccessful()) {
                    /// If successful
                    User user = response.body();
                    String responseString = "Response Code : " + response.code() + "\nName : " + user.getUsername() + "\nPassword : " + user.getPassword();
                    Log.d("", responseString);
                    //TODO something so Android knows who is logged in
                    //Gera eh þannig að Android viti hver er loggaður inn
                    //goToViewTask();
                    SharedPreferences.Editor editor = sharedpreferences.edit();

                    // below two lines will put values for
                    // email and password in shared preferences.
                    editor.putString(USERNAME_KEY, mUsername.getText().toString());
                    editor.putString(PASSWORD_KEY, mPassword.getText().toString());

                    // to save our data with key and value.
                    editor.apply();

                    // starting new activity.
                    Intent i = new Intent(LoginActivity.this, TaskActivity.class);
                    startActivity(i);

                } else{ Log.d("", "No success but no failure "); }
            }

            @Override
            public void onFailure(Call<User> apicall, Throwable t) {
                Log.e("", "Failed to get user: ");
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (username != null && password != null) {
            Intent i = new Intent(LoginActivity.this,TaskActivity.class);
            startActivity(i);
        }
    }


}