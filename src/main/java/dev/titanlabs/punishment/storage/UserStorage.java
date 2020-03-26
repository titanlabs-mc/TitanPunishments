package dev.titanlabs.punishment.storage;

import com.eatthepath.uuid.FastUUID;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import dev.titanlabs.punishment.PunishmentPlugin;
import dev.titanlabs.punishment.cache.IpCache;
import dev.titanlabs.punishment.objects.punishments.Ban;
import dev.titanlabs.punishment.objects.punishments.Kick;
import dev.titanlabs.punishment.objects.punishments.Mute;
import dev.titanlabs.punishment.objects.punishments.Warning;
import dev.titanlabs.punishment.objects.user.User;
import dev.titanlabs.punishment.storage.adapters.BanAdapter;
import dev.titanlabs.punishment.storage.adapters.KickAdapter;
import dev.titanlabs.punishment.storage.adapters.MuteAdapter;
import dev.titanlabs.punishment.storage.adapters.WarningAdapter;
import me.hyfe.simplespigot.json.Token;
import me.hyfe.simplespigot.json.TypeTokens;
import me.hyfe.simplespigot.storage.storage.Storage;
import me.hyfe.simplespigot.storage.storage.load.Deserializer;
import me.hyfe.simplespigot.storage.storage.load.Serializer;

import java.util.List;
import java.util.UUID;

public class UserStorage extends Storage<User> {
    private final IpCache ipCache;

    public UserStorage(PunishmentPlugin plugin) {
        super(plugin, factory -> factory.create(plugin.getConfigStore().commons().get("storageMethod"), path -> path.resolve("data").resolve("users")));
        this.ipCache = plugin.getIpCache();

        this.addAdapter(Ban.class, new BanAdapter());
        this.addAdapter(Kick.class, new KickAdapter());
        this.addAdapter(Mute.class, new MuteAdapter());
        this.addAdapter(Warning.class, new WarningAdapter());
        this.saveChanges();
    }

    @Override
    public Serializer<User> serializer() {
        return (user, json, gson) -> {
            System.out.println("1");
            json.addProperty("uuid", FastUUID.toString(user.getUuid()));
            System.out.println("2");
            json.addProperty("bans", gson.toJson(Lists.newArrayList(user.getBans())));
            System.out.println("3");
            System.out.println("active ban 1 ".concat(String.valueOf(user.getActiveBan())));
            json.addProperty("lastKnownActiveBan", gson.toJson(user.getActiveBan()));
            System.out.println("active ban 2 ".concat(String.valueOf(user.getActiveBan())));
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
            List<String> ipAddresses = gson.fromJson(json.get("ipAddresses").getAsString(), TypeTokens.findType());
            return new User(uuid, Sets.newHashSet(bans), lastKnownActiveBan, Sets.newHashSet(mutes), lastKnownActiveMute,
                    Sets.newHashSet(warnings), Sets.newHashSet(lastKnownActiveWarnings), Sets.newHashSet(kicks), Sets.newHashSet(this.ipCache.loadIps(ipAddresses)));
        };
    }
}
