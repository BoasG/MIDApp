package is.hi.midapp;

public class TaskListView {

    // TextView 1
    private String mTaskName;

    // TextView 2
    private String mTaskStatus;

    // TextView 3
    private String mTaskDueDate;

    private long mID;

    // create constructor to set the values for all the parameters of the each single view
    public TaskListView(long mID,String mTaskName, String mTaskStatus, String mTaskDueDate) {
        this.mID = mID;
        this.mTaskName = mTaskName;
        this.mTaskStatus = mTaskStatus;
        this.mTaskDueDate = mTaskDueDate;
    }

    public long getmID() { return mID; }

    public String getmTaskName() {
        return mTaskName;
    }

    public String getmTaskStatus() {
        return mTaskStatus;
    }

    public String getmTaskDueDate() {
        return mTaskDueDate;
    }
}
