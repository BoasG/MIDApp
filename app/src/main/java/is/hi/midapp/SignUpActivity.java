package is.hi.midapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import is.hi.midapp.Persistance.Entities.Task;
import is.hi.midapp.Persistance.Entities.User;
import is.hi.midapp.networking.NetworkCallback;
import is.hi.midapp.networking.NetworkManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//TODO check id password matches repeatpassword
//TODO send a notification if user exists
//TODO send a successful notification and go to login page if succeded

public class SignUpActivity extends AppCompatActivity {
    //declare attributes
    EditText mName;
    EditText mPassword;
    EditText mRepeatPassword;
    EditText mEmail;
    Button mSubscribe_button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        //associate attributes with Viewids
        mName = findViewById(R.id.editName);
        mPassword = findViewById(R.id.Password);
        mRepeatPassword = findViewById(R.id.Repeatpassword);
        mEmail = findViewById(R.id.EmailField);
        mSubscribe_button = findViewById(R.id.Subscribe_button);

        mSubscribe_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkDataEntered();

            }
        });

    }

    //check entered data for the name
    void checkDataEntered() {
        if (isEmpty(mName) && isEmpty(mPassword)) {
            Toast t = Toast.makeText(this, "You must enter a username and password to register!", Toast.LENGTH_SHORT);
            t.show();
        }
        else if (isEmpty(mName)) {
            Toast t = Toast.makeText(this, "You must enter a username to register!", Toast.LENGTH_SHORT);
            t.show();
        }
        else if (isEmpty(mPassword)) {
            Toast t = Toast.makeText(this, "You must enter a password to register!", Toast.LENGTH_SHORT);
            t.show();
        }
        if (isEmail(mEmail) == false) {
            mEmail.setError("Enter valid email!");
        }
        if(!mPassword.getText().toString().equals(mRepeatPassword.getText().toString())){
            //TODO: undo changes
            String msg = mPassword.getText() + ":" + mRepeatPassword.getText();
            Toast t = Toast.makeText(this, msg, Toast.LENGTH_SHORT);
            t.show();
            t = Toast.makeText(this, "Passwords do not match!", Toast.LENGTH_SHORT);
            t.show();
        } else if(isEmail(mEmail) && isEmpty(mName) == false && isEmpty(mPassword) == false){
            User user = new User(mName.getText().toString(), mPassword.getText().toString(), mEmail.getText().toString());
            Log.d("TAG", mName.getText().toString());
            Log.d("TAG", mPassword.getText().toString());
            Log.d("TAG", mEmail.getText().toString());
            Log.d("TAG", user.getUsername());
            Log.d("TAG", user.getEmail());
            Log.d("TAG", user.getPassword());
            NetworkCallback networkCallback = NetworkManager.getService().create(NetworkCallback.class);
            Call<User> apiCall = networkCallback.signup(user);
            callNetworkUser(apiCall);
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

    private void callNetworkUser(Call<User> apiCall){
        apiCall.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> apicall, Response<User> response) {
                /// Once we get response, it can be success or failure
                if (response.isSuccessful()) {
                    /// If successful
                    User user = response.body();
                    String responseString = "Response Code : " + response.code() + "\nName : " + user.getUsername() + "\nPassword : " + user.getPassword();
                    Log.d("", responseString);
                    if(user.getUsername() != null){
                        Toast.makeText(SignUpActivity.this, "User created, please log in",
                                Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(SignUpActivity.this, LoginActivity.class);
                        startActivity(i);
                    } else{
                        Toast.makeText(SignUpActivity.this, "Username taken",
                                Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.d("", "No success but no failure "); }
            }

            @Override
            public void onFailure(Call<User> apicall, Throwable t) {
                Log.e("", "Failed to get user: ");
            }
        });
    }


}