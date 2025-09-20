package org.minigamzreborn.bytelyplay.dirtbox.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import lombok.extern.slf4j.Slf4j;
import net.minestom.server.codec.Result;
import net.minestom.server.codec.Transcoder;
import net.minestom.server.inventory.PlayerInventory;
import net.minestom.server.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.io.UncheckedIOException;
import java.util.Map;
import java.util.Set;

@Slf4j
public class PlayerInventorySerializerDeserializer {
    // TODO: also do the cursor so yeah.
    private static final ObjectMapper mapper = new ObjectMapper();

    public static JsonNode buildJsonTree(PlayerInventory inv) {
        ObjectNode rootNode = mapper.createObjectNode();

        for (int i = 0; i < inv.getItemStacks().length; i++) {
            try {
                ItemStack stack = inv.getItemStack(i);
                if (stack.isAir()) continue;

                Result<@NotNull JsonElement> result = ItemStack.CODEC.encode(Transcoder.JSON, stack);
                JsonElement jsonElement = result.orElse(null);
                if (jsonElement == null) {
                    log.warn("ItemStack encoding failed...");
                    continue;
                }
                JsonNode node = mapper.readTree(result.orElseThrow().toString());
                rootNode.set(String.valueOf(i), node);
            } catch (JsonProcessingException e) {
                log.error("JsonProcessingException happened while building json tree for a player inventory, continuing to the get itemstack", e);
            }
        }
        return rootNode;
    }
    public static void fillInventory(JsonNode rootNode, PlayerInventory inv) {
        try {
            Set<Map.Entry<String, JsonNode>> entries = rootNode.properties();

            for (Map.Entry<String, JsonNode> entry : entries) {
                JsonNode subNode = entry.getValue();
                if (subNode == null) continue;

                Result<ItemStack> result = ItemStack.CODEC.decode(Transcoder.JSON, JsonParser.parseString(mapper.writeValueAsString(subNode)));
                ItemStack resultStack = result.orElse(null);

                if (resultStack == null) {
                    log.warn("Couldn't decode ItemStack.");
                    continue;
                }
                inv.setItemStack(Integer.parseInt(entry.getKey()), resultStack);
            }
        } catch (JsonProcessingException e) {
            throw new UncheckedIOException(e);
        }
    }
}
