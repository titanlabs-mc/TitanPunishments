package dev.titanlabs.punishment.storage;

import com.eatthepath.uuid.FastUUID;
import com.google.common.collect.Sets;
import dev.titanlabs.punishment.PunishmentPlugin;
import dev.titanlabs.punishment.objects.punishments.Ban;
import dev.titanlabs.punishment.objects.punishments.Kick;
import dev.titanlabs.punishment.objects.punishments.Mute;
import dev.titanlabs.punishment.objects.punishments.Warning;
import dev.titanlabs.punishment.objects.user.IpAddress;
import dev.titanlabs.punishment.objects.user.User;
import dev.titanlabs.punishment.storage.adapters.BanAdapter;
import me.hyfe.simplespigot.json.Token;
import me.hyfe.simplespigot.storage.storage.Storage;
import me.hyfe.simplespigot.storage.storage.load.Deserializer;
import me.hyfe.simplespigot.storage.storage.load.Serializer;

import java.util.List;
import java.util.UUID;

public class UserStorage extends Storage<User> {

    public UserStorage(PunishmentPlugin plugin) {
        super(plugin, factory -> factory.create(plugin.getConfigStore().commons().get("storageMethod"), path -> path.resolve("data").resolve("users")));

        this.addAdapter(Ban.class, new BanAdapter());
        this.saveChanges();
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
            List<Ban> bans = gson.fromJson(json.get("bans").getAsString(), new Token<List<Ban>>().type());
            Ban lastKnownActiveBan = gson.fromJson(json.get("lastKnownActiveBan").getAsString(), Ban.class);
            List<Mute> mutes = gson.fromJson(json.get("mutes").getAsString(), new Token<List<Mute>>().type());
            Mute lastKnownActiveMute = gson.fromJson(json.get("lastKnownActiveMute").getAsString(), Mute.class);
            List<Warning> warnings = gson.fromJson(json.get("warnings").getAsString(), new Token<List<Warning>>().type());
            List<Warning> lastKnownActiveWarnings = gson.fromJson(json.get("lastKnownActiveWarnings").getAsString(), new Token<List<Warning>>().type());
            List<Kick> kicks = gson.fromJson(json.get("kicks").getAsString(), new Token<List<Kick>>().type());
            List<IpAddress> ipAddresses = gson.fromJson(json.get("ipAddresses").getAsString(), new Token<List<IpAddress>>().type());
            return new User(uuid, Sets.newHashSet(bans), lastKnownActiveBan, Sets.newHashSet(mutes), lastKnownActiveMute,
                    Sets.newHashSet(warnings), Sets.newHashSet(lastKnownActiveWarnings), Sets.newHashSet(kicks), Sets.newHashSet(ipAddresses));
        };
    }
}
