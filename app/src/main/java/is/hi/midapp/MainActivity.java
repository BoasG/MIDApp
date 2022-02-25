package is.hi.midapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button mGoToCreateTaskButton;

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
    }

    private void goToCreateTask() {
        Intent i = new Intent(MainActivity.this, CreateTaskActivity.class);
        startActivity(i);

    }
}