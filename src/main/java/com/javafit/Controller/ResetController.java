package com.javafit.Controller;

import org.bson.Document;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

import javafx.scene.control.Alert;
import javafx.stage.Window;

public class ResetController {
	
	private String userName;
	private String dob;
	private MongoClient mongoClient;
	private MongoDatabase usersDB;
	
	public ResetController(String uName, String dob) {
		this.userName = uName;
		this.dob = dob;
		
		this.initializeMongoConnection();
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
	
	public boolean validate() {
		FindIterable<Document> iterable = this.usersDB.getCollection("USERS").find(new Document("username", this.userName));
		if (iterable.first()==null) {
			System.out.println("User not found!");
			return false;
		}
		else {
			if(iterable.first().get("dob").equals(this.dob)) {
				System.out.println("Successfully validated");
				return true;
			}
			else {
				System.out.println("DOB Not Valid");
				return false;
			}
		}
	}
	
	
	
}
