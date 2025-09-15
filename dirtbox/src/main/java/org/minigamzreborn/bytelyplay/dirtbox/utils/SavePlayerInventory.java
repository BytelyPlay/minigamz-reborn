package org.minigamzreborn.bytelyplay.dirtbox.utils;

import net.minestom.server.entity.Player;
import net.minestom.server.inventory.PlayerInventory;
import org.bson.Document;
import org.minigamzreborn.bytelyplay.dirtbox.constants.MongoDBConstants;

public class SavePlayerInventory {
    public static void savePlayerInventory(Player p) {
        Document doc = new Document();
        // TODO: Find a way to turn a JsonNode in a document.
        doc.append(
                p.getUuid().toString(),
                PlayerInventorySerializerDeserializer.buildJsonTree(p.getInventory())
        );
    }
}
