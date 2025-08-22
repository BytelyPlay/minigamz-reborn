package org.minigamzreborn.bytelyplay.hub.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import net.minestom.server.coordinate.Pos;

import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Config {
    public Pos spawnPoint = Pos.ZERO;
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
        int spawnX = spawnPointNode.get("x").asInt();
        int spawnY = spawnPointNode.get("y").asInt();
        int spawnZ = spawnPointNode.get("z").asInt();
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

        rootNode.set("spawn_point", spawnPointNode);
        return rootNode;
    }
}
