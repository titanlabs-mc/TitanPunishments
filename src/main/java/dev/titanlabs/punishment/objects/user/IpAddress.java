package dev.titanlabs.punishment.objects.user;

import com.google.common.collect.Sets;

import java.util.Set;
import java.util.UUID;

public class IpAddress {
    private final String address;
    private Set<UUID> uniqueIds = Sets.newHashSet();

    public IpAddress(UUID uuid, String address) {
        this.address = address;
        this.uniqueIds.add(uuid);
    }

    public String getAddress() {
        return this.address;
    }

    public Set<UUID> getUniqueIds() {
        return this.uniqueIds;
    }

    public void addPlayer(UUID uuid) {
        this.uniqueIds.add(uuid);
    }
}
