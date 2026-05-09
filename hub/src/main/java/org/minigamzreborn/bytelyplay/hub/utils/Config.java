package org.minigamzreborn.bytelyplay.hub.utils;

import lombok.Getter;
import net.minestom.server.coordinate.Pos;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.node.ObjectNode;

public final class Config {
    private static Config instance;

    @Getter
    private String secret = "";

    @Getter
    private String listenIp = "0.0.0.0";

    @Getter
    private int port = 25566;

    @Getter
    private Pos spawnPoint = Pos.ZERO;

    public static Config getInstance() {
        if (instance == null) return new Config();
        return instance;
    }
    private Config() {
        if (instance != null) {
            throw new IllegalStateException("Tried to call the constructor of a singleton class twice.");
        }
        instance = this;
    }
    public static void deserialize(JsonNode rootNode) {
        Config config = Config.getInstance();
        JsonNode spawnPointNode = rootNode.get("spawn_point");

        double spawnX = spawnPointNode.get("x").asDouble();
        double spawnY = spawnPointNode.get("y").asDouble();
        double spawnZ = spawnPointNode.get("z").asDouble();

        String secret = rootNode.get("secret").asString();
        String listenIP = rootNode.get("listenIp").asString();
        int port = rootNode.get("port").asInt();

        config.secret = secret;
        config.listenIp = listenIP;
        config.spawnPoint = new Pos(spawnX, spawnY, spawnZ);
        config.port = port;
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

        rootNode.put("secret", config.secret);
        rootNode.put("listenIp", config.listenIp);
        rootNode.put("port", config.port);
        return rootNode;
    }
}
