package dev.titanlabs.punishment.objects.punishment;

import java.time.ZonedDateTime;
import java.util.UUID;

public abstract class TimedPunishment extends Punishment {
    private final ZonedDateTime dateTime;
    private final long length; // in millis

    public TimedPunishment(UUID victim, UUID executor, String reason, ZonedDateTime dateTime, long length) {
        super(victim, executor, reason);
        this.dateTime = dateTime;
        this.length = length;
    }

    public ZonedDateTime getDateTime() {
        return this.dateTime;
    }

    public long getLength() {
        return this.length;
    }

    public boolean isPermanent() {
        return this.length < 0;
    }
}
