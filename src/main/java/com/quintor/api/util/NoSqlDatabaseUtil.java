package com.quintor.api.util;


import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class NoSqlDatabaseUtil {
    private static MongoCollection db;

    public NoSqlDatabaseUtil() {
        db = null;
    }

    public static MongoCollection getConnection()
    {
        ConnectionString connectionString = new ConnectionString("mongodb://root:example@mongodb:27017");
        MongoClientSettings mongoClientSettings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .build();
        MongoClient mongoClient = MongoClients.create(mongoClientSettings);
        MongoDatabase mongoDatabase = mongoClient.getDatabase("MT940");
        db = mongoDatabase.getCollection("Transactions");
        return db;
    }
}
