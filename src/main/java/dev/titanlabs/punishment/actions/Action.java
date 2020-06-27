package dev.titanlabs.punishment.actions;

public abstract class Action {
    private final String condition;
    private final String value;

    public Action(String condition, String value) {
        this.condition = condition;
        this.value = value;
    }
}
