package dev.titanlabs.punishment.actions;

public abstract class Action {
    private final String type;
    private final String condition;
    private final String value;

    public Action(String type, String condition, String value) {
        this.type = type;
        this.condition = condition;
        this.value = value;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder("[").append(this.type).append("]");
        if (this.condition != null) {
            builder.append("(".concat(this.condition).concat(")"));
        }
        if (this.value != null) {
            builder.append("{".concat(this.value).concat("}"));
        }
        return builder.toString();
    }
}
