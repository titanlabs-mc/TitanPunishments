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
        System.out.println(this.ban == null);
        System.out.println(this.ban.isExpired());
        return this.ban != null && !this.ban.isExpired();
    }
}
