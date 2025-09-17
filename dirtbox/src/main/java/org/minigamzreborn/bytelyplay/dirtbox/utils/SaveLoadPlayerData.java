package org.minigamzreborn.bytelyplay.dirtbox.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.ReplaceOptions;
import lombok.extern.slf4j.Slf4j;
import net.minestom.server.MinecraftServer;
import net.minestom.server.entity.Player;
import org.bson.Document;
import org.minigamzreborn.bytelyplay.dirtbox.constants.MongoDBConstants;

import java.io.UncheckedIOException;
import java.util.Collection;
import java.util.Optional;

@Slf4j
public class SaveLoadPlayerData {
    private static final ObjectMapper mapper = new ObjectMapper();
    public static void savePlayerData(Player p) {
        try {
            Document doc = new Document("_id", p.getUuid().toString());
            doc.append(
                    p.getUuid().toString(),
                    Document.parse(
                            mapper.writer().writeValueAsString(PlayerInventorySerializerDeserializer.buildJsonTree(p.getInventory()))
                    )
            );
            MongoDBConstants.playerInventoryCollection.replaceOne(
                    Filters.eq("_id", p.getUuid().toString()),
                    doc,
                    new ReplaceOptions().upsert(true)
            );
        } catch (JsonProcessingException e) {
            throw new UncheckedIOException(e);
        }
    }
    public static void saveAllPlayerInventories() {
        Collection<Player> players = MinecraftServer.getConnectionManager().getOnlinePlayers();

        for (Player p : players) {
            savePlayerData(p);
        }
    }
    public static Optional<JsonNode> getPlayerData(Player p) {
        try {
            MongoCollection<Document> collection = MongoDBConstants.playerInventoryCollection;

            FindIterable<Document> findIterable = collection.find(Filters.eq("_id", p.getUuid().toString()));

            try (MongoCursor<Document> cursor = findIterable.iterator()) {
                if (cursor.available() >= 1) {
                    Document doc = cursor.next();
                    JsonNode rootNode = mapper.readTree(doc.toJson());

                    return Optional.of(rootNode.get(p.getUuid().toString()));
                } else {
                    return Optional.empty();
                }
            }
        } catch (JsonProcessingException e) {
            throw new UncheckedIOException(e);
        }
    }
}