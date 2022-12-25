package de.ng.skypvp.database;

import java.util.UUID;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

public class Storage {
	
	public static Storage instance;
	
	private MongoClient mongoClient;
	private MongoDatabase database;
	
	private String serverAddress = "127.0.0.1";
	private int port = 17027;
//	private String userName = "minecraft";
//	private String databaseName = "test";
//	private char[] password = "123456".toCharArray();
	
	public Storage() {
		instance = this;
		
		/*
		System.setProperty("javax.net.ssl.trustStore", "./Cert/MongoClientKeyCert.p12");
		System.setProperty("javax.net.ssl.trustStorePassword","123456");
		
		System.setProperty("javax.net.ssl.keyStore","./Cert/MongoClientKeyCert.jks");
		System.setProperty("javax.net.ssl.keyStorePassword","123456");
		*/
		
//		MongoCredential credential = MongoCredential.createCredential(userName, databaseName, password);
//		MongoClientOptions options = MongoClientOptions.builder()
//				.sslEnabled(false)
//				.sslInvalidHostNameAllowed(false)
//				.build();
		
		mongoClient = new MongoClient(new ServerAddress(serverAddress, port)/*, credential, options*/);
		database = mongoClient.getDatabase("minecraft");
		
	}
	
	public void shutdown() {
		if(mongoClient != null)
			mongoClient.close();
	}
	
	public void setValue(String collectionName, UUID uuid, String key, Object value) {
		Document document = findDocument(collectionName, uuid);
		document.put(key, value);
		updateDocument(collectionName, uuid, document);
	}
	
	public void insertDocument(String collectionName, Document document) {
		MongoCollection<Document> collection = database.getCollection(collectionName);
		collection.insertOne(document);
	}
	
	public void updateDocument(String collectionName, UUID uuid, Document document) {
		MongoCollection<Document> collection = database.getCollection(collectionName);
		collection.updateOne(Filters.eq("uuid", uuid.toString()), new Document("$set", document));
	}
	
	public void deleteDocument(String collectionName, UUID uuid) {
		MongoCollection<Document> collection = database.getCollection(collectionName);
		collection.deleteOne(Filters.eq("uuid", uuid.toString()));
	}
	
	public Document findDocument(String collectionName, UUID uuid) {
		MongoCollection<Document> collection = database.getCollection(collectionName);
		return collection.find(Filters.eq("uuid", uuid.toString())).first();
	}
}