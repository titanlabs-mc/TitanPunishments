package dev.titanlabs.punishment.storage.adapters;

import com.eatthepath.uuid.FastUUID;
import com.google.gson.*;
import dev.titanlabs.punishment.objects.punishments.Kick;
import me.hyfe.simplespigot.storage.adapter.Adapter;

import java.lang.reflect.Type;
import java.util.UUID;

public class KickAdapter implements Adapter<Kick> {

    @Override
    public Kick deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        UUID executor = FastUUID.parseUUID(jsonObject.get("executor").getAsString());
        UUID subject = FastUUID.parseUUID(jsonObject.get("subject").getAsString());
        String reason = jsonObject.get("reason").getAsString();
        long kickTime = jsonObject.get("kickTime").getAsLong();
        return new Kick(executor, subject, reason, kickTime);
    }

    @Override
    public JsonElement serialize(Kick kick, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("executor", FastUUID.toString(kick.getExecutor()));
        jsonObject.addProperty("subject", FastUUID.toString(kick.getSubject()));
        jsonObject.addProperty("reason", kick.getReason());
        jsonObject.addProperty("kickTime", kick.getPunishmentTime());
        return jsonObject;
    }
}
