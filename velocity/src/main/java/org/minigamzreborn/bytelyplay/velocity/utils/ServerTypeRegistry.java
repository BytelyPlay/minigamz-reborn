package org.minigamzreborn.bytelyplay.velocity.utils;

import com.velocitypowered.api.proxy.server.RegisteredServer;
import com.velocitypowered.api.proxy.server.ServerInfo;
import org.minigamzreborn.bytelyplay.protobuffer.enums.ServerTypeOuterClass;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

public class ServerTypeRegistry {
    public static ConcurrentHashMap<RegisteredServer, ServerTypeOuterClass.ServerType> typeAndAddress = new ConcurrentHashMap<>();
}
