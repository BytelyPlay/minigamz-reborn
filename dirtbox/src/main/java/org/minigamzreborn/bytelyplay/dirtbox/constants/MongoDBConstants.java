package org.minigamzreborn.bytelyplay.dirtbox.constants;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class MongoDBConstants {
    public static MongoClient client;
    public static MongoDatabase database;

    public static MongoCollection<Document> playerInventoryCollection;
}
