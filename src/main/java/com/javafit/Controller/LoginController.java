package com.javafit.Controller;

//class imports
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.bson.Document;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class LoginController {
	//class attributes
	private String userName;
	private String passWord;
	private MongoClient mongoClient;
	private MongoDatabase usersDB;
	
	/*
	 * Class constructor takes in a username and a password.
	 * Generates a hash on the imputed password.
	 * Lastly initilizes the mongodb connection.
	 */
	public LoginController(String uName, String pWord) {
		this.userName = uName;
		this.passWord = pWord;
		this.generateHash();
		this.initializeMongoConnection();
	}
	
	/*
	 * Login function, checks if theres is a user object with the inputed username, if there is it uses the password hash and compares
	 * it against the password hash in the database.
	 * If these password hashes match the the user gets authenticated.
	 */
	public boolean login() {
		FindIterable<Document> iterable = this.usersDB.getCollection("USERS").find(new Document("username", this.userName));
		if (iterable.first()==null) {
			System.out.println("User not found!");
			return false;
		}
		else {
			if (iterable.first().get("password").equals(this.passWord)) {
				System.out.println("Authentication Successful!");
				return true;
			}
		}
		return false;
	}
	
	/*
	 * Starts up the mongo db client and starts a connection to the databse. 
	 * Grabs the user database.
	 */
	private void initializeMongoConnection() {
		   this.mongoClient = MongoClients.create(
			    "mongodb+srv://ckleest:ckk@javafit-qy8fa.mongodb.net/test?retryWrites=true&w=majority");
			this.usersDB = mongoClient.getDatabase("USERS");
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
	 * Generates a MD5 hash with a given string, intended for password usage.
	 */
	private void generateHash() {
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
        
	/*
	 * Get method to grab the username.
	 */
    public String getUsername() {
          return this.userName;
    }
	
}
