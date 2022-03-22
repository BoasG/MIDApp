package is.hi.midapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.view.View;
import android.widget.Toast;
import android.util.Patterns;

public class subscribe extends AppCompatActivity {
    //declare attributes
    EditText editName;
    EditText Password_id;
    EditText Repeatpassword_id;
    EditText EmailField;
    Button Subscribe_button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscribe);

        //associate attributes with Viewids
        editName = findViewById(R.id.editName);
        Password_id = findViewById(R.id.Password_id);
        Repeatpassword_id = findViewById(R.id.Repeatpassword_id);
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
        if (isEmpty(editName)) {
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