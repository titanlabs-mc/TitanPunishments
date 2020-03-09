package dev.titanlabs.punishment.objects.user;

import com.google.common.collect.Sets;
import dev.titanlabs.punishment.objects.punishments.Ban;
import dev.titanlabs.punishment.objects.punishments.Kick;
import dev.titanlabs.punishment.objects.punishments.Mute;
import dev.titanlabs.punishment.objects.punishments.Warning;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Set;
import java.util.UUID;

public class User {
    private final UUID uuid;
    private final Player player;
    private Set<Ban> bans = Sets.newHashSet();
    private Ban activeBan;
    private Set<Mute> mutes = Sets.newHashSet();
    private Mute activeMute;
    private Set<Warning> warnings = Sets.newHashSet();
    private Set<Warning> activeWarnings = Sets.newHashSet();
    private Set<Kick> kicks = Sets.newHashSet();
    private IpAddress ipAddress;
    private Set<IpAddress> ipAddresses = Sets.newHashSet();

    public User(UUID uuid) {
        this.player = Bukkit.getPlayer(uuid);
        this.uuid = uuid;
    }

    public Player getPlayer() {
        return this.player;
    }

    public UUID getUuid() {
        return this.uuid;
    }

    public Set<Ban> getBans() {
        return this.bans;
    }

    public Ban getActiveBan() {
        return this.activeBan;
    }

    public void ban(Ban ban) {
        this.activeBan = ban;
        this.bans.add(ban);
    }

    public boolean isBanned() {
        return !(this.activeBan == null || this.activeBan.isExpired());
    }

    public Set<Mute> getMutes() {
        return this.mutes;
    }

    public Mute getActiveMute() {
        return this.activeMute;
    }

    public void mute(Mute mute) {
        this.activeMute = mute;
        this.mutes.add(mute);
    }

    public boolean isMuted() {
        return !(this.activeMute == null || this.activeMute.isExpired());
    }

    public Set<Warning> getWarnings() {
        return this.warnings;
    }

    public Set<Warning> getActiveWarnings() {
        return this.activeWarnings;
    }

    public void warn(Warning warning) {
        this.activeWarnings.add(warning);
        this.warnings.add(warning);
    }

    public Set<Kick> getKicks() {
        return this.kicks;
    }

    public void kick(Kick kick) {
        this.kicks.add(kick);
    }

    public void setIpAddress(IpAddress ipAddress) {
        this.ipAddress = ipAddress;
    }

    public IpAddress getIpAddress() {
        return this.ipAddress;
    }

    public Set<IpAddress> getIpAddresses() {
        return this.ipAddresses;
    }

    public void setIpAddresses(Set<IpAddress> ipAddresses) {
        this.ipAddresses = ipAddresses;
    }
}
