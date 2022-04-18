package is.hi.midapp.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.ArrayList;

import is.hi.midapp.R;
import is.hi.midapp.TaskActivity;

public class TaskListViewAdapter extends ArrayAdapter<TaskListView> {

    private Context context;
    // invoke the suitable constructor of the ArrayAdapter class
    public TaskListViewAdapter(@NonNull Context context, ArrayList<TaskListView> arrayList) {
        // pass the context and arrayList for the super
        // constructor of the ArrayAdapter class
        super(context, 0, arrayList);
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // convertView which is recyclable view
        View currentItemView = convertView;

        // of the recyclable view is null then inflate the custom layout for the same
        if (currentItemView == null) {
            currentItemView = LayoutInflater.from(getContext()).inflate(R.layout.task_list, parent, false);
        }

        LinearLayout linearLayout = currentItemView.findViewById(R.id.linLay);
        linearLayout.setTag(position);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("", "onClick: ");
                int position = (Integer) view.getTag();
                // Access the row position here to get the correct data item
                // get the position of the view from the ArrayAdapter
                TaskListView currentNumberPosition = getItem(position);
                String name = currentNumberPosition.getmTaskName();
                long id = currentNumberPosition.getmID();
                Log.d("TAG", "name: " + name);
                Log.d("TAG", "id: " + id);
                if (context instanceof TaskActivity) {
                    ((TaskActivity)context).goToNewTask(id);
                }
                // Do what you want here...
            }
        });

        // get the position of the view from the ArrayAdapter
        TaskListView currentNumberPosition = getItem(position);

        // then according to the position of the view assign the desired TextView 1 for the same
        TextView textView1 = currentItemView.findViewById(R.id.textView1);
        textView1.setText(currentNumberPosition.getmTaskName());

        // then according to the position of the view assign the desired TextView 2 for the same
        TextView textView2 = currentItemView.findViewById(R.id.textView2);
        textView2.setText(currentNumberPosition.getmTaskStatus());

        // then according to the position of the view assign the desired TextView 3 for the same
        TextView textView3 = currentItemView.findViewById(R.id.textView3);
        textView3.setText(currentNumberPosition.getmTaskDueDate());

        // then return the recyclable view
        return currentItemView;
    }
}
