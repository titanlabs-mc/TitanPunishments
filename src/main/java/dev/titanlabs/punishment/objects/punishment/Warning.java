package dev.titanlabs.punishment.objects.punishment;

import java.util.UUID;

public class Warning extends Punishment {

    public Warning(UUID victim, UUID executor, String reason) {
        super(victim, executor, reason);
    }
}
