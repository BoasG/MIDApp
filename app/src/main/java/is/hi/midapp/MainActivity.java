package is.hi.midapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button mGoToCreateTaskButton;
    private Button mGoToViewTaskButton;
    private Button mGoToSignUpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mGoToCreateTaskButton = (Button) findViewById(R.id.go_to_create_task_button);
        mGoToCreateTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToCreateTask();
            }
        });
        mGoToViewTaskButton = (Button) findViewById(R.id.go_to_view_task_button);
        mGoToViewTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToViewTask();
            }
        });
        mGoToSignUpButton = (Button) findViewById(R.id.go_to_sign_up_button);
        mGoToSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToSignUp();
            }
        });
    }

    private void goToCreateTask() {
        Intent i = new Intent(MainActivity.this, CreateTaskActivity.class);
        startActivity(i);
    }
    private void goToViewTask() {
        Intent i = new Intent(MainActivity.this, TaskActivity.class);
        startActivity(i);
    }
    private void goToSignUp() {
        Intent i = new Intent(MainActivity.this, SignUpActivity.class);
        startActivity(i);
    }
}