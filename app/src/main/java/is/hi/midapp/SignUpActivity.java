package is.hi.midapp;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SignUpActivity extends AppCompatActivity {
    //declare attributes
    EditText Name;
    EditText Password;
    EditText Repeatpassword;
    EditText EmailField;
    Button Subscribe_button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        //associate attributes with Viewids
        Name = findViewById(R.id.editName);
        Password = findViewById(R.id.Password);
        Repeatpassword = findViewById(R.id.Repeatpassword);
        EmailField = findViewById(R.id.EmailField);
        Subscribe_button = findViewById(R.id.Subscribe_button);

        Subscribe_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkDataEntered();
            }
        });

    }

    //check entered data for the name
    void checkDataEntered() {
        if (isEmpty(Name)) {
            Toast t = Toast.makeText(this, "You must enter your name to register!", Toast.LENGTH_SHORT);
            t.show();
        }

        if (isEmail(EmailField) == false) {
            EmailField.setError("Enter valid email!");
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


}