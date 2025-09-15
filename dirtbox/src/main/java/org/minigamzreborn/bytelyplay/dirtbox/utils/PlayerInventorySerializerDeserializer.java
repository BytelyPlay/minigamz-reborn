package org.minigamzreborn.bytelyplay.dirtbox.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.minestom.server.inventory.PlayerInventory;

import java.io.IOException;
import java.io.UncheckedIOException;

public class PlayerInventorySerializerDeserializer {
    private static final ObjectMapper mapper = new ObjectMapper();
    public static PlayerInventory deserialize(byte[] bytes) {
        try {
            return mapper.readValue(bytes, new TypeReference<>() {});
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
    public static byte[] serialize(PlayerInventory inv) {
        try {
            return mapper.writeValueAsBytes(inv);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
