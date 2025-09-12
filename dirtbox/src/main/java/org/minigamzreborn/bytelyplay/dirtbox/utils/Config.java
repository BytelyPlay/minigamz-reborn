package org.minigamzreborn.bytelyplay.dirtbox.utils;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Lombok;
import lombok.Setter;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.instance.Instance;
import org.abstractvault.bytelyplay.data.DataSetter;
import org.abstractvault.bytelyplay.enums.DataFormat;

import java.nio.file.Files;
import java.nio.file.Path;

public class Config {
    private static final Path CONFIG_FILE_PATH = Path.of("./configuration", "config.json");
    private static final Path CONFIG_FOLDER = Path.of("./configuration");

    @Getter @Setter(value = AccessLevel.PRIVATE)
    private Pos spawnPoint = new Pos(0.5, 2, 0.5, 0, 0);
    @Getter @Setter(value = AccessLevel.PRIVATE)
    private String forwardingSecret = "";

    private static Config instance;

    private Config() {
        if (instance != null) throw new IllegalStateException("Tried to create an instance of a singleton twice.");
        instance = this;
    }
    public static Config getInstance() {
        return instance == null ? new Config() : instance;
    }
    public DataSetter getDataSetter() {
        return new DataSetter.Builder()
                .getterSetter(this::getSpawnPoint, this::setSpawnPoint, "spawn_point")
                .getterSetter(this::getForwardingSecret, this::setForwardingSecret, "forwarding_secret")
                .build();
    }
    // TODO: don't use datasetter for this... use jackson itself directly...
    public void loadConfig() {
        try {
            DataSetter configDataSetter = Config.getInstance().getDataSetter();

            if (!Files.exists(CONFIG_FOLDER)) Files.createDirectories(CONFIG_FOLDER);

            if (Files.exists(CONFIG_FILE_PATH)) {
                configDataSetter.load(CONFIG_FILE_PATH);
            } else {
                configDataSetter.save(CONFIG_FILE_PATH, DataFormat.TEXT_PRETTY_JSON);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
