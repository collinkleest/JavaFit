package com.javafit.Controller;

// class imports
import org.bson.Document;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class ResetController {
	
	// class attributes
	private String userName;
	private String dob;
	private MongoClient mongoClient;
	private MongoDatabase usersDB;
	
	/*
	 * Class constructor, takes username and date of birth to authenticate 
	 * if this is the current user that we want to change.
	 * Lastly the constructor starts a mongo connection.
	 */
	public ResetController(String uName, String dob) {
		this.userName = uName;
		this.dob = dob;
		
		this.initializeMongoConnection();
	}
	
	/*
	 * This method starts an active connection with the MongoDB database.
	 */
	private void initializeMongoConnection() {
		   this.mongoClient = MongoClients.create(
			    "mongodb+srv://ckleest:ckk@javafit-qy8fa.mongodb.net/test?retryWrites=true&w=majority");
			this.usersDB = mongoClient.getDatabase("USERS");
	}
	
	/*
	 * This method simply closes the current mongodb connection. 
	 */
	public void closeMongoConnection() {
		System.out.println("closing mongo client");
		this.mongoClient.close();
		System.out.println("successfully closed mongo connection");
	}
	
	/*
	 * Validates if the users inputs match the account they want to reset the password for.
	 * Checks their username, and if the date of birth matches with that username.
	 * Mkaes a query to ge that user object, then checks the users authenticity.
	 */
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
