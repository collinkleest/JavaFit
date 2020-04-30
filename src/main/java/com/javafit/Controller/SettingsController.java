package com.javafit.Controller;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class SettingsController {
	private String userName;
	private MongoClient mongoClient;
	private MongoDatabase usersDB;
	
	
	public SettingsController(String uName) {
		this.userName = uName;
		
		
		
	}
	
	
	
	private void initializeMongoConnection() {
        this.mongoClient = MongoClients.create(
                "mongodb+srv://ckleest:ckk@javafit-qy8fa.mongodb.net/test?retryWrites=true&w=majority");
        this.usersDB = mongoClient.getDatabase("USERS");
    }

    public void closeMongoConnection() {
        System.out.println("closing mongo client");
        this.mongoClient.close();
        System.out.println("successfully closed mongo connection");
    }
	
}
