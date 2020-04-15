package dev.titanlabs.punishment.objects.punishments;

import java.util.UUID;

public class Kick implements Punishment {
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

    @Override
    public UUID getExecutor() {
        return this.executor;
    }

    @Override
    public boolean isExecutorConsole() {
        return this.executor == null;
    }

    @Override
    public UUID getSubject() {
        return this.subject;
    }

    @Override
    public long getPunishmentTime() {
        return this.kickTime;
    }

    @Override
    public String getReason() {
        return this.reason;
    }
}
