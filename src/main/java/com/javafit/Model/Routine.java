package com.javafit.Model;

public class Routine {

    //class attributes
    private String routineName;
    private String sets;
    private boolean gainStrength;
    private boolean gainMuscle;
    private boolean loseWeight;
    private String muscleGroup;
    private boolean home;
    private boolean gym;

    /*
	 * Constructor to instantiate a routine object
     */
    public Routine(String routineName, String sets, boolean gainStrength, boolean gainMuscle, boolean loseWeight,
            String muscleGroup, boolean home, boolean gym) {
        super();
        this.routineName = routineName;
        this.sets = sets;
        this.gainStrength = gainStrength;
        this.gainMuscle = gainMuscle;
        this.loseWeight = loseWeight;
        this.muscleGroup = muscleGroup;
        this.home = home;
        this.gym = gym;
    }

    /*
	 * To string method for debugging and printing out the attributes of a routine object
     */
    @Override
    public String toString() {
        return "Routine [routineName=" + routineName + ", sets=" + sets + ", gainStrength=" + gainStrength
                + ", gainMuscle=" + gainMuscle + ", loseWeight=" + loseWeight + ", muscleGroup=" + muscleGroup
                + ", home=" + home + ", gym=" + gym + "]";
    }

    /*
	 * Below are all getters and setters for the class
     */
    public String getRoutineName() {
        return routineName;
    }

    public void setRoutineName(String routineName) {
        this.routineName = routineName;
    }

    public String getSets() {
        return sets;
    }

    public void setSets(String sets) {
        this.sets = sets;
    }

    public boolean isGainStrength() {
        return gainStrength;
    }

    public void setGainStrength(boolean gainStrength) {
        this.gainStrength = gainStrength;
    }

    public boolean isGainMuscle() {
        return gainMuscle;
    }

    public void setGainMuscle(boolean gainMuscle) {
        this.gainMuscle = gainMuscle;
    }

    public boolean isLoseWeight() {
        return loseWeight;
    }

    public void setLoseWeight(boolean loseWeight) {
        this.loseWeight = loseWeight;
    }

    public String getMuscleGroup() {
        return muscleGroup;
    }

    public void setMuscleGroup(String muscleGroup) {
        this.muscleGroup = muscleGroup;
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

}
