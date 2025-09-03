package org.minigamzreborn.bytelyplay.velocity.utils;

import com.velocitypowered.api.proxy.server.RegisteredServer;
import org.minigamzreborn.bytelyplay.protobuffer.enums.ServerTypeOuterClass;

import java.util.concurrent.ConcurrentHashMap;

public class ServerTypeRegistry {
    public static ConcurrentHashMap<RegisteredServer, ServerTypeOuterClass.ServerType> typeAndAddress = new ConcurrentHashMap<>();
}
