package dev.titanlabs.punishment.service;

import dev.titanlabs.punishment.PunishmentPlugin;
import dev.titanlabs.punishment.config.Lang;

public class Time {
    private static Lang lang;

    public Time(PunishmentPlugin plugin) {
        lang = plugin.getLang();
    }

    public static long parseTimeInput(String input) {
        if (input.endsWith("y") || input.endsWith("years")) {
            int years = Integer.parseInt(input.split("y")[0]);
            return years * 365 * 24 * 60 * 60 * 1000;
        }
        if (input.endsWith("mo") || input.endsWith("months")) {
            int months = Integer.parseInt(input.split("mo")[0]);
            return months * 30 * 24 * 60 * 60 * 1000;
        }
        if (input.endsWith("d") || input.endsWith("days")) {
            int days = Integer.parseInt(input.split("d")[0]);
            return days * 24 * 60 * 60 * 1000;
        }
        if (input.endsWith("h") || input.endsWith("hours")) {
            int hours = Integer.parseInt(input.split("h")[0]);
            return hours * 60 * 60 * 1000;
        }
        if (input.endsWith("m") || input.endsWith("minutes")) {
            int minutes = Integer.parseInt(input.split("m")[0]);
            return minutes * 60 * 1000;
        }
        if (input.endsWith("s") || input.endsWith("seconds")) {
            int seconds = Integer.parseInt(input.split("s")[0]);
            return seconds * 1000;
        }
        return 0;
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
