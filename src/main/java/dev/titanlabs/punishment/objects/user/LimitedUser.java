package dev.titanlabs.punishment.objects.user;

import dev.titanlabs.punishment.objects.punishment.Mute;

import java.util.Optional;
import java.util.UUID;

public class LimitedUser implements User {
    private final UUID uuid;
    private final Mute mute;

    public LimitedUser(UUID uuid, Mute mute) {
        this.uuid = uuid;
        this.mute = mute;
    }

    @Override
    public UUID getUuid() {
        return this.uuid;
    }

    @Override
    public boolean isLimited() {
        return true;
    }

    @Override
    public boolean isMuted() {
        return this.mute == null || !this.mute.isActive();
    }

    @Override
    public Optional<Mute> getCurrentMute() {
        return Optional.ofNullable(this.mute);
    }
}
