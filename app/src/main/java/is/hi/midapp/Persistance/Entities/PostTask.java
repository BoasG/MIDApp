package is.hi.midapp.Persistance.Entities;

public class PostTask {

        private String name;
        private String priority;
        private String startDate;
        private String endDate;
        private String dueDate;
        private String category;
        private String status;
        private String owner;


    public PostTask(String name, String priority, String startDate, String endDate,
                    String dueDate, String category, String status, String owner) {
        this.name = name;
        this.priority = priority;
        this.startDate = startDate;
        this.endDate = endDate;
        this.dueDate = dueDate;
        this.category = category;
        this.status = status;
        this.owner = owner;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPriority() {
        return priority;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public String getDueDate() {
        return dueDate;
    }

    public String getCategory() {
        return category;
    }

    public String getStatus() {
        return status;
    }

    public String getOwner() {
        return owner;
    }

}
