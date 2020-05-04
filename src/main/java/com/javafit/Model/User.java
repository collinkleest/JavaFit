package com.javafit.Model;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.json.simple.JSONObject;

public class User extends Person {

    /*
     * Class Attributes (a username for the user, a password for the user
     * booleans for their selected choices of gaining muscle, gaining strength
     * or losing weight, they can also choose where they workout, either at home
     * or in the gym or both. 
     */
    private String userName;
    private String passWord;
    private boolean gainMuscle;
    private boolean gainStrength;
    private boolean loseWeight;
    private boolean home;
    private boolean gym;

    //class constructor to instantiate a user object
    public User(String name, String height, String weight, String dob,
            String uName, String pWord, boolean muscle, boolean strength,
            boolean loseWeight, boolean home, boolean gym) {
        super(name, height, weight, dob);
        this.userName = uName;
        this.passWord = pWord;
        this.gainMuscle = muscle;
        this.gainStrength = strength;
        this.loseWeight = loseWeight;
        this.home = home;
        this.gym = gym;
    }

    /*
     * Below are all getters and setters for the class
     */
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public boolean isGainMuscle() {
        return gainMuscle;
    }

    public void setGainMuscle(boolean gainMuscle) {
        this.gainMuscle = gainMuscle;
    }

    public boolean isGainStrength() {
        return gainStrength;
    }

    public void setGainStrength(boolean gainStrength) {
        this.gainStrength = gainStrength;
    }

    public boolean isLoseWeight() {
        return loseWeight;
    }

    public void setLoseWeight(boolean loseWeight) {
        this.loseWeight = loseWeight;
    }

    public boolean isHome() {
        return home;
    }

    public void setHome(boolean home) {
        this.home = home;
    }

    public boolean isGym() {
        return gym;
    }

    public void setGym(boolean gym) {
        this.gym = gym;
    }

    /*
     * to string method for debugging, prints out all attibutes for user object
     */
    @Override
    public String toString() {
        return super.toString() + "User [userName=" + userName + ", passWord=" + passWord + ", gainMuscle=" + gainMuscle
                + ", gainStrength=" + gainStrength + ", loseWeight=" + loseWeight + ", home=" + home + ", gym=" + gym
                + "]";
    }

    /* generates hash for password */
    public void generatePasswordHash(String passWord) {
        StringBuilder hash = new StringBuilder();
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] hashedBytes = md.digest(passWord.getBytes());
            char[] digits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
            for (int idx = 0; idx < hashedBytes.length; idx++) {
                byte b = hashedBytes[idx];
                hash.append(digits[(b & 0xf0) >> 4]);
                hash.append(digits[b & 0x0f]);
            }
        } catch (NoSuchAlgorithmException e) {
            System.out.println("no such algo error");
        }
        this.passWord = hash.toString();
    }

    /*
     * Generates JSON Object for User
     * From creators of the simple-json jar we used:
     *
     * NOTE:
     * With respect to the screen output shown on this page, ignore the possibility of output or lack of
     * output similar to the following:
     * Note: Code99.java uses unchecked or unsafe operations.
     * Note: Recompile with -Xlint:unchecked for details.
     *
     * Page 58 of the following simple-json documentation
     * https://www.google.com/url?sa=t&rct=j&q=&esrc=s&source=web&cd=1&cad=rja&uact=8&ved=2ahUKEwi4wYOjkZnpAhVpgXIEHYb8BUQQFjAAegQIARAB&url=https%3A%2F%2Fcnx.org%2Fexports%2Fe6c441f6-0a46-44c6-9f3c-b48759faac95%4014.1.pdf%2Fthe-json-simple-java-library-14.1.pdf&usg=AOvVaw0dJPP2N-8h8uH5K4WZSTYl
     * 
     */
    public JSONObject generateJSON() {
        JSONObject user = new JSONObject();
        user.put("name", super.getName());
        user.put("username", this.userName);
        user.put("password", this.passWord);
        user.put("height", super.getHeight());
        user.put("weight", super.getWeight());
        user.put("dob", super.getDob());
        user.put("gainMuscle", this.gainMuscle);
        user.put("gainStrength", this.gainStrength);
        user.put("loseWeight", this.loseWeight);
        user.put("home", this.home);
        user.put("gym", this.gym);
        return user;
    }
}
