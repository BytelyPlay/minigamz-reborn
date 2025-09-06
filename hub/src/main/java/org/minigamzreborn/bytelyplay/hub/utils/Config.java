package org.minigamzreborn.bytelyplay.hub.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Getter;
import net.minestom.server.coordinate.Pos;

public class Config {
    @Getter
    private String secret = "";
    @Getter
    private Pos spawnPoint = Pos.ZERO;
    private static Config instance;
    public static Config getInstance() {
        if (instance == null) return new Config();
        return instance;
    }
    private Config() {
        instance = this;
    }
    public static void deserialize(JsonNode rootNode) {
        Config config = new Config();
        JsonNode spawnPointNode = rootNode.get("spawn_point");
        double spawnX = spawnPointNode.get("x").asDouble();
        double spawnY = spawnPointNode.get("y").asDouble();
        double spawnZ = spawnPointNode.get("z").asDouble();
        String secret = rootNode.get("secret").asText();

        config.secret = secret;
        config.spawnPoint = new Pos(spawnX, spawnY, spawnZ);
    }
    public static JsonNode serialize() {
        Config config = Config.getInstance();
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode rootNode = mapper.createObjectNode();
        ObjectNode spawnPointNode = mapper.createObjectNode();
        spawnPointNode.put("x", config.spawnPoint.x());
        spawnPointNode.put("y", config.spawnPoint.y());
        spawnPointNode.put("z", config.spawnPoint.z());

        rootNode.put("secret", config.secret);
        rootNode.set("spawn_point", spawnPointNode);
        return rootNode;
    }
}
