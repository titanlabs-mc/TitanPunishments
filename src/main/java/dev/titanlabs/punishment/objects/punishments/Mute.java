package dev.titanlabs.punishment.objects.punishments;

import java.util.UUID;

public class Mute implements Punishment {
    private final UUID executor;
    private final UUID subject;
    private final String reason;
    private final long muteTime;
    private final boolean temporary;
    private long expiryTime;
    private long length;

    public Mute(UUID executor, UUID subject, String reason) {
        this.executor = executor;
        this.subject = subject;
        this.reason = reason;
        this.muteTime = System.currentTimeMillis();
        this.temporary = false;
    }

    public Mute(UUID executor, UUID subject, String reason, long length) {
        this.executor = executor;
        this.subject = subject;
        this.reason = reason;
        this.length = length;
        this.temporary = true;
        this.muteTime = System.currentTimeMillis();
        this.expiryTime = this.muteTime + length;
    }

    public Mute(UUID executor, UUID subject, String reason, boolean temporary, long muteTime, long length) {
        this.executor = executor;
        this.subject = subject;
        this.reason = reason;
        this.length = length;
        this.temporary = temporary;
        this.muteTime = muteTime;
        this.expiryTime = this.muteTime + length;
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
        return this.muteTime;
    }

    @Override
    public String getReason() {
        return this.reason;
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
