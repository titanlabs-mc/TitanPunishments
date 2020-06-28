package dev.titanlabs.punishment.storage;

import com.google.common.collect.Sets;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import dev.titanlabs.punishment.objects.punishment.Ban;
import dev.titanlabs.punishment.objects.punishment.Punishment;
import dev.titanlabs.punishment.objects.user.User;
import me.hyfe.simplespigot.json.TypeTokens;
import me.hyfe.simplespigot.plugin.SimplePlugin;
import me.hyfe.simplespigot.storage.storage.Storage;
import me.hyfe.simplespigot.storage.storage.load.Deserializer;
import me.hyfe.simplespigot.storage.storage.load.Serializer;
import me.hyfe.simplespigot.uuid.FastUUID;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class UserStorage extends Storage<User> {

    public UserStorage(SimplePlugin plugin) {
        super(plugin, factory -> factory.create(plugin.getConfigStore().commons().get("storage-type"), path -> path.resolve("users"), ""));
    }

    @Override
    public Serializer<User> serializer() {
        return (user, json, gson) -> {
            json.addProperty("uuid", FastUUID.toString(user.getUuid()));
            json.addProperty("track-punishments", gson.toJson(user.getTrackPunishments()));
            json.addProperty("manual-bans", gson.toJson(user.getManualBans()));
            json.addProperty("manual-mutes", gson.toJson(user.getManualMutes()));
            json.addProperty("manual-kicks", gson.toJson(user.getManualKicks()));
            json.addProperty("manual-warnings", gson.toJson(user.getManualWarnings()));
            return json;
        };
    }

    @Override
    public Deserializer<User> deserializer() {
        return (json, gson) -> {
            UUID uuid = FastUUID.parseUUID(json.get("uuid").getAsString());
            Map<String, Integer> trackPunishments = gson.fromJson(json.get("track-punishments").getAsString(), TypeTokens.findType());
            Set<Ban> manualBans = this.interpret(json, gson, "manual-bans");
            Set<Ban> manualMutes = this.interpret(json, gson, "manual-mutes");
            Set<Ban> manualKicks = this.interpret(json, gson, "manual-kicks");
            Set<Ban> manualWarnings = this.interpret(json, gson, "manual-warnings");
            return new User(uuid, trackPunishments, manualBans, manualMutes, manualKicks, manualWarnings);
        };
    }

    public <T extends Punishment> Set<T> interpret(JsonObject json, Gson gson, String type) {
        List<T> punishments = gson.fromJson(json.get(type).getAsString(), new TypeToken<List<T>>(){}.getType());
        return Sets.newHashSet(punishments);
    }
}
