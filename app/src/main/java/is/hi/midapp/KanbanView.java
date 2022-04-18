package is.hi.midapp;

public class KanbanView {

    // TextView 1
    private String mTaskName;

    private long mID;

    // create constructor to set the values for all the parameters of the each single view
    public KanbanView(long mID, String mTaskName) {
        this.mID = mID;
        this.mTaskName = mTaskName;
    }

    public long getmID() { return mID; }

    public String getmTaskName() {
        return mTaskName;
    }
}