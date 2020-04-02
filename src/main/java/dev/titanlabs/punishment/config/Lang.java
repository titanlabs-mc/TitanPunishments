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
    private final Config config;
    private Map<String, List<String>> langContent = Maps.newHashMap();

    public Lang(PunishmentPlugin plugin) {
        this.plugin = plugin;
        this.config = plugin.getConfig("lang");
    }

    public SubLang get(String id) {
        return this.get(id, null);
    }

    public SubLang get(String id, Replace replace) {
        Object requested = this.config.get(id);
        List<String> value = null;
        if (requested instanceof String) {
            value = Collections.singletonList(this.config.string(id));
        } else if (requested instanceof List) {
            value = this.config.stringList(id);
        }

        if (value == null || value.isEmpty()) {
            System.out.println("^^^^^^^^^^ -[TitanPunishment]- ^^^^^^^^^^");
            System.out.println(" ");
            System.out.println("Missing the configuration value '".concat(id).concat("', located in the file 'lang.yml'"));
            System.out.println(" ");
            System.out.println("^^^^^^^^^^ -[TitanPunishment]- ^^^^^^^^^^");
            return null;
        }
        return new SubLang(Text.modify(value, replace));
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
