package dev.titanlabs.punishment.objects.punishments;

import java.util.UUID;

public class Warning {
    private final UUID executor;
    private final UUID subject;
    private final String reason;
    private final long warningTime;
    private long expiryTime;
    private long length;

    public Warning(UUID executor, UUID subject, String reason) {
        this.executor = executor;
        this.subject = subject;
        this.reason = reason;
        this.warningTime = System.currentTimeMillis();
    }

    public Warning(UUID executor, UUID subject, String reason, long length) {
        this.executor = executor;
        this.subject = subject;
        this.reason = reason;
        this.length = length;
        this.warningTime = System.currentTimeMillis();
        this.expiryTime = this.warningTime + length;
    }

    public Warning(UUID executor, UUID subject, String reason, long length, long warningTime) {
        this.executor = executor;
        this.subject = subject;
        this.reason = reason;
        this.length = length;
        this.warningTime = warningTime;
        this.expiryTime = this.warningTime + length;
    }

    public UUID getExecutor() {
        return this.executor;
    }

    public UUID getSubject() {
        return this.subject;
    }

    public String getReason() {
        return this.reason;
    }

    public long getWarningTime() {
        return this.warningTime;
    }

    public long getExpiryTime() {
        return this.expiryTime;
    }

    public long getLength() {
        return this.length;
    }

    public boolean isExpired() {
        return System.currentTimeMillis() > this.expiryTime;
    }
}
