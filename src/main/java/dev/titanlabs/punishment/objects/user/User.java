package dev.titanlabs.punishment.objects.user;

import dev.titanlabs.punishment.objects.punishment.Ban;

import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class User {
    private final UUID uuid;
    private final Map<String, Integer> trackPunishments;
    private final Set<Ban> manualBans;
    private final Set<Ban> manualMutes;
    private final Set<Ban> manualKicks;
    private final Set<Ban> manualWarnings;

    public User(UUID uuid, Map<String, Integer> trackPunishments, Set<Ban> manualBans, Set<Ban> manualMutes, Set<Ban> manualKicks, Set<Ban> manualWarnings) {
        this.uuid = uuid;
        this.trackPunishments = trackPunishments;
        this.manualBans = manualBans;
        this.manualMutes = manualMutes;
        this.manualKicks = manualKicks;
        this.manualWarnings = manualWarnings;
    }

    public UUID getUuid() {
        return this.uuid;
    }

    public Map<String, Integer> getTrackPunishments() {
        return this.trackPunishments;
    }

    public Set<Ban> getManualBans() {
        return this.manualBans;
    }

    public Set<Ban> getManualMutes() {
        return this.manualMutes;
    }

    public Set<Ban> getManualKicks() {
        return this.manualKicks;
    }

    public Set<Ban> getManualWarnings() {
        return this.manualWarnings;
    }
}
