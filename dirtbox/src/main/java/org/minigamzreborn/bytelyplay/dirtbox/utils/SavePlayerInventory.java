package org.minigamzreborn.bytelyplay.dirtbox.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.ReplaceOptions;
import net.minestom.server.entity.Player;
import net.minestom.server.inventory.PlayerInventory;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.minigamzreborn.bytelyplay.dirtbox.constants.MongoDBConstants;

import java.io.UncheckedIOException;

public class SavePlayerInventory {
    private static ObjectMapper mapper = new ObjectMapper();
    public static void savePlayerInventory(Player p) {
        try {
            Document doc = new Document();
            doc.append("_id", p.getUuid().toString());
            doc.append(
                    p.getUuid().toString(),
                    Document.parse(
                            mapper.writer().writeValueAsString(PlayerInventorySerializerDeserializer.buildJsonTree(p.getInventory()))
                    )
            );
            MongoDBConstants.playerInventoryCollection.replaceOne(
                    Filters.eq("_id", p.getUuid()),
                    doc,
                    new ReplaceOptions().upsert(true)
            );
        } catch (JsonProcessingException e) {
            throw new UncheckedIOException(e);
        }
    }
}
