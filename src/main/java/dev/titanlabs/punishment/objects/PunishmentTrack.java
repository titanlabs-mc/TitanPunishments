package dev.titanlabs.punishment.objects;

import dev.titanlabs.punishment.actions.Action;

import java.util.Map;
import java.util.Set;

public class PunishmentTrack {
    private final String identifier;
    private String name;
    private Map<Integer, Set<Action>> actionsMap;

    public PunishmentTrack(String identifier, String name, Map<Integer, Set<Action>> actionsMap) {
        this.identifier = identifier;
        this.name = name;
        this.actionsMap = actionsMap;
    }

    public String getIdentifier() {
        return this.identifier;
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

    public Map<Integer, Set<Action>> getActionsMap() {
        return this.actionsMap;
    }

    public void setActionsMap(Map<Integer, Set<Action>> actionsMap) {
        this.actionsMap = actionsMap;
    }
}
