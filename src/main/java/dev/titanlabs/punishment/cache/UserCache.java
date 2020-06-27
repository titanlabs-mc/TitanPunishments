package dev.titanlabs.punishment.cache;

import dev.titanlabs.punishment.objects.user.User;
import me.hyfe.simplespigot.cache.FutureCache;
import me.hyfe.simplespigot.plugin.SimplePlugin;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

public class UserCache extends FutureCache<UUID, User> {

    public UserCache(SimplePlugin plugin) {
        super(plugin);
    }

    public void loadOnline() {
        for (Player player : Bukkit.getOnlinePlayers()) {

        }
    }
}
