package com.javafit.Controller;

//class imports
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.bson.Document;

import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class SendResetController {
	
	//class attributes
	private String passWord;
	private String userName;
	private MongoClient mongoClient;
	private MongoDatabase usersDB;
	
	/*
	 * Class constructor, takes in user password, and username.
	 * calls generate hash function, and starts a mongo connection.
	 */
	public SendResetController(String pWord, String uName) {
		this.passWord = pWord;
		this.userName = uName;
		this.generateHash();
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
	 * Function to make a password reset query.
	 * First checks if there is actually a user in the database with the inputed username, if this is false it makes the query to change
	 * the current users password.
	 * This functionality is actually in the appliaction in the change user settings controller.
	 * Function returns true if the users password was successfully changed and returns false if it is not successfully changed. 
	 */
	public boolean reset(){
		FindIterable<Document> iterable = this.usersDB.getCollection("USERS").find(new Document("username", this.userName));
		if (iterable.first()==null) {
			System.out.println("User not found!");
			return false;
		}
		else {
			
			BasicDBObject searchQuery = new BasicDBObject();
			searchQuery.append("username", this.userName);
			
			BasicDBObject updateQuery = new BasicDBObject();
			updateQuery.append("$set",
			new BasicDBObject().append("password", this.passWord));
			
			usersDB.getCollection("USERS").updateOne(searchQuery, updateQuery);
		
			System.out.println("Succesful password change!");
			return true;
		}
	}
	
	/*
	 * This method takes in a password and coverts it to a MD5 hash of the password.
	 */
	public void generateHash() {
		StringBuilder hash = new StringBuilder();
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] hashedBytes = md.digest(passWord.getBytes());
			char[] digits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
			for (int idx = 0; idx < hashedBytes.length; idx++) {
				byte b = hashedBytes[idx];
				hash.append(digits[(b & 0xf0) >> 4]);
				hash.append(digits[b & 0x0f]);
			}
		} catch(NoSuchAlgorithmException e) {
			System.out.println("no such algo error");
		}
		this.passWord = hash.toString();
	}
	
}
