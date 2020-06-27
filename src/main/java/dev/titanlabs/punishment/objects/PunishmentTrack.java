package dev.titanlabs.punishment.objects;

import dev.titanlabs.punishment.actions.Action;

import java.util.Map;
import java.util.Set;

public class PunishmentTrack {
    private String name;
    private Map<Integer, Set<Action>> actionsMap;

    public PunishmentTrack(String name, Map<Integer, Set<Action>> actionsMap) {
        this.name = name;
        this.actionsMap = actionsMap;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Action> getActions(int violations) {
        return this.actionsMap.get(violations);
    }

    public void setActionsMap(Map<Integer, Set<Action>> actionsMap) {
        this.actionsMap = actionsMap;
    }
}
