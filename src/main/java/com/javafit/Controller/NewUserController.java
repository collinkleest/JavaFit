package com.javafit.Controller;

// class imports
import org.bson.Document;

import com.javafit.Model.User;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class NewUserController {
	
	// class attributes
	private MongoClient mongoClient;
	private MongoDatabase usersDB;
	private User tempUser;
	
	/*
	 * Constructor that takes in all the attributes for a user and creates a user.
	 */
	public NewUserController(String name, String height, String weight, String dob,
			String uName, String pWord, boolean muscle, boolean strength,
			boolean loseWeight, boolean home, boolean gym) {
		
		this.tempUser = new User(name, height, weight, dob, uName, pWord, muscle, strength, loseWeight, home, gym);
		//REQUIRED MAKE SURE YOU CALL GENERATE PASS HASH FUNCTION
		this.tempUser.generatePasswordHash(this.tempUser.getPassWord());
		this.initializeMongoConnection();
	}
	
	private void initializeMongoConnection() {
		   this.mongoClient = MongoClients.create(
			    "mongodb+srv://ckleest:ckk@javafit-qy8fa.mongodb.net/test?retryWrites=true&w=majority");
			this.usersDB = mongoClient.getDatabase("USERS");
	}
	
	public void insertUser() {
		MongoCollection<Document> collection = usersDB.getCollection("USERS");
		Document tempDoc = new Document();
		tempDoc.put("username", this.tempUser.getUserName());
		tempDoc.put("name", this.tempUser.getName());
		tempDoc.put("password", this.tempUser.getPassWord());
		tempDoc.put("dob", this.tempUser.getDob());
		tempDoc.put("height", this.tempUser.getHeight());
		tempDoc.put("weight", this.tempUser.getWeight());
		tempDoc.put("gainMuscle", this.tempUser.isGainMuscle());
		tempDoc.put("gainStrength", this.tempUser.isGainStrength());
		tempDoc.put("loseWeight", this.tempUser.isLoseWeight());
		tempDoc.put("home", this.tempUser.isHome());
		tempDoc.put("gym", this.tempUser.isGym());
		collection.insertOne(tempDoc);
	}
	
	public void closeMongoConnection() {
		System.out.println("closing mongo client");
		this.mongoClient.close();
		System.out.println("successfully closed mongo connection");
	}
	
	public boolean checkUserExistence() {
		FindIterable<Document> iterable = this.usersDB.getCollection("USERS").find(new Document("username", this.tempUser.getUserName()));
		if (iterable.first() == null) {
			return false;
		}
		else {
			System.out.println("#-- USER ALREADY EXISTS --#");
			return true;
		}
	}
	
}
