package org.minigamzreborn.bytelyplay.dirtbox.utils;

import org.abstractvault.bytelyplay.data.DataSetter;
import org.abstractvault.bytelyplay.enums.DataFormat;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import net.minestom.server.coordinate.Pos;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;

// TODO: Also make the IP to register the server with configurable.
@Slf4j
public class Config {
    private static final DataSetter CONFIG_SETTER = new DataSetter.Builder()
            .getterSetter(Config.getInstance()::getListenIp,
                    Config.getInstance()::setListenIp, "listen_ip")
            .getterSetter(Config.getInstance()::getListenPort,
                    Config.getInstance()::setListenPort, "listen_port")

            .getterSetter(Config.getInstance()::getProxyIp,
                    Config.getInstance()::setProxyIp, "proxy_ip")
            .getterSetter(Config.getInstance()::getProxyPort,
                    Config.getInstance()::setProxyPort, "proxy_port")

            .getterSetter(Config.getInstance()::getIpToRegisterWith,
                    Config.getInstance()::setIpToRegisterWith, "ip_to_register_with")

            .getterSetter(Config.getInstance()::getSpawnPoint,
                    Config.getInstance()::setSpawnPoint, "spawn_point")

            .getterSetter(Config.getInstance()::getForwardingSecret,
                    Config.getInstance()::setForwardingSecret, "secret")

            .getterSetter(Config.getInstance()::getMongoDBConnectionString,
                    Config.getInstance()::setMongoDBConnectionString, "mongodb_connection_url")
            .build();
    private static final Path CONFIG_FILE_PATH = Path.of(
            "./configuration", "config.json"
    );
    private static final Path CONFIG_FOLDER = Path.of("./configuration");

    @Getter @Setter(value = AccessLevel.PRIVATE)
    private Pos spawnPoint = new Pos(0.5, 2, 0.5, 0, 0);

    @Getter @Setter(value = AccessLevel.PRIVATE)
    private String forwardingSecret = "";

    @Getter @Setter(value = AccessLevel.PRIVATE)
    private String mongoDBConnectionString = "mongodb://localhost:27017";

    @Getter @Setter(value = AccessLevel.PRIVATE)
    private String listenIp = "127.0.0.1";

    @Getter @Setter(value = AccessLevel.PRIVATE)
    private int listenPort = 25569;

    @Getter @Setter(value = AccessLevel.PRIVATE)
    private String proxyIp = "127.0.0.1";

    @Getter @Setter(value = AccessLevel.PRIVATE)
    private short proxyPort = 9485;

    @Getter @Setter(value = AccessLevel.PRIVATE)
    private String ipToRegisterWith = "127.0.0.1";

    private static Config instance;

    private Config() {
        if (instance != null) throw new IllegalStateException(
                "Tried to create an instance of a singleton twice."
        );
        instance = this;
    }
    public static Config getInstance() {
        return instance == null ? new Config() : instance;
    }

    public void loadConfig() {
        try {
            if (!Files.exists(CONFIG_FOLDER)) Files.createDirectories(CONFIG_FOLDER);

            if (Files.exists(CONFIG_FILE_PATH)) {
                CONFIG_SETTER.deserialize(Files.newInputStream(CONFIG_FILE_PATH));
            } else {
                CONFIG_SETTER.serialize(
                        Files.newOutputStream(CONFIG_FILE_PATH),
                        DataFormat.TEXT_PRETTY_JSON
                );
            }
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
