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


    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Task() {
    }

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

    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
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

    public void setPriority(Boolean priority) {
        this.priority = priority;
    }

    public Task getMotherTask() {
        return motherTask;
    }

    public void setMotherTask(Task motherTask) {
        this.motherTask = motherTask;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public TaskCategory getCategory() {
        return category;
    }

    public void setCategory(TaskCategory category) {
        this.category = category;
    }
}
