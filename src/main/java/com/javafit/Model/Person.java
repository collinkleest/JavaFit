package com.javafit.Model;

public class Person {
	
	/* class attributes (users full name or first name
	 * users height, users weight and users date of birth)*/
	private String name;
	private String height;
	private String weight;
	private String dob;
	
	
	public Person(String name, String height, String weight, String dob) {
		this.name = name;
		this.height = height;
		this.weight = weight;
		this.dob = dob;
	}


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


	@Override
	public String toString() {
		return "Person [name=" + name + ", height=" + height + ", weight=" + weight + ", dob=" + dob + "]";
	}
}
