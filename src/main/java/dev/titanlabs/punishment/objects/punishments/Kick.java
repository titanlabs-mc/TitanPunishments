package dev.titanlabs.punishment.objects.punishments;

import java.util.UUID;

public class Kick {
    private final UUID executor;
    private final UUID subject;
    private final long kickTime;
    private String reason;

    public Kick(UUID executor, UUID subject) {
        this.executor = executor;
        this.subject = subject;
        this.kickTime = System.currentTimeMillis();
    }

    public Kick(UUID executor, UUID subject, String reason) {
        this.executor = executor;
        this.subject = subject;
        this.reason = reason;
        this.kickTime = System.currentTimeMillis();
    }

    public Kick(UUID executor, UUID subject, String reason, long kickTime) {
        this.executor = executor;
        this.subject = subject;
        this.reason = reason;
        this.kickTime = kickTime;
    }

    public UUID getExecutor() {
        return this.executor;
    }

    public boolean isConsoleExecutor() {
        return this.executor == null;
    }

    public UUID getSubject() {
        return this.subject;
    }

    public long getKickTime() {
        return this.kickTime;
    }

    public String getReason() {
        return this.reason;
    }
}
