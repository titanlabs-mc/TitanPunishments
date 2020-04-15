package dev.titanlabs.punishment.storage.adapters;

import com.eatthepath.uuid.FastUUID;
import com.google.gson.*;
import dev.titanlabs.punishment.objects.punishments.Warning;
import me.hyfe.simplespigot.storage.adapter.Adapter;

import java.lang.reflect.Type;
import java.util.UUID;

public class WarningAdapter implements Adapter<Warning> {

    @Override
    public Warning deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        UUID executor = FastUUID.parseUUID(jsonObject.get("executor").getAsString());
        UUID subject = FastUUID.parseUUID(jsonObject.get("subject").getAsString());
        String reason = jsonObject.get("reason").getAsString();
        long warningTime = jsonObject.get("warningTime").getAsLong();
        long length = jsonObject.get("length").getAsLong();
        return new Warning(executor, subject, reason, warningTime, length);
    }

    @Override
    public JsonElement serialize(Warning warning, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("executor", FastUUID.toString(warning.getExecutor()));
        jsonObject.addProperty("subject", FastUUID.toString(warning.getSubject()));
        jsonObject.addProperty("reason", warning.getReason());
        jsonObject.addProperty("warningTime", warning.getPunishmentTime());
        jsonObject.addProperty("length", warning.getLength());
        return jsonObject;
    }
}
