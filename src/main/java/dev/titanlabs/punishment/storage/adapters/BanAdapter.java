package dev.titanlabs.punishment.storage.adapters;

import com.eatthepath.uuid.FastUUID;
import com.google.gson.*;
import dev.titanlabs.punishment.PunishmentEndReason;
import dev.titanlabs.punishment.objects.punishments.Ban;
import me.hyfe.simplespigot.storage.adapter.Adapter;

import java.lang.reflect.Type;
import java.util.UUID;

public class BanAdapter implements Adapter<Ban> {

    @Override
    public Ban deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        UUID executor = FastUUID.parseUUID(jsonObject.get("executor").getAsString());
        UUID subject = FastUUID.parseUUID(jsonObject.get("subject").getAsString());
        long banTime = jsonObject.get("banTime").getAsLong();
        boolean temporary = jsonObject.get("temporary").getAsBoolean();
        String reason = jsonObject.get("reason").getAsString();
        long length = jsonObject.get("length").getAsLong();
        PunishmentEndReason endReason = PunishmentEndReason.valueOf(jsonObject.get("punishmentEndReason").getAsString());

        return new Ban(executor, subject, reason, temporary, banTime, length, endReason);
    }

    @Override
    public JsonElement serialize(Ban ban, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("executor", FastUUID.toString(ban.getExecutor()));
        jsonObject.addProperty("subject", FastUUID.toString(ban.getSubject()));
        jsonObject.addProperty("banTime", ban.getBanTime());
        jsonObject.addProperty("temporary", ban.isTemporary());
        jsonObject.addProperty("reason", ban.getReason());
        jsonObject.addProperty("length", ban.getLength());
        jsonObject.addProperty("punishmentEndReason", String.valueOf(ban.getEndReason()));
        return jsonObject;
    }
}
