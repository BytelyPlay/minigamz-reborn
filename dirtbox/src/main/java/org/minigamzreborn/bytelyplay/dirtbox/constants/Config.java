package org.minigamzreborn.bytelyplay.dirtbox.constants;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import net.minestom.server.coordinate.Pos;
import org.abstractvault.bytelyplay.data.DataSetter;
import org.abstractvault.bytelyplay.enums.DataFormat;

import java.io.ByteArrayOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

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
    // TODO: don't use datasetter for this... use jackson itself directly...
    public void loadConfig() {
        try {
            if (!Files.exists(CONFIG_FOLDER)) Files.createDirectories(CONFIG_FOLDER);

            if (Files.exists(CONFIG_FILE_PATH)) {
                serialize(Files.readString(CONFIG_FILE_PATH));
            } else {
                Files.writeString(CONFIG_FILE_PATH, deserialize());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private String deserialize() {
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

        return rootNode.asText();
    }
    private void serialize(String data) {
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
