package dev.titanlabs.punishment.objects.punishments;

import dev.titanlabs.punishment.PunishmentEndReason;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class Ban implements Punishment {
    private final UUID executor;
    private final UUID subject;
    private final long banTime;
    private final boolean temporary;
    private String reason;
    private long expiryTime;
    private long length;
    private PunishmentEndReason endReason;

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

    public Ban(UUID executor, UUID subject, long length) {
        this.executor = executor;
        this.subject = subject;
        this.length = length;
        this.temporary = true;
        this.banTime = System.currentTimeMillis();
        this.expiryTime = this.banTime + length;
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


    public Ban(UUID executor, UUID subject, String reason, boolean temporary, long banTime, long length, PunishmentEndReason endReason) {
        this.executor = executor;
        this.subject = subject;
        this.reason = reason;
        this.length = length;
        this.temporary = temporary;
        this.banTime = banTime;
        this.endReason = endReason;
        this.expiryTime = this.banTime + length;
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
    public String getReason() {
        return this.reason;
    }

    @Override
    public long getPunishmentTime() {
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
        if (!this.temporary) {
            return false;
        }
        return System.currentTimeMillis() > this.expiryTime;
    }

    public PunishmentEndReason getEndReason() {
        return this.endReason;
    }

    public void setEndReason(PunishmentEndReason endReason) {
        this.endReason = endReason;
    }

    public void end(PunishmentEndReason punishmentEndReason) {
        this.endReason = punishmentEndReason;
        this.expiryTime = System.currentTimeMillis();
    }

    public long getRemainingTime(TimeUnit unit) {
        long remainingTimeMillis = this.banTime + this.length;
        switch (unit) {
            case SECONDS:
                return remainingTimeMillis / 1000;
            case MINUTES:
                return (remainingTimeMillis / 1000) / 60;
            default:
                return remainingTimeMillis;
        }
    }
}
