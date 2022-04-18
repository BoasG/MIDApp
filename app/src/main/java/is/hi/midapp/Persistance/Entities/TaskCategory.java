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
        if (displayValue.equals("Household chores")){
            return "HOUSEHOLD";
        } else if (displayValue.equals("Training and Competition")){
            return "SPORTS";
        } else if (displayValue.equals("Schoolwork")){
            return "SCHOOL";
        } else if (displayValue.equals("Work")){
            return "WORK";
        } else if (displayValue.equals("Hobbies")){
            return "HOBBIES";
        } else if (displayValue.equals("Self Care")){
            return "SELF_CARE";
        } else if (displayValue.equals("Family")){
            return "FAMILY";
        } else if (displayValue.equals("Friends")) {
            return "FRIENDS";
        } else return "";

    }

    @Override public String toString() {
        return displayValue;
    }

}
