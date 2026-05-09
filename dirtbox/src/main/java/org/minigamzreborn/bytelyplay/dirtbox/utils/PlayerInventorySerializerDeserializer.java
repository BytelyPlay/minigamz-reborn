package org.minigamzreborn.bytelyplay.dirtbox.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import lombok.extern.slf4j.Slf4j;
import net.kyori.adventure.nbt.BinaryTag;
import net.kyori.adventure.nbt.BinaryTagIO;
import net.kyori.adventure.nbt.CompoundBinaryTag;
import net.minestom.server.codec.Result;
import net.minestom.server.codec.Transcoder;
import net.minestom.server.entity.Player;
import net.minestom.server.inventory.PlayerInventory;
import net.minestom.server.item.ItemStack;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Map;
import java.util.Set;

@SuppressWarnings("UnstableApiUsage")
@Slf4j
public class PlayerInventorySerializerDeserializer {
    // TODO: also do the cursor so yeah.
    private static final ObjectMapper mapper = new ObjectMapper();

    public static Document buildJsonTree(Player player) {
        Document doc = new Document("_id", player.getUuid().toString());
        PlayerInventory inv = player.getInventory();

        for (int i = 0; i < inv.getItemStacks().length; i++) {
            try {
                ItemStack stack = inv.getItemStack(i);
                if (stack.isAir()) continue;

                Result<@NotNull BinaryTag> result = ItemStack.CODEC.encode(Transcoder.NBT, stack);

                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                BinaryTagIO.writer().write((CompoundBinaryTag) result.orElseThrow(), stream);

                doc.put(String.valueOf(i), stream.toByteArray());
            } catch (JsonProcessingException e) {
                log.error("JsonProcessingException happened while building json tree for a player inventory, continuing to the get itemstack", e);
            } catch (IOException e) {
                log.error("Error occurred while trying to encode data", e);
            }
        }
        return doc;
    }

    public static void fillInventory(Document rootNode, PlayerInventory inv) {
        for (Map.Entry<String, Object> entry : rootNode.entrySet()) {
            try {
                Object subNode = entry.getValue();
                if (subNode instanceof byte[] stack) {
                    ByteArrayInputStream stream = new ByteArrayInputStream(stack);
                    CompoundBinaryTag tag = BinaryTagIO.reader().read(stream);

                    Result<@NotNull ItemStack> result = ItemStack.CODEC.decode(Transcoder.NBT, tag);

                    inv.setItemStack(Integer.parseInt(entry.getKey()), result.orElseThrow());
                } else {
                    if (subNode instanceof String s) {
                        if (!s.equals("_id")) {
                            log.warn("Couldn't read a certain ItemStack because it isn't binary.");
                        }
                    }
                }
            } catch (JsonProcessingException e) {
                log.error("Something went wrong processing json", e);
            } catch (IOException e) {
                log.error("Something went wrong decoding NBT", e);
            }
        }
    }
}
