package dev.titanlabs.punishment.service;

import dev.titanlabs.punishment.PunishmentPlugin;
import dev.titanlabs.punishment.config.Lang;

public class Time {
    private static Lang lang;

    public Time(PunishmentPlugin plugin) {
        lang = plugin.getLang();
    }

    public static String format(long milliseconds) {
        boolean containsPrevious = false;
        long initialSeconds = milliseconds / 60;
        long years = initialSeconds / 31536000;
        long months = initialSeconds % 31536000 / 2592000; // Months calculated with 30 days.
        long weeks = initialSeconds % 2592000 / 604800;
        long days = initialSeconds % 604800 / 86400;
        long hours = initialSeconds % 86400 / 3600;
        long minutes = initialSeconds % 3600 / 60;
        long seconds = initialSeconds % 60;
        StringBuilder builder = new StringBuilder();
        if (years >= 1) { // 1 year or more
            builder.append(String.format("%d ".concat(lang.get(years == 1 ? "year" : "years").compatibleString()), years));
            containsPrevious = true;
        }
        if (months >= 1) { // 1 month or more
            builder = run(builder, containsPrevious, months, "month", "months");
            containsPrevious = true;
        }
        if (weeks >= 1) { // 1 week or more
            builder = run(builder, containsPrevious, weeks, "week", "weeks");
            containsPrevious = true;
        }
        if (days >= 1) { // 1 day or more
            builder = run(builder, containsPrevious, days, "day", "days");
            containsPrevious = true;
        }
        if (hours >= 1) { // 1 hour or more
            builder = run(builder, containsPrevious, hours, "hour", "hours");
            containsPrevious = true;
        }
        if (minutes >= 1) { // 1 minute or more
            builder = run(builder, containsPrevious, minutes, "minute", "minutes");
            containsPrevious = true;
        }
        if (seconds >= 1) { // 1 second or more
            builder = run(builder, containsPrevious, seconds, "second", "seconds");
        }
        return builder.toString();
    }

    private static StringBuilder run(StringBuilder builder, boolean containsPrevious, long variable, String singular, String plural) {
        return builder.append(String.format((containsPrevious ? ", " : "").concat("%d ".concat(lang.get(variable == 1 ? singular : plural).compatibleString())), variable));
    }

    private static StringBuilder addFormat(StringBuilder builder, boolean containsPrevious) {
        if (containsPrevious) {
            return builder.append(", ");
        }
        return builder;
    }
}
