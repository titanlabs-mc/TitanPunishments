package dev.titanlabs.punishment.objects.punishment;

import java.time.ZonedDateTime;
import java.util.UUID;

public class Mute extends TimedPunishment {

    public Mute(UUID victim, UUID executor, String reason, ZonedDateTime dateTime, long length) {
        super(victim, executor, reason, dateTime, length);
    }
}
