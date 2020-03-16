package dev.titanlabs.punishment.service.punishment;

public class PunishmentUtils {

    public static boolean silent(String reason) {
        return reason.endsWith("-s");
    }

    public static String fixSilent(String reason) {
        return reason.substring(0, reason.length() - 2);
    }
}
