package is.hi.midapp.Persistance.Entities;

public enum TaskStatus {
    NOT_STARTED("Not Started"),
    IN_PROGRESS("In progress"),
    COMPLETED("Completed");

    private final String displayValue;

    TaskStatus(String displayValue) {
        this.displayValue = displayValue;
    }

    public String getDisplayValue() {
        return displayValue;
    }
}