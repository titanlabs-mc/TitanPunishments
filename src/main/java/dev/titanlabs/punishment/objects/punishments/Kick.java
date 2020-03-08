package dev.titanlabs.punishment.objects.punishments;

import java.util.UUID;

public class Kick {
    private final UUID executor;
    private final UUID subject;
    private final long warningTime;
    private String reason;

    public Kick(UUID executor, UUID subject) {
        this.executor = executor;
        this.subject = subject;
        this.warningTime = System.currentTimeMillis();
    }

    public Kick(UUID executor, UUID subject, String reason) {
        this.executor = executor;
        this.subject = subject;
        this.reason = reason;
        this.warningTime = System.currentTimeMillis();
    }

    public UUID getExecutor() {
        return this.executor;
    }

    public UUID getSubject() {
        return this.subject;
    }

    public long getWarningTime() {
        return this.warningTime;
    }

    public String getReason() {
        return this.reason;
    }
}
