package org.argus.example.eureka.client;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.mongodb.*;
import com.mongodb.client.MongoIterable;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDbFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * @author starboyate
 */
@RestController
public class MongoController {

	@Autowired
	private MongoTemplate template;

	@Autowired
	private MongoProperties properties;

	@Autowired
	private MongoClient client;

	@GetMapping("/mongo")
	public Object test() {
		Mongo mongo = new Mongo("192.168.1.235", 27017);
		DB db = mongo.getDB("g08");
		CommandResult resultSet = db.getStats();
		CommandResult result = db.command("serverStatus");
		return result;
	}


	@GetMapping("/mongodb")
	public Object hello() {
		Document a = this.template.getDb().runCommand(new Document("serverStatus", "admin"));
		Document document = this.client.getDatabase("admin").runCommand(new BasicDBObject("top", 1));
		Map<String, Object> map = (Map<String, Object>) document.get("totals");
		ArrayList<String> arrayList = Lists.newArrayList();
		for (Map.Entry<String, Object> entry : map.entrySet()) {

			arrayList.add(entry.getKey());
		}
		return arrayList;
	}
}
