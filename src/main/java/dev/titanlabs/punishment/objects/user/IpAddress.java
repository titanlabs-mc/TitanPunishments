package dev.titanlabs.punishment.objects.user;

import com.google.common.collect.Sets;

import java.util.Set;
import java.util.UUID;

public class IpAddress {
    private final String address;
    private Set<UUID> onlineUniqueIds = Sets.newHashSet();
    private Set<UUID> uniqueIds = Sets.newHashSet();

    public IpAddress(String address, UUID uuid) {
        this.address = address;
        this.uniqueIds.add(uuid);
    }

    public String getAddress() {
        return this.address;
    }

    public Set<UUID> getUniqueIds() {
        return this.uniqueIds;
    }

    public boolean hasOnlinePlayers() {
        return !this.onlineUniqueIds.isEmpty();
    }

    public boolean isOnIp(UUID uuid) {
        return this.uniqueIds.contains(uuid);
    }

    public boolean isOnline(UUID uuid) {
        return this.onlineUniqueIds.contains(uuid);
    }

    public void registerOnline(UUID uuid) {
        this.uniqueIds.add(uuid);
        this.onlineUniqueIds.add(uuid);
    }

    public void registerOffline(UUID uuid) {
        this.onlineUniqueIds.remove(uuid);
    }
}
