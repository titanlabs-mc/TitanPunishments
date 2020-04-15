package dev.titanlabs.punishment.storage.adapters;

import com.eatthepath.uuid.FastUUID;
import com.google.gson.*;
import dev.titanlabs.punishment.objects.punishments.Mute;
import me.hyfe.simplespigot.storage.adapter.Adapter;

import java.lang.reflect.Type;
import java.util.UUID;

public class MuteAdapter implements Adapter<Mute> {

    @Override
    public Mute deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        UUID executor = FastUUID.parseUUID(jsonObject.get("executor").getAsString());
        UUID subject = FastUUID.parseUUID(jsonObject.get("subject").getAsString());
        long muteTime = jsonObject.get("muteTime").getAsLong();
        boolean temporary = jsonObject.get("temporary").getAsBoolean();
        String reason = jsonObject.get("reason").getAsString();
        long length = jsonObject.get("length").getAsLong();

        return new Mute(executor, subject, reason, temporary, muteTime, length);
    }

    @Override
    public JsonElement serialize(Mute mute, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("executor", FastUUID.toString(mute.getExecutor()));
        jsonObject.addProperty("subject", FastUUID.toString(mute.getSubject()));
        jsonObject.addProperty("muteTime", mute.getPunishmentTime());
        jsonObject.addProperty("temporary", mute.isTemporary());
        jsonObject.addProperty("reason", mute.getReason());
        jsonObject.addProperty("length", mute.getLength());
        return jsonObject;
    }
}
