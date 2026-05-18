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
    private int listenPort = 25566;

    @Getter
    private Pos spawnPoint = Pos.ZERO;

    @Getter
    private String proxyIp = "127.0.0.1";

    @Getter
    private short proxyPort = 9485;

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

        config.secret = rootNode.get("secret").asString();
        config.listenIp = rootNode.get("listen_ip").asString();
        config.listenPort = rootNode.get("listen_port").asInt();
        
        config.proxyIp = rootNode.get("proxy_ip").asString();
        config.proxyPort = rootNode.get("proxy_port").asShort();

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

        rootNode.put("secret", config.secret);
        rootNode.put("listen_ip", config.listenIp);
        rootNode.put("listen_port", config.listenPort);

        rootNode.put("proxy_ip", config.proxyIp);
        rootNode.put("proxy_port", config.proxyPort);
        return rootNode;
    }
}
