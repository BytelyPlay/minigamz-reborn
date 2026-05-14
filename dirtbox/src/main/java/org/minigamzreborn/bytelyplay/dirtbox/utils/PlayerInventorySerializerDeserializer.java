package org.minigamzreborn.bytelyplay.dirtbox.utils;

import net.minestom.server.MinecraftServer;
import net.minestom.server.codec.Encoder;
import net.minestom.server.registry.Registry;
import net.minestom.server.registry.RegistryTranscoder;
import org.bson.types.Binary;
import org.minigamzreborn.bytelyplay.dirtbox.constants.RegistryTranscoders;
import tools.jackson.core.JacksonException;
import tools.jackson.databind.ObjectMapper;
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

    public static Document buildJsonTree(Player player) throws JacksonException {
        Document doc = new Document("_id", player.getUuid().toString());
        PlayerInventory inv = player.getInventory();

        for (int i = 0; i < inv.getItemStacks().length; i++) {
            try {
                ItemStack stack = inv.getItemStack(i);
                if (stack.isAir()) continue;

                Result<@NotNull BinaryTag> result =
                        ItemStack.CODEC.encode(RegistryTranscoders.nbtRegistryTranscoder, stack);

                if (result instanceof Result.Error<BinaryTag> err) {
                    log.warn("Error when encoding an ItemStack: ",
                            new IllegalArgumentException(err.message()));
                    continue;
                }

                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                BinaryTagIO.writer().write((CompoundBinaryTag) result.orElseThrow(), stream);

                doc.put(String.valueOf(i), stream.toByteArray());
            } catch (IOException e) {
                log.error("Error occurred while trying to encode data", e);
            }
        }
        return doc;
    }

    public static void fillInventory(Document rootNode, PlayerInventory inv) throws JacksonException, IOException {
        for (Map.Entry<String, Object> entry : rootNode.entrySet()) {
            Object subNode = entry.getValue();
            if (subNode instanceof Binary stack) {
                ByteArrayInputStream stream = new ByteArrayInputStream(stack.getData());
                CompoundBinaryTag tag = BinaryTagIO.reader().read(stream);

                Result<@NotNull ItemStack> result =
                        ItemStack.CODEC.decode(RegistryTranscoders.nbtRegistryTranscoder, tag);

                inv.setItemStack(Integer.parseInt(entry.getKey()), result.orElseThrow());
            } else {
                if (entry.getKey().equals("_id")) continue;
                log.warn("Couldn't read a certain ItemStack because it isn't binary.");
            }
        }
    }
}
