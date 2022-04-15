package is.hi.midapp.Persistance.Entities;

public enum TaskCategory {
    HOUSEHOLD("Household chores"),
    SPORTS("Training and Competition"),
    SCHOOL("Schoolwork"),
    WORK("Work"),
    HOBBIES("Hobbies"),
    SELF_CARE("Self Care"),
    FAMILY("Family"),
    FRIENDS("Friends");

    private final String displayValue;

    TaskCategory(String displayValue) {
        this.displayValue = displayValue;
    }

    public String getDisplayValue() {
        return displayValue;
    }

    public String getEnumValue() {
        if (displayValue == "Household chores"){
            return "HOUSEHOLD";
        } else if (displayValue == "Training and Competition"){
            return "SPORTS";
        } else if (displayValue == "Schoolwork"){
            return "SCHOOL";
        } else if (displayValue == "Work"){
            return "WORK";
        } else if (displayValue == "Hobbies"){
            return "HOBBIES";
        } else if (displayValue == "Self Care"){
            return "SELF_CARE";
        } else if (displayValue == "Family"){
            return "FAMILY";
        } else if (displayValue == "Friends") {
            return "FRIENDS";
        } else return "";

    }

    @Override public String toString() {
        return displayValue;
    }

}
