package de.ng.skypvp.database;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.UUID;

import org.bson.Document;

public class StorageCache {
	
	private static final HashMap<UUID, Document> cache = new HashMap<>();
	
	public static Document getDocument(String collectionName, UUID uuid) {
		if(cache.containsKey(uuid))
			return cache.get(uuid);
		Document document = Storage.instance.findDocument(collectionName, uuid);
		
		if(document == null) {
			document = new Document("uuid", uuid.toString());
			Storage.instance.insertDocument("skypvp", document);
		}
		cache.put(uuid, document);
		
		return document;
	}
	
	public static void setDocumantValue(String collectionName, UUID uuid, String key, Object value) {
		Document document = getDocument(collectionName, uuid);
		document.put(key, value);
	}
	
	public static void removeFromCache(String collectionName, UUID uuid) {
		if(!cache.containsKey(uuid))
			return;
		Storage.instance.updateDocument(collectionName, uuid, cache.remove(uuid));
	}

	public static void shutdown() {
		Storage storage = Storage.instance;
		for(Entry<UUID, Document> entry : cache.entrySet())
			storage.updateDocument("skypvp", entry.getKey(), entry.getValue());
	}
}