package com.javafit.Model;

public class Person {

    /* class attributes (users full name or first name
	 * users height, users weight and users date of birth)*/
    private String name;
    private String height;
    private String weight;
    private String dob;

    //constructor for a person takes in name, height, weight, and date of birth.
    public Person(String name, String height, String weight, String dob) {
        this.name = name;
        this.height = height;
        this.weight = weight;
        this.dob = dob;
    }


    /*
     * Below is all the getters and setters for the class
     */
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }


    /*
	 * TO string method for debugging, prints out person object
     */
    @Override
    public String toString() {
        return "Person [name=" + name + ", height=" + height + ", weight=" + weight + ", dob=" + dob + "]";
    }
}
