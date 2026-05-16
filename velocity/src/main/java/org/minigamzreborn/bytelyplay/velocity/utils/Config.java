package org.minigamzreborn.bytelyplay.velocity.utils;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.abstractvault.bytelyplay.data.DataSetter;
import org.abstractvault.bytelyplay.enums.DataFormat;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Config {
    // This is higher, since CONFIG_SETTER needs to access it, it should've been under CONFIG_SETTER if I was following my formatting rules.
    @Getter
    private static final Config instance = new Config();

    private static final DataSetter CONFIG_SETTER =
            new DataSetter.Builder()
                    .getterSetter(Config.getInstance()::getProtocolListenPort,
                            Config.getInstance()::setProtocolListenPort,
                            "port", Short.class)
                    .getterSetter(Config.getInstance()::getIp,
                            Config.getInstance()::setIp, "ip",
                            String.class)
                    .build();

    @Getter
    private static Path configFile;

    @Getter @Setter(value = AccessLevel.PRIVATE)
    private short protocolListenPort = 9485;

    @Getter @Setter(value = AccessLevel.PRIVATE)
    private String ip = "127.0.0.1";

    public void init() throws IOException {
        // The Illegal argument exception cannot be thrown since
        // I specify the classes of each getter and setter,
        // and also the getters never return false.

        if (Files.exists(configFile)) CONFIG_SETTER.load(configFile);
        else CONFIG_SETTER.save(configFile, DataFormat.TEXT_PRETTY_JSON);
    }

    public static void setConfigFile(Path p) {
        if (configFile == null) {
            configFile = p.resolve("config.json");
        }
        else throw new IllegalCallerException("Cannot set the config path twice.");
    }
}
