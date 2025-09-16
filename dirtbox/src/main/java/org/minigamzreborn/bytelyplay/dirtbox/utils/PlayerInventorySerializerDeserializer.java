package org.minigamzreborn.bytelyplay.dirtbox.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import lombok.extern.slf4j.Slf4j;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;
import net.minestom.server.codec.Result;
import net.minestom.server.codec.Transcoder;
import net.minestom.server.inventory.PlayerInventory;
import net.minestom.server.item.ItemStack;
import org.bson.Document;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.concurrent.atomic.AtomicReference;

@Slf4j
public class PlayerInventorySerializerDeserializer {
    private static final ObjectMapper mapper = new ObjectMapper();

    public static JsonNode buildJsonTree(PlayerInventory inv) {
        ObjectNode rootNode = mapper.createObjectNode();

        for (int i = 0; i < inv.getItemStacks().length; i++) {
            ItemStack stack = inv.getItemStack(i);
            if (stack.isAir()) continue;

            Result<@NotNull JsonElement> result = ItemStack.CODEC.encode(Transcoder.JSON, stack);
            JsonElement jsonElement = result.orElse(null);
            if (jsonElement == null) {
                log.warn("ItemStack encoding failed...");
                continue;
            }
            rootNode.put(String.valueOf(i), result.orElseThrow().getAsString());
        }
        return rootNode;
    }
    public static void fillInventory(JsonNode rootNode, PlayerInventory inv) {
        for (int i = 0; i < rootNode.size(); i++) {
            JsonNode subNode = rootNode.get(String.valueOf(i));
            if (subNode == null) continue;

            Result<ItemStack> result = ItemStack.CODEC.decode(Transcoder.JSON, JsonParser.parseString(subNode.textValue()));
            ItemStack resultStack = result.orElse(null);
            if (resultStack == null) {
                log.warn("Couldn't decode ItemStack.");
                continue;
            }
            inv.setItemStack(i, resultStack);
        }
    }
}
