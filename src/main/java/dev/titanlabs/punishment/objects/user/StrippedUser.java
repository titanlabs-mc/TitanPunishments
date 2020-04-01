package dev.titanlabs.punishment.objects.user;

import dev.titanlabs.punishment.objects.punishments.Ban;

public class StrippedUser {
    private final Ban ban;

    public StrippedUser(Ban ban) {
        this.ban = ban;
    }

    public Ban getBan() {
        return this.ban;
    }

    public boolean isBanned() {
        return this.ban != null && !this.ban.isExpired();
    }
}
