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
	private final User tempUser;
	
	/*
	 * Constructor that takes in all the attributes for a user and creates a temporary user.
	 * Generates a hash for the temp User and starts a mongon connection. 
	 * **Insert user gets called from the view.**
	 */
	public NewUserController(String name, String height, String weight, String dob,
			String uName, String pWord, boolean muscle, boolean strength,
			boolean loseWeight, boolean home, boolean gym) {
		
		this.tempUser = new User(name, height, weight, dob, uName, pWord, muscle, strength, loseWeight, home, gym);
		//REQUIRED MAKE SURE YOU CALL GENERATE PASSWORD HASH FUNCTION
		this.tempUser.generatePasswordHash(this.tempUser.getPassWord());
		this.initializeMongoConnection();
	}
	
	/*
	 * Starts the mongoDB connection.
	 */
	private void initializeMongoConnection() {
		   this.mongoClient = MongoClients.create(
			    "mongodb+srv://ckleest:ckk@javafit-qy8fa.mongodb.net/test?retryWrites=true&w=majority");
			this.usersDB = mongoClient.getDatabase("USERS");
	}
	
	/*
	 * Uses the active MongoDB connection, grabs the USERS DB and collection
	 * Inserts a user into the database.
	 */
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
	
	/*
	 * Closes the current active mongodb connection.
	 */
	public void closeMongoConnection() {
		System.out.println("closing mongo client");
		this.mongoClient.close();
		System.out.println("successfully closed mongo connection");
	}
	
	/*
	 * Checks if a user already exists, if  it does then a user is not inserted into the DB.
	 * The program will alert the active user that there is already a user that exists with the username they inputed
	 */
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
