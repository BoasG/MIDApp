package is.hi.midapp.Persistance.Entities;

import java.util.Date;

public class Task {

    public long ID;

    private String name;
    private Boolean priority;
    private Date startDate;
    private Date endDate;
    private Date dueDate;
    private TaskCategory category;
    private TaskStatus status;
    private Task motherTask;

    public Task(String name, Boolean priority, Date startDate, Date endDate, Date dueDate,
                TaskCategory taskCategory, TaskStatus taskStatus) {
        this.name = name;
        this.priority = priority;
        this.startDate = startDate;
        this.endDate = endDate;
        this.dueDate = dueDate;
        this.category = taskCategory;
        this.status = taskStatus;
    }


    public Date getDueDate() {
        return dueDate;
    }

    public long getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getPriority() {
        return priority;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public TaskCategory getCategory() {
        return category;
    }

}
