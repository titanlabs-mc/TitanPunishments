package dev.titanlabs.punishment.manager;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import dev.titanlabs.punishment.PunishmentPlugin;
import dev.titanlabs.punishment.actions.Action;
import dev.titanlabs.punishment.actions.ActionManager;
import dev.titanlabs.punishment.objects.PunishmentTrack;
import lombok.SneakyThrows;
import me.hyfe.simplespigot.config.Config;
import me.hyfe.simplespigot.config.ConfigLoader;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;

public class TrackManager {
    private final PunishmentPlugin plugin;
    private final Path tracksPath;
    private final ActionManager actionManager;
    private Map<String, PunishmentTrack> trackNames = Maps.newHashMap();

    public TrackManager(PunishmentPlugin plugin) {
        this.plugin = plugin;
        this.actionManager = plugin.getActionManager();
        this.tracksPath = plugin.getDataFolder().toPath().resolve("tracks");
    }

    public Optional<PunishmentTrack> get(String name) {
        return Optional.ofNullable(this.trackNames.get(name));
    }

    public void modifyTrack(String name, Consumer<PunishmentTrack> track) {
        PunishmentTrack requestedTrack = this.trackNames.get(name);
        if (requestedTrack != null) {
            track.accept(requestedTrack);
        }
    }

    public void createTrack(String name, PunishmentTrack track) {
        this.trackNames.put(name, track);
    }

    @SneakyThrows
    public void load() {
        if (!this.tracksPath.toFile().exists()) {
            this.makeDefaultFiles();
        }
        Files.walk(this.tracksPath).map(Path::toFile).map(file -> new Config(this.plugin, file, true)).forEach(config -> {
            String name = config.string("name");
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
                this.trackNames.put(name, new PunishmentTrack(name, violationActions));
            });
        });
    }

    public void save() {

    }

    private void makeDefaultFiles() {
        this.plugin.saveResource("tracks/spam.yml", false);
        this.plugin.saveResource("tracks/cheating.yml", false);
        this.plugin.saveResource("tracks/racism.yml", false);
        this.plugin.saveResource("tracks/advertising.yml", false);
        this.plugin.saveResource("tracks/swearing.yml", false);
    }
}
