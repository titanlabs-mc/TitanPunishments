package dev.titanlabs.punishment.objects.punishments;

import java.util.UUID;

public interface Punishment {

    UUID getExecutor();

    boolean isExecutorConsole();

    UUID getSubject();

    long getPunishmentTime();

    String getReason();
}
