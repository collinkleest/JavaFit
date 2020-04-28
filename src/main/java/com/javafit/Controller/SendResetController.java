package com.javafit.Controller;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.bson.Document;

import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class SendResetController {
	
	private String passWord;
	private String userName;
	private MongoClient mongoClient;
	private MongoDatabase usersDB;
	
	public SendResetController(String pWord, String uName) {
		this.passWord = pWord;
		this.userName = uName;
		this.generateHash();
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
