package dev.titanlabs.punishment.objects.user;

import dev.titanlabs.punishment.objects.punishment.Mute;

import java.util.Optional;
import java.util.UUID;

public interface User {

    UUID getUuid();

    boolean isLimited();

    boolean isMuted();

    Optional<Mute> getCurrentMute();
}
