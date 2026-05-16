package org.minigamzreborn.bytelyplay.velocity.utils;

import com.velocitypowered.api.proxy.server.RegisteredServer;
import lombok.Getter;
import org.minigamzreborn.bytelyplay.protobuffer.enums.ServerTypeOuterClass;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;

public class ServerTypeRegistry {
    @Getter
    private static final ServerTypeRegistry instance = new ServerTypeRegistry();

    // TODO: Replace this being public with this class having stuff to access this.
    public ConcurrentHashMap<RegisteredServer,
            ServerTypeOuterClass.ServerType> typeAndAddress =
            new ConcurrentHashMap<>();

    public Optional<RegisteredServer> getRandomServerOfType(
            ServerTypeOuterClass.ServerType toType
    ) {
        List<RegisteredServer> options = new ArrayList<>();
        typeAndAddress.forEach((regServer, type) -> {
            if (type == toType) options.add(regServer);
        });
        if (options.isEmpty()) return Optional.empty();

        return Optional.of(
                options.get(ThreadLocalRandom.current().nextInt(0, options.size()))
        );
    }
}
