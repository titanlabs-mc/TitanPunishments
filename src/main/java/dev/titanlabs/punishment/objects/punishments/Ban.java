package dev.titanlabs.punishment.objects.punishments;

import java.util.UUID;

public class Ban {
    private final UUID executor;
    private final UUID subject;
    private final long banTime;
    private final boolean temporary;
    private String reason;
    private long expiryTime;
    private long length;

    public Ban(UUID executor, UUID subject) {
        this.executor = executor;
        this.subject = subject;
        this.banTime = System.currentTimeMillis();
        this.temporary = false;
    }

    public Ban(UUID executor, UUID subject, String reason) {
        this.executor = executor;
        this.subject = subject;
        this.reason = reason;
        this.banTime = System.currentTimeMillis();
        this.temporary = false;
    }

    public Ban(UUID executor, UUID subject, String reason, long length) {
        this.executor = executor;
        this.subject = subject;
        this.reason = reason;
        this.length = length;
        this.temporary = true;
        this.banTime = System.currentTimeMillis();
        this.expiryTime = this.banTime + length;
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

    public long getBanTime() {
        return this.banTime;
    }

    public boolean isTemporary() {
        return this.temporary;
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
