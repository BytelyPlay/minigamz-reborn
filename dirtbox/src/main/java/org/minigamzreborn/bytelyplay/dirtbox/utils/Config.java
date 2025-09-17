package org.minigamzreborn.bytelyplay.dirtbox.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import net.minestom.server.coordinate.Pos;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Slf4j
public class Config {
    private final ObjectMapper mapper = new ObjectMapper();
    private static final Path CONFIG_FILE_PATH = Path.of("./configuration", "config.json");
    private static final Path CONFIG_FOLDER = Path.of("./configuration");

    @Getter @Setter(value = AccessLevel.PRIVATE)
    private Pos spawnPoint = new Pos(0.5, 2, 0.5, 0, 0);
    @Getter @Setter(value = AccessLevel.PRIVATE)
    private String forwardingSecret = "";
    @Getter @Setter(value = AccessLevel.PRIVATE)
    private String mongoDBConnectionString = "mongodb://localhost:27017";

    private static Config instance;

    private Config() {
        if (instance != null) throw new IllegalStateException("Tried to create an instance of a singleton twice.");
        instance = this;
    }
    public static Config getInstance() {
        return instance == null ? new Config() : instance;
    }
    public void loadConfig() {
        try {
            if (!Files.exists(CONFIG_FOLDER)) Files.createDirectories(CONFIG_FOLDER);

            if (Files.exists(CONFIG_FILE_PATH)) {
                deserialize(Files.readString(CONFIG_FILE_PATH));
                log.debug("Deserializing config since there is a config.");
            } else {
                Files.writeString(CONFIG_FILE_PATH, serialize());
                log.debug("Serializing config since there is no config.");
            }
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
    private String serialize() {
        try {
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(buildJsonTree());
        } catch (JsonProcessingException e) {
            throw new UncheckedIOException(e);
        }
    }
    private void deserialize(String data) {
        try {
            JsonNode rootNode = mapper.readTree(data);

            JsonNode spawnPoint = rootNode.get("spawn_point");
            this.setSpawnPoint(new Pos(spawnPoint.get("x").asDouble(),
                    spawnPoint.get("y").asDouble(),
                    spawnPoint.get("z").asDouble(),
                    spawnPoint.get("yaw").floatValue(),
                    spawnPoint.get("pitch").floatValue()));

            this.setForwardingSecret(rootNode.get("forwarding_secret").asText());
            this.setMongoDBConnectionString(rootNode.get("mongodb_connection_url").asText());
        } catch (JsonProcessingException e) {
            throw new UncheckedIOException(e);
        }
    }
    private JsonNode buildJsonTree() {
        ObjectNode rootNode = mapper.createObjectNode();
        ObjectNode spawnPoint = mapper.createObjectNode();

        spawnPoint.put("x", this.getSpawnPoint().x());
        spawnPoint.put("y", this.getSpawnPoint().y());
        spawnPoint.put("z", this.getSpawnPoint().z());
        spawnPoint.put("yaw", this.getSpawnPoint().yaw());
        spawnPoint.put("pitch", this.getSpawnPoint().pitch());

        rootNode.put("forwarding_secret", this.getForwardingSecret());
        rootNode.put("mongodb_connection_url", this.getMongoDBConnectionString());

        rootNode.set("spawn_point", spawnPoint);

        return rootNode;
    }
}
