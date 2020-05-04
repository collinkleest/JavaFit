package com.javafit.Controller;

import com.javafit.View.ReportView;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

/**
 *
 * @author Connell Boyce and Collin Kleest
 */
public class ReportController {

    private final ReportView reportView;
    private MongoClient mongoClient;
    private MongoDatabase usersDB;
    private Document userObj;

    public ReportController(String userName) {
        reportView = new ReportView(this, userName);
    }

    public void initializeMongoConnection() {
        this.mongoClient = MongoClients.create(
                "mongodb+srv://ckleest:ckk@javafit-qy8fa.mongodb.net/test?retryWrites=true&w=majority");
        this.usersDB = mongoClient.getDatabase("USERS");
    }

    public void closeMongoConnection() {
        System.out.println("closing mongo client");
        this.mongoClient.close();
        System.out.println("successfully closed mongo connection");
    }

    public Document getUserObj(String uName) {
        FindIterable<Document> iterable = this.usersDB.getCollection("USERS").find(new Document("username", uName));
        this.userObj = iterable.first();
        return userObj;
    }

    public MongoDatabase getUsersDB() {
        return this.usersDB;
    }
}
