package dev.titanlabs.punishment.objects.punishment;

import java.util.UUID;

public class Kick extends Punishment {

    public Kick(UUID victim, UUID executor, String reason) {
        super(victim, executor, reason);
    }
}
