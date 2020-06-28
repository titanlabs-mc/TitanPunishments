package dev.titanlabs.punishment.manager;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import dev.titanlabs.punishment.PunishmentPlugin;
import dev.titanlabs.punishment.actions.Action;
import dev.titanlabs.punishment.objects.PunishmentTrack;
import lombok.SneakyThrows;
import me.hyfe.simplespigot.cache.FutureCache;
import me.hyfe.simplespigot.config.Config;
import me.hyfe.simplespigot.config.ConfigLoader;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

public class TrackManager extends FutureCache<String, PunishmentTrack> {
    private final PunishmentPlugin plugin;
    private final Path tracksPath;
    private final ActionManager actionManager;

    public TrackManager(PunishmentPlugin plugin) {
        super(plugin);
        this.plugin = plugin;
        this.actionManager = plugin.getActionManager();
        this.tracksPath = plugin.getDataFolder().toPath().resolve("tracks");
    }

    public void modifyTrack(String identifier, Consumer<PunishmentTrack> trackConsumer) {
        this.get(identifier).thenAccept(optionalTrack -> {
            optionalTrack.ifPresent(trackConsumer);
        });
    }

    public void createTrack(String identifier, PunishmentTrack track) {
        this.set(identifier, track);
    }

    @SneakyThrows
    public void load() {
        if (!this.tracksPath.toFile().exists()) {
            this.makeDefaultFiles();
        }
        Files.walk(this.tracksPath).map(Path::toFile).map(file -> new Config(this.plugin, file, true)).forEach(config -> {
            String name = config.string("name");
            String identifier = config.string("identifier");
            Map<Integer, Set<Action>> violationActions = Maps.newLinkedHashMap();
            ConfigLoader.reader(config).keyLoop(false, stringViolation -> {
                int violation = Integer.parseInt(stringViolation);
                if (violation == 0) {
                    return;
                }
                Set<Action> singleActions = Sets.newLinkedHashSet();
                for (String stringAction : config.stringList(stringViolation)) {
                    Action action = this.actionManager.parse(stringAction);
                    if (action != null) {
                        singleActions.add(action);
                    }
                    violationActions.put(violation, singleActions);
                }
                this.set(identifier, new PunishmentTrack(identifier, name, violationActions));
            });
        });
    }

    @SneakyThrows
    public void save(String identifier, boolean invalidate) {
        this.get(identifier).thenAccept(optionalTrack -> {
            optionalTrack.ifPresent(track -> {
                File file = this.tracksPath.resolve(identifier).toFile();
                YamlConfiguration yamlConfiguration = new YamlConfiguration();
                yamlConfiguration.set("identifier", track.getIdentifier());
                yamlConfiguration.set("name", track.getName());
                for (Map.Entry<Integer, Set<Action>> entry : track.getActionsMap().entrySet()) {
                    List<String> actions = Lists.newArrayList();
                    for (Action action : entry.getValue()) {
                        actions.add(action.toString());
                    }
                    yamlConfiguration.set(String.valueOf(entry.getKey()), actions);
                }
                yamlConfiguration.save(file);
                if (invalidate) {
                    this.invalidate(identifier);
                }
            });
        });
    }

    public void saveAll(boolean invalidate) {
        for (String identifier : this.getSubCache().asMap().keySet()) {
            this.save(identifier, invalidate);
        }
    }

    private void makeDefaultFiles() {
        this.plugin.saveResource("tracks/spam.yml", false);
        this.plugin.saveResource("tracks/cheating.yml", false);
        this.plugin.saveResource("tracks/racism.yml", false);
        this.plugin.saveResource("tracks/advertising.yml", false);
        this.plugin.saveResource("tracks/swearing.yml", false);
    }
}
