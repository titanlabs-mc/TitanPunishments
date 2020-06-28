package dev.titanlabs.punishment.objects.punishment;

import java.time.ZonedDateTime;
import java.util.UUID;

public class Ban extends TimedPunishment {

    public Ban(UUID victim, UUID executor, String reason, ZonedDateTime dateTime, long length) {
        super(victim, executor, reason, dateTime, length);
    }
}
