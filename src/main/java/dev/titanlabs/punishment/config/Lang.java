package dev.titanlabs.punishment.config;

import com.google.common.collect.Maps;
import dev.titanlabs.punishment.PunishmentPlugin;
import me.hyfe.simplespigot.config.Config;
import me.hyfe.simplespigot.text.Replace;
import me.hyfe.simplespigot.text.Text;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class Lang {
    private final PunishmentPlugin plugin;
    private Map<String, List<String>> langContent = Maps.newHashMap();

    public Lang(PunishmentPlugin plugin) {
        this.plugin = plugin;
        this.load();
    }

    public void load() {
        Config config = this.plugin.getConfig("lang");
        for (String key : config.keys("", false)) {
            Object value = config.get(key);
            this.langContent.put(key, value instanceof String ? Collections.singletonList(config.get(key)) : config.get(key));
        }
    }

    public SubLang get(String id) {
        return this.get(id, null);
    }

    public SubLang get(String id, Replace replace) {
        List<String> requested = this.langContent.get(id);

        if (requested == null || requested.isEmpty()) {
            System.out.println("^^^^^^^^^^ -[TitanPunishment]- ^^^^^^^^^^");
            System.out.println(" ");
            System.out.println("Missing the configuration value '".concat(id).concat("', located in the file 'lang.yml'"));
            System.out.println(" ");
            System.out.println("^^^^^^^^^^ -[TitanPunishment]- ^^^^^^^^^^");
            return null;
        }
        return new SubLang(Text.modify(requested, replace));
    }

    public class SubLang {
        private final List<String> message;

        public SubLang(List<String> message) {
            this.message = message;
        }

        public List<String> list() {
            return this.message;
        }

        public String compatibleString() {
            StringBuilder stringBuilder = new StringBuilder();
            for (String line : this.message) {
                stringBuilder.append(line.concat("\n"));
            }
            return stringBuilder.toString();
        }

        public void to(String permission) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                if (player.hasPermission(permission)) {
                    Text.sendMessage(player, this.compatibleString());
                }
            }
        }

        public void to(CommandSender sender) {
            Text.sendMessage(sender, this.compatibleString());
        }
    }
}
