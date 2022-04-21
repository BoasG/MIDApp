package is.hi.midapp.adapters;

import is.hi.midapp.Persistance.Entities.Task;

public class KanbanView {

    // TextView 1
    private String mTaskName;

    private long mID;
    private Task mTask;

    // create constructor to set the values for all the parameters of the each single view
    public KanbanView(Task mTask, long mID, String mTaskName) {
        this.mTask = mTask;
        this.mID = mID;
        this.mTaskName = mTaskName;
    }

    public long getmID() { return mID; }

    public String getmTaskName() {
        return mTaskName;
    }

    public Task getmTask() {return mTask; }
}