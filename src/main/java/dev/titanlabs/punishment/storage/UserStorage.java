package dev.titanlabs.punishment.storage;

import com.eatthepath.uuid.FastUUID;
import dev.titanlabs.punishment.Punishment;
import dev.titanlabs.punishment.objects.punishments.Ban;
import dev.titanlabs.punishment.objects.punishments.Kick;
import dev.titanlabs.punishment.objects.punishments.Mute;
import dev.titanlabs.punishment.objects.punishments.Warning;
import dev.titanlabs.punishment.objects.user.IpAddress;
import dev.titanlabs.punishment.objects.user.User;
import me.hyfe.simplespigot.json.TypeTokens;
import me.hyfe.simplespigot.storage.storage.Storage;
import me.hyfe.simplespigot.storage.storage.load.Deserializer;
import me.hyfe.simplespigot.storage.storage.load.Serializer;

import java.util.Set;
import java.util.UUID;

public class UserStorage extends Storage<User> {

    public UserStorage(Punishment plugin) {
        super(plugin, factory -> factory.create(plugin.getConfigStore().commons().get("storageMethod"), path -> path.resolve("data").resolve("users")));
    }

    @Override
    public Serializer<User> serializer() {
        return (user, json, gson) -> {
            json.addProperty("uuid", FastUUID.toString(user.getUuid()));
            json.addProperty("bans", gson.toJson(user.getBans()));
            json.addProperty("lastKnownActiveBan", gson.toJson(user.getActiveBan()));
            json.addProperty("mutes", gson.toJson(user.getMutes()));
            json.addProperty("lastKnownActiveMute", gson.toJson(user.getActiveMute()));
            json.addProperty("warnings", gson.toJson(user.getWarnings()));
            json.addProperty("lastKnownActiveWarnings", gson.toJson(user.getActiveWarnings()));
            json.addProperty("kicks", gson.toJson(user.getKicks()));
            json.addProperty("ipAddresses", gson.toJson(user.getIpAddresses()));
            return json;
        };
    }

    @Override
    public Deserializer<User> deserializer() {
        return (json, gson) -> {
            UUID uuid = FastUUID.parseUUID(json.get("uuid").getAsString());
            Set<Ban> bans = gson.fromJson("bans", TypeTokens.findType());
            Ban lastKnownActiveBan = gson.fromJson("lastKnownActiveBan", TypeTokens.findType());
            Set<Mute> mutes = gson.fromJson("mutes", TypeTokens.findType());
            Mute lastKnownActiveMute = gson.fromJson("lastKnownActiveMute", TypeTokens.findType());
            Set<Warning> warnings = gson.fromJson("warnings", TypeTokens.findType());
            Set<Warning> lastKnownActiveWarnings = gson.fromJson("lastKnownActiveWarnings", TypeTokens.findType());
            Set<Kick> kicks = gson.fromJson("kicks", TypeTokens.findType());
            Set<IpAddress> ipAddresses = gson.fromJson("ipAddresses", TypeTokens.findType());
            return new User(uuid, bans, lastKnownActiveBan, mutes, lastKnownActiveMute, warnings, lastKnownActiveWarnings, kicks, ipAddresses);
        };
    }
}
