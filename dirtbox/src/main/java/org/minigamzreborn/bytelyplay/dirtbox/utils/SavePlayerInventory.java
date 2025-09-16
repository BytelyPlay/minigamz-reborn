package org.minigamzreborn.bytelyplay.dirtbox.utils;

import com.mongodb.client.model.Filters;
import com.mongodb.client.model.ReplaceOptions;
import net.minestom.server.entity.Player;
import net.minestom.server.inventory.PlayerInventory;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.minigamzreborn.bytelyplay.dirtbox.constants.MongoDBConstants;

public class SavePlayerInventory {
    public static void savePlayerInventory(Player p) {
        Document doc = new Document();
        doc.append("_id", p.getUuid().toString());
        doc.append(
                p.getUuid().toString(),
                Document.parse(PlayerInventorySerializerDeserializer.buildJsonTree(p.getInventory()).asText())
        );
        MongoDBConstants.playerInventoryCollection.replaceOne(
                Filters.eq("_id", p.getUuid()),
                doc,
                new ReplaceOptions().upsert(true)
        );
    }
}
