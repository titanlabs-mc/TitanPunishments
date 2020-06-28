package dev.titanlabs.punishment.objects.punishment;

import java.util.UUID;

public abstract class Punishment {
    private final UUID victim;
    private final UUID executor;
    private final String reason;

    public Punishment(UUID victim, UUID executor, String reason) {
        this.victim = victim;
        this.executor = executor;
        this.reason = reason;
    }

    public UUID getVictim() {
        return this.victim;
    }

    public UUID getExecutor() {
        return this.executor;
    }

    public String getReason() {
        return this.reason;
    }
}
