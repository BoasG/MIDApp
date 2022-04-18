package is.hi.midapp;

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

public class KanbanViewAdapter extends ArrayAdapter<KanbanView>{

    private Context context;
    // invoke the suitable constructor of the ArrayAdapter class
    public KanbanViewAdapter(@NonNull Context context, ArrayList<KanbanView> arrayList) {
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
            currentItemView = LayoutInflater.from(getContext()).inflate(R.layout.kanban_list, parent, false);
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
                KanbanView currentNumberPosition = getItem(position);
                String name = currentNumberPosition.getmTaskName();
                long id = currentNumberPosition.getmID();
                Log.d("TAG", "name: " + name);
                Log.d("TAG", "id: " + id);
                if (context instanceof TaskActivity) {
                    ((KanbanActivity)context).goToNewTask(id);
                }
                // Do what you want here...
            }
        });

        // get the position of the view from the ArrayAdapter
        KanbanView currentNumberPosition = getItem(position);

        // then according to the position of the view assign the desired TextView 1 for the same
        TextView textView1 = currentItemView.findViewById(R.id.textView1);
        textView1.setText(currentNumberPosition.getmTaskName());

        // then return the recyclable view
        return currentItemView;
    }
}
